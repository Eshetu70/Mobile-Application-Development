package edu.uncc.hw06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.hw06.databinding.CommentRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Forum mForum;
    private String mParam2;

    public ForumFragment() {
        // Required empty public constructor
    }


    public static ForumFragment newInstance(Forum forum) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable( ARG_PARAM_FORUM, forum);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = (Forum) getArguments().getSerializable(ARG_PARAM_FORUM);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentForumBinding binding;
   FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Comment> comments = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    CommentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forum");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommentAdapter();
        binding.recyclerView.setAdapter(adapter);


        binding.textViewForumTitle.setText(mForum.getTitle());
        binding.textViewForumCreatedBy.setText(mForum.getCreatedByName());
        binding.textViewForumText.setText(mForum.getDescription());


        listenerRegistration= db.collection("forums").document(mForum.getDocId())
                 .collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
                     @Override
                     public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                         if(error !=null){
                             error.printStackTrace();
                             return;
                         }
                            comments.clear();
                         for (QueryDocumentSnapshot doc:value) {
                                Comment comment = doc.toObject(Comment.class);
                                comments.add(comment);
                         }
                     adapter.notifyDataSetChanged();
                         binding.textViewCommentsCount.setText(comments.size()+" comments");
                     }


                 });



        binding.buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.editTextComment.getText().toString();
                if(comment.isEmpty()){
                    binding.editTextComment.setError("Enter a comment");
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    DocumentReference docRef =db.collection("forums").document(mForum.getDocId())
                            .collection("comments").document();

                    HashMap<String, Object> data =new HashMap<>();
                    data.put("comment", comment);
                    data.put("ownerId", mAuth.getCurrentUser().getUid());
                    data.put("ownerName", mAuth.getCurrentUser().getDisplayName());
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("docId", docRef.getId());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                binding.editTextComment.setText("");

                            }else{
                                binding.editTextComment.setError("Error while submitting comment");

                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration !=null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CommentRowItemBinding ItemBinding = CommentRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CommentViewHolder(ItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
           Comment comment = comments.get(position);
           holder.setUpUI(comment);

        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder{
            CommentRowItemBinding mBinding;
            Comment mComment;

            public CommentViewHolder(CommentRowItemBinding ItemBinding) {
                super(ItemBinding.getRoot());
                this.mBinding = ItemBinding;

            }
            void setUpUI(Comment comment){
                this.mComment = comment;
                mBinding.textViewCommentText.setText(mComment.getComment());
                mBinding.textViewCommentCreatedBy.setText(mComment.getOwnerName());
                if(mComment.getCreatedAt() !=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    mBinding.textViewCommentCreatedAt.setText(sdf.format(mComment.getCreatedAt().toDate()));
                }else{
                    mBinding.textViewCommentCreatedAt.setText("");
                }

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("forums").document(mForum.getDocId())
                                .collection("comments").document(mComment.getDocId()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                        }else{
                                            Toast.makeText(getActivity(), "Error while deleting comment", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        }
    }
}