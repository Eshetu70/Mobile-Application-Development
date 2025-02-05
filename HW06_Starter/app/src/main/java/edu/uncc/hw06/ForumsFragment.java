package edu.uncc.hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

import edu.uncc.hw06.databinding.ForumRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumsBinding;

public class ForumsFragment extends Fragment {

    public ForumsFragment() {
        // Required empty public constructor
    }

    FragmentForumsBinding binding;

    ArrayList<Forum> mForums =new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ListenerRegistration listenerRegistration;
    ForumsAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new ForumsAdapter();
         binding.recyclerView.setAdapter(adapter);



        listenerRegistration = FirebaseFirestore.getInstance().collection("forums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    error.printStackTrace();
                    return;
                }
                mForums.clear();
                for (QueryDocumentSnapshot doc: value) {
                    Forum forum = doc.toObject(Forum.class);
                    mForums.add(forum);
                }
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonCreateForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewForum();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(listenerRegistration !=null){
            listenerRegistration.remove();

        }
    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumsViewHolder>{


        @NonNull
        @Override
        public ForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForumRowItemBinding vhBinding = ForumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForumsViewHolder(vhBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumsViewHolder holder, int position) {
          Forum forum = mForums.get(position);
          holder.setUpUid(forum);
        }

        @Override
        public int getItemCount() {
            return mForums.size();
        }

        class ForumsViewHolder extends RecyclerView.ViewHolder{
            ForumRowItemBinding mBinding;
            Forum mForum;
            public ForumsViewHolder(@NonNull ForumRowItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding =vhBinding;

            }

            public void deleteFirstComment(ArrayList<Comment> comments){
                if(comments.size() > 0 ){
                    Comment comment = comments.remove(0);
                   db.collection("forums").document(mForum.getDocId()).collection("comments")
                           .document(comment.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   deleteFirstComment(comments);
                               }
                           });
                } else {
                    db.collection("forums").document(mForum.getDocId()).delete();
                }
            }

            void setUpUid(Forum forum){
              this.mForum =forum;
              mBinding.textViewForumTitle.setText(mForum.getTitle());
              mBinding.textViewForumCreatedBy.setText(mForum.getOwnerName());
              mBinding.textViewForumText.setText(mForum.getDescription());
              //mBinding.textViewForumLikesDate.setText(mForum.getCreatedAt());

            if(mAuth.getCurrentUser().getUid().equalsIgnoreCase(mForum.getOwnerId())){
                mBinding.imageViewDelete.setVisibility(View.VISIBLE);

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        db.collection("forums").document(mForum.getDocId()).collection("comments")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        ArrayList<Comment> comments = new ArrayList<>();
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot doc:task.getResult()) {
                                                Comment comment =doc.toObject(Comment.class);
                                                comments.add(comment);
                                            }
                                            deleteFirstComment(comments);

                                        }else{

                                        }

                                    }
                                });



                    }
                });


            }else{
                mBinding.imageViewDelete.setVisibility(View.INVISIBLE);

            }
            String likes="0 likes";
            if(mForum.getLikes()!=null && mForum.getLikes().size()>0){
                likes= mForum.getLikes().size()+" likes";
                if(mForum.getLikes().contains((mAuth.getCurrentUser().getUid()))){
                    mBinding.imageViewLike.setImageResource(R.drawable.like_favorite);
                }else{
                    mBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }
            }else{
                mBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);

            }
                mBinding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mForum.getLikes() !=null){
                            if(mForum.getLikes().contains((mAuth.getCurrentUser().getUid()))){

                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("likes", FieldValue.arrayRemove(mAuth.getCurrentUser().getUid()));
                                FirebaseFirestore.getInstance().collection("forums").document(mForum.getDocId()).update(updateData);

                            }else{
                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("likes", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()));
                                FirebaseFirestore.getInstance().collection("forums").document(mForum.getDocId()).update(updateData);

                            }
                        }else{

                        }
                    }
                });

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm a");
                String date = simpleDateFormat.format(mForum.getCreatedAt().toDate());

               mBinding.textViewForumLikesDate.setText(likes + " | " +date);

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    mListener.gotoForum(mForum);
                    }
                });

            }
        }
    }

    ForumsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsListener) context;
    }

    interface ForumsListener {
        void createNewForum();
        void logout();
        void gotoForum(Forum forum);
    }
}