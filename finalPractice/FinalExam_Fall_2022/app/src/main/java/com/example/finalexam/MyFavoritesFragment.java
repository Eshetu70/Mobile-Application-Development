package com.example.finalexam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.databinding.FragmentMyFavoritesBinding;
import com.example.finalexam.databinding.ListItemPhotoFavoriteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyFavoritesFragment extends Fragment {

    public MyFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentMyFavoritesBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Photo> mPhotos = new ArrayList<>();
    PhotoFavoriteAdapter adapter;
    ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Favorites");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PhotoFavoriteAdapter();
        binding.recyclerView.setAdapter(adapter);



    listenerRegistration =  db.collection("favorites").whereEqualTo("userId", mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

              if(error !=null){
                  error.printStackTrace();
              }
              mPhotos.clear();
              for (QueryDocumentSnapshot doc: value) {
                  Photo photo = doc.toObject(Photo.class);
                  mPhotos.add(photo);

              }
              adapter.notifyDataSetChanged();

          }
      });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    class PhotoFavoriteAdapter extends RecyclerView.Adapter<PhotoFavoriteAdapter.PhotoFavoriteViewHolder>{
     @NonNull
     @Override
     public PhotoFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ListItemPhotoFavoriteBinding vhBinding = ListItemPhotoFavoriteBinding.inflate(getLayoutInflater(), parent, false);
         return new PhotoFavoriteViewHolder(vhBinding );
     }

     @Override
     public void onBindViewHolder(@NonNull PhotoFavoriteViewHolder holder, int position) {
            Photo photo = mPhotos.get(position);
            holder.setUpView(photo);
//            holder.mBinding.tvTitle.setText(photo.getTitle());
//            holder.mBinding.tvOwner.setText(photo.getOwner());
//            Picasso.get().load(photo.getUrl()).into(holder.mBinding.ivPhoto);

     }

     @Override
     public int getItemCount() {
         return mPhotos.size();
     }

     class PhotoFavoriteViewHolder extends RecyclerView.ViewHolder{
         ListItemPhotoFavoriteBinding mBinding;
         Photo mPhoto;
              public PhotoFavoriteViewHolder(ListItemPhotoFavoriteBinding vhBinding) {
                super(vhBinding.getRoot());
                this.mBinding = vhBinding;


                mBinding.imageViewThumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onPhotoSelected(mPhoto);

                    }
                });


                mBinding.imageViewTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("favorites").document(mPhoto.getDocId()).delete();
                    }
                });
            }
            void setUpView(Photo photo){
                 this.mPhoto = photo;
                mBinding.textViewDescription.setText(mPhoto.getDescription());
                mBinding.textViewUserFullName.setText(mPhoto.getUsername());
                mBinding.imageViewThumbnail.setImageURI(Uri.parse(mPhoto.getThumb()));
                mBinding.textViewCreatedAt.setText(mPhoto.getCreated_at());
                Picasso.get().load(mPhoto.getThumb()).into(mBinding.imageViewThumbnail);
                Picasso.get().load(mPhoto.getProfile_image()).into(mBinding.imageViewUserThumbnail);
            }
        }
 }
    OnPhotoSelectedListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnPhotoSelectedListener) context;
    }

    interface OnPhotoSelectedListener{
     void onPhotoSelected(Photo photo);
 }
}