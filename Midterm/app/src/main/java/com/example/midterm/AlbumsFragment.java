package com.example.midterm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.midterm.databinding.AlbumRowItemBinding;
import com.example.midterm.databinding.FragmentAlbumsBinding;
import com.example.midterm.models.Album;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlbumsFragment extends Fragment {
    public AlbumsFragment() {
        // Required empty public constructor
    }

    FragmentAlbumsBinding binding;
    AlbumAdabter adabter;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentAlbumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Album> mAlbums = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Albums");

         adabter =new AlbumAdabter();
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         binding.recyclerView.setAdapter(adabter);

        getAlbam();


    }

    void getAlbam(){
       //

        Request request = new Request.Builder()
                .url("https://rss.applemarketingtools.com/api/v2/us/music/most-played/25/albums.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    String body = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonAlbum = jsonArray.getJSONObject(i);
                            Album album = new Album(jsonAlbum);

                            mAlbums.add(album);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    adabter.notifyDataSetChanged();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else{

                }


            }
        });
    }

    class AlbumAdabter extends RecyclerView.Adapter<AlbumAdabter.AlbumViewHolder>{
        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            AlbumRowItemBinding vhBinding =AlbumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new AlbumViewHolder(vhBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
         Album album = mAlbums.get(position);
         holder.setUpUI(album);
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }

        class AlbumViewHolder extends RecyclerView.ViewHolder{
            AlbumRowItemBinding mBinding;
            Album malbum;
            public AlbumViewHolder(@NonNull AlbumRowItemBinding vhBinding ) {
                super(vhBinding.getRoot());
                this.mBinding=vhBinding;


            }
      void setUpUI(Album album){
                this.malbum =album;
          Picasso.get().load(album.getArtworkUrl100()).into(mBinding.imageViewAlbumIcon);
          mBinding.textViewArtistName.setText(album.getArtistName());
          mBinding.textViewGenres.setText((CharSequence) album.getGenres());
          mBinding.textViewReleaseDate.setText(album.getReleaseDate());
      }
        }
    }
}