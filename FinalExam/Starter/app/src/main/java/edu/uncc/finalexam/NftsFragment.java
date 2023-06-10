package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import edu.uncc.finalexam.databinding.FragmentNftsBinding;
import edu.uncc.finalexam.databinding.NftRowItemBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NftsFragment extends Fragment {
    public NftsFragment() {
        // Required empty public constructor
    }

    FragmentNftsBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<NFT> mNfts = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ListenerRegistration listenerRegistration;
    NftAdapter adapter;
    ArrayList<UPNFT> upnfts = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNftsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("NFTs");

        binding.recyclerViewNfts.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NftAdapter();
        binding.recyclerViewNfts.setAdapter(adapter);
        getNfts();

        binding.buttonNftSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNftsAsc();
            }
        });

        binding.buttonNftSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNftsDesc();
            }
        });




        listenerRegistration = db.collection("favorites").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    error.printStackTrace();
                }
                mNfts.clear();
                for (QueryDocumentSnapshot doc: value) {
                   NFT nft = doc.toObject(NFT.class);
                    mNfts.add(nft);

                }
                adapter.notifyDataSetChanged();

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

    void sortNftsAsc(){
        Collections.sort(mNfts, new Comparator<NFT>() {
            @Override
            public int compare(NFT o1, NFT o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }
    void sortNftsDesc(){
        Collections.sort(mNfts, new Comparator<NFT>() {
            @Override
            public int compare(NFT o1, NFT o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            mListener.logout();
        }
        return super.onOptionsItemSelected(item);
    }


   public void getNfts() {
       Request request = new Request.Builder()
               .url("https://www.theappsdr.com/api/nfts")
               .build();

  client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
          e.printStackTrace();
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

          if(response.isSuccessful()){
              String body =response.body().string();
              try {
                  JSONObject jsonObject = new JSONObject(body);
                  JSONArray jsonArray =jsonObject.getJSONArray("assets");
                  mNfts.clear();
                  for (int i = 0; i < jsonArray.length(); i++) {
                      JSONObject jsonNft = jsonArray.getJSONObject(i);
                        NFT nft = new NFT(jsonNft);
                      mNfts.add(nft);
                  }
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         adapter.notifyDataSetChanged();
                     }
                 });



              } catch (JSONException e) {
                  throw new RuntimeException(e);
              }
          }else{

          }

      }
  });


   }

   class NftAdapter extends RecyclerView.Adapter<NftAdapter.NtfViewHolder>{
       @NonNull
       @Override
       public NtfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           NftRowItemBinding vhBinding = NftRowItemBinding.inflate(getLayoutInflater(), parent, false);
           return new NtfViewHolder(vhBinding);
       }

       @Override
       public void onBindViewHolder(@NonNull NtfViewHolder holder, int position) {
           NFT nft = mNfts.get(position);
//           UPNFT upnft =upnfts.get(position);
              holder.setupUI(nft);
//              holder.setUPNFT(upnft);

       }

       @Override
       public int getItemCount() {
           return mNfts.size();
       }

       class NtfViewHolder extends RecyclerView.ViewHolder{
           NftRowItemBinding mBinding;
           NFT mNft;
           UPNFT mUpnft;

            public NtfViewHolder(NftRowItemBinding vhBinding) {
                super(vhBinding.getRoot());
                this.mBinding = vhBinding;


                mBinding.imageViewAddRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mBinding.imageViewAddRemove.setTag(R.drawable.ic_plus);



                        if(mBinding.imageViewAddRemove.getTag().equals(R.drawable.ic_plus)) {

                            mBinding.imageViewAddRemove.setImageResource(R.drawable.ic_minus);
                            DocumentReference docRef = db.collection("favorites").document();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("name", mNft.getName());
                            data.put("collection_name", mNft.getCollection_name());
                            data.put("image_thumbnail_url", mNft.getImage_thumbnail_url());
                            data.put("banner_image_url", mNft.getBanner_image_url());
                            data.put("user_id", mAuth.getCurrentUser().getUid());
                            data.put("doc_id", docRef.getId());

                            docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else if(!mBinding.imageViewAddRemove.getTag().equals(R.drawable.ic_plus)){
                            mBinding.imageViewAddRemove.setImageResource(R.drawable.ic_plus);
                            DocumentReference docRef =db.collection("favorites").document(mNft.getDoc_id());
                            docRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                });



            }

           void setupUI(NFT nft){
                this.mNft = nft;
                mBinding.textViewNftName.setText(mNft.getName());
                mBinding.textViewCollectionName.setText(mNft.getCollection_name());
               Picasso.get().load(mNft.getImage_thumbnail_url()).into(mBinding.imageViewNftIcon);
               Picasso.get().load(mNft.getBanner_image_url()).into(mBinding.imageViewCollectionBanner);


           }


        }
   }
    NftsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NftsListener) context;
    }

    interface NftsListener {
        void logout();
    }
}