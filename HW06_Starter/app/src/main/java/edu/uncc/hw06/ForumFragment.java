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

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Comment> mComments = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    CommentAdapter adapter;
    private String mParam2;
    public ForumFragment() {
        // Required empty public constructor
    }


    public static ForumFragment newInstance(Forum forum) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FORUM, forum);

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


        listenerRegistration = db.collection("forums").document(mForum.getDocId())
                .collection("comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    error.printStackTrace();
                    return;
                }
                mComments.clear();
                for (QueryDocumentSnapshot doc: value) {
                   Comment comment = doc.toObject(Comment.class);
                    mComments.add(comment);
                }
                binding.textViewCommentsCount.setText(mComments.size()+" Comments");
                adapter.notifyDataSetChanged();

            }
        });


        binding.textViewForumTitle.setText(mForum.getTitle());
        binding.textViewForumCreatedBy.setText(mForum.getOwnerName());
        binding.textViewForumText.setText(mForum.getDescription());


        binding.buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.editTextComment.getText().toString();
                if(comment.isEmpty()){
                    Toast.makeText(getActivity(), "Enter comment", Toast.LENGTH_SHORT).show();
                }else{

                    DocumentReference docRef = db.collection("forums")
                            .document(mForum.getDocId())
                            .collection("comments").document();

                    HashMap<String, Object> data = new HashMap<>();

                    data.put("ownerId", mAuth.getCurrentUser().getUid());
                    data.put("ownerName", mAuth.getCurrentUser().getDisplayName());
                    data.put("comment", comment);
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("docId",docRef.getId());

                  docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){

                              binding.editTextComment.setText("");
//                              adapter.notifyDataSetChanged();
                          }else{
                              Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
        if(listenerRegistration !=null);
        listenerRegistration.remove();
    }

    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CommentRowItemBinding vhBinding = CommentRowItemBinding.inflate(getLayoutInflater(), parent, false);


            return new CommentViewHolder(vhBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
                 Comment comment = mComments.get(position);
                 holder.setUpUI(comment);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder{
            CommentRowItemBinding mBinding;
            Comment mComment;

            public CommentViewHolder(@NonNull CommentRowItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding = vhBinding;
            }

            void setUpUI(Comment comment){
                this.mComment =comment;

                mBinding.textViewCommentCreatedBy.setText(mComment.getOwnerName());
                mBinding.textViewCommentText.setText(mComment.getComment());
                if(mComment.getCreatedAt() !=null){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm a");
                    mBinding.textViewCommentCreatedAt.setText(simpleDateFormat.format(mComment.getCreatedAt().toDate()));

                }else{
                    mBinding.textViewCommentCreatedAt.setText("");
                }


            if(mComment.getOwnerId().equalsIgnoreCase(mAuth.getCurrentUser().getUid())){
                mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("forums").document(mForum.getDocId()).collection("comments")
                                .document(mComment.getDocId()).delete();
                    }
                });
            }else{
                mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
            }
            }
        }
    }


}