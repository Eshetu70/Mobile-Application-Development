package edu.uncc.assessment06;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uncc.assessment06.databinding.CartRowItemBinding;
import edu.uncc.assessment06.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCartBinding binding;

    FirebaseAuth mAuth =FirebaseAuth.getInstance();
    ArrayList<Product> mProducts = new ArrayList<>();
    ProductAdapter adapter;
 ListenerRegistration listenerRegistration;
 Product product = new Product();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Cart");

     binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
     adapter = new ProductAdapter();
     binding.recyclerView.setAdapter(adapter);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        listenerRegistration=  db.collection("product")
                .whereEqualTo("own_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("demo", "Listen failed.", error);
                    return;
                }
                mProducts.clear();

                for (QueryDocumentSnapshot document : value) {
                   Product product =document.toObject(Product.class);
                    mProducts.add(product);
                    binding.textViewTotal.setText("Total Product "+ product.getPrice());
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
        }
    }







    class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{
     @NonNull
     @Override
     public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         CartRowItemBinding vhBinding = CartRowItemBinding.inflate(getLayoutInflater(), parent, false);
         return new ProductHolder(vhBinding);
     }

     @Override
     public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
       Product product = mProducts.get(position);
       holder.setUP(product);
     }

     @Override
     public int getItemCount() {
         return mProducts.size();
     }

     class ProductHolder extends RecyclerView.ViewHolder{
         CartRowItemBinding mBinding;
         Product mProduct;

            public ProductHolder(@NonNull CartRowItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding =vhBinding;
            }

            public void setUP(Product product){
                mProduct =product;
                mBinding.textViewProductPrice.setText(product.getPrice());
                mBinding.textViewProductName.setText(product.getName());
                Picasso.get().load(product.getImg_url()).into(mBinding.imageViewProductIcon);


                mBinding.imageViewDeleteFromCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("product").document(product.getPid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){



                                }else{

                                }

                            }
                        });
                    }
                });

            }
        }
 }
}