package edu.uncc.hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.hw06.databinding.ForumRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumsBinding;

public class ForumsFragment extends Fragment {

    public ForumsFragment() {
        // Required empty public constructor
    }

    FragmentForumsBinding binding;
    ForumsAdapter adapter;
    ListenerRegistration listenerRegistration;
    ArrayList<Forum> mForums = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ForumsAdapter();
        binding.recyclerView.setAdapter(adapter);

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        listenerRegistration= db.collection("forums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    error.printStackTrace();
                    return;
                }
                mForums.clear();
                for (QueryDocumentSnapshot doc: value) {
                    Forum forum =doc.toObject(Forum.class);
                    mForums.add(forum);

                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration!=null){
            listenerRegistration.remove();
            listenerRegistration=null;
        }
    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumsViewHolder>{

        @NonNull
        @Override
        public ForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForumRowItemBinding itemBinding = ForumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForumsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumsViewHolder holder, int position) {
            Forum forum = mForums.get(position);
            holder.setUPUId(forum);

        }

        @Override
        public int getItemCount() {
            return mForums.size();
        }

        class ForumsViewHolder extends RecyclerView.ViewHolder {
            ForumRowItemBinding mBinding;
            Forum mForum;
            public ForumsViewHolder(ForumRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.mBinding =itemBinding;


            }


            void setUPUId(Forum forum){
                this.mForum =forum;
                mBinding.textViewForumTitle.setText(mForum.getTitle());
                mBinding.textViewForumCreatedBy.setText(mForum.getCreatedByName());
                mBinding.textViewForumText.setText(mForum.getDescription());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm a");
                String date = simpleDateFormat.format(mForum.getCreatedAt().toDate());
                mBinding.textViewForumLikesDate.setText(date);
              if(mForum.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                  mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("forums").document(mForum.getDocId()).collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for(QueryDocumentSnapshot doc: task.getResult()){
                                        db.collection("forums").document(mForum.getDocId()).collection("comments").document(doc.getId()).delete();
                                    }
                                    db.collection("forums").document(mForum.getDocId()).delete();
                                }
                            });
                        }
                    });
              }else {
                  mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
              }
              String likes = "0 likes";
            if(mForum.getLikes()!=null && mForum.getLikes().size()>0){
                likes = mForum.getLikes().size()+" likes";


                if(mForum.getLikes().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    mBinding.imageViewLike.setImageResource(R.drawable.like_favorite);
            }else{
                    mBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }

            }else {
                mBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);
            }
            mBinding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mForum.getLikes()!=null && mForum.getLikes().size()>0){
                        if(mForum.getLikes().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            mForum.getLikes().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }else{
                            mForum.getLikes().add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }else {
                        mForum.setLikes(new ArrayList<String>());
                        mForum.getLikes().add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("forums").document(mForum.getDocId()).set(mForum);
                }
            });
            mBinding.textViewForumLikesDate.setText(likes+" | "+date);

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
        void gotoForum(Forum forum);
        void logout();
    }
}