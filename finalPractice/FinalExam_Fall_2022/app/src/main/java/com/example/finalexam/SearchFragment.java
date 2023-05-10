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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalexam.databinding.FragmentSearchBinding;
import com.example.finalexam.databinding.ListItemPhotoSearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSearchBinding binding;
    OkHttpClient client = new OkHttpClient();
    PhotoAdapter adapter;
    ArrayList<Photo> photoList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Search");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PhotoAdapter();
        binding.recyclerView.setAdapter(adapter);
        String query = binding.editTextSearchKeyword.getText().toString();
       getSearchResults(query);


       binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                String query = binding.editTextSearchKeyword.getText().toString();
                getSearchResults(query);
           }
       });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout_menu_item){
            //logout code goes here ..
            //
            mlistener.logout();

            return true;
        } else if(item.getItemId() == R.id.my_favorites_menu_item){
            //my favorites code goes here ...
            mlistener.myFavorites();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void getSearchResults(String query){
        //base url is https://api.unsplash.com/search/photos/?client_id=your-acess-key&query=user-entered-keywords&per_page=50&orientation=landscape&content_filter=high

        //use the client to make a request to the api
        //use the response to get the json data

        //use the json data to create a list of Photo objects
        //use the list of Photo objects to create a PhotoAdapter
        //use the PhotoAdapter to set the adapter for the RecyclerView

        //use the RecyclerView to display the list of photos

        //use the RecyclerView to set the layout manager
        Request request = new Request.Builder()
                .url("https://api.unsplash.com/search/photos/?client_id=OFBXIX6_Q3Lf3ZHlnUIgD0fQ0bNdk7OLJ7ooY1-6CC0&query="+query+"&per_page=50&orientation=landscape&content_filter=high")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        photoList.clear();
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject photoObject = jsonArray.getJSONObject(i);
                            Photo photo = new Photo(photoObject);
                            photoList.add(photo);
                        }



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                     adapter.notifyDataSetChanged();



                        }
                    });
                }

            }
        });
    }

class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPhotoSearchBinding vhBinding =ListItemPhotoSearchBinding.inflate(getLayoutInflater(), parent, false);

        return new PhotoViewHolder(vhBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo =photoList.get(position);
        holder.setUP(photo);


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{
        ListItemPhotoSearchBinding mBinding;
        Photo mphoto;

            public PhotoViewHolder(ListItemPhotoSearchBinding vhBinding) {
                super(vhBinding.getRoot());
                this.mBinding =vhBinding;


                mBinding.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       DocumentReference docRef = db.collection("favorites").document();
                        HashMap<String, Object>data = new HashMap<>();
                        data.put("id", mphoto.getId());
                        data.put("description", mphoto.getDescription());
                        data.put("thumb", mphoto.getThumb());
                        data.put("profile_image", mphoto.profile_image);
                        data.put("username", mphoto.getUsername());
                        data.put("created_at", mphoto.getCreated_at());
                        data.put("userId", mAuth.getCurrentUser().getUid());
                        data.put("docId", docRef.getId());
                        data.put("html", mphoto.getHtml());
                       docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){

                                   mlistener.myFavorites();
                               }else{
                                   Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });



                    }
                });

            }

            void setUP(Photo photo){
                this.mphoto = photo;
                mBinding.textViewDescription.setText(mphoto.getDescription());
                mBinding.textViewUserFullName.setText(mphoto.getUsername());
                mBinding.imageViewThumbnail.setImageURI(Uri.parse(mphoto.getThumb()));
                mBinding.textViewCreatedAt.setText(mphoto.getCreated_at());
                Picasso.get().load(mphoto.getThumb()).into(mBinding.imageViewThumbnail);
                Picasso.get().load(mphoto.getProfile_image()).into(mBinding.imageViewUserThumbnail);



            }
        }
}
    SearchListener mlistener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener = (SearchListener) context;
    }

    interface SearchListener{
        void logout();
        void myFavorites();
    }
}