package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.midterm.databinding.FragmentReviewsBinding;
import com.example.midterm.databinding.RowItemReviewBinding;
import com.example.midterm.models.Product;
import com.example.midterm.models.Review;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_PRODUCT = "ARG_PARAM_PRODUCT";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Product mProduct;
    private String mParam2;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(Product product) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PRODUCT,product);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PARAM_PRODUCT);

        }
    }

    FragmentReviewsBinding binding;
    ArrayList<Review> mReviews = new ArrayList<>();

    private final OkHttpClient client = new OkHttpClient();
    Review review1 = new Review();
    ReviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Product Review");

        adapter = new ReviewAdapter(getActivity(), R.layout.row_item_review, mReviews);
        binding.listView.setAdapter(adapter);

          binding.textViewProductName.setText(mProduct.getName());
          binding.textViewProductPrice.setText(mProduct.getPrice());
          Picasso.get().load(mProduct.getImg_url()).into(binding.imageViewProductIcon);
           getReview();

           binding.buttonCreateReview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mlistener.gotoCreateRew(mProduct);

               }
           });
    }

    void getReview(){

        //https://www.theappsdr.com/api/product/reviews/31b100f4-ec80-4ef7-8ba9-05575e54499f

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/product/reviews/"+ mProduct.getPid())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mReviews.clear();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("reviews");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject reviewJsonObject =jsonArray.getJSONObject(i);
                            Review review = new Review(reviewJsonObject);
                            mReviews.add(review);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();

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

    class ReviewAdapter extends ArrayAdapter<Review> {
        public ReviewAdapter(@NonNull Context context, int resource, @NonNull List<Review> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            RowItemReviewBinding mBinding;
            if(convertView==null){
                mBinding =RowItemReviewBinding.inflate(getLayoutInflater(), parent, false);
                convertView=mBinding.getRoot();
                convertView.setTag(mBinding);
            }else{
                mBinding = (RowItemReviewBinding) convertView.getTag();
            }

            Review review =getItem(position);
            mBinding.textViewReview.setText(review.getReview());
            mBinding.textViewReviewDate.setText(review.getCreated_at());
            Picasso.get().load(review.getReview()).into(mBinding.imageViewReviewRating);

          if(review.getRating().equals("1")){
              Picasso.get().load(R.drawable.stars_1).into(mBinding.imageViewReviewRating);
            }else if(review.getRating().equals("2")){
              Picasso.get().load(R.drawable.stars_2).into(mBinding.imageViewReviewRating);
            }else if(review.getRating().equals("3")){
                Picasso.get().load(R.drawable.stars_3).into(mBinding.imageViewReviewRating);
            }else if(review.getRating().equals("4")){
                Picasso.get().load(R.drawable.stars_4).into(mBinding.imageViewReviewRating);
            }else if(review.getRating().equals("5")){
                Picasso.get().load(R.drawable.stars_5).into(mBinding.imageViewReviewRating);

          }

            return convertView;
        }
    }

    ReviewListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(ReviewListener)context;
    }

    interface ReviewListener{
        void gotoCreateRew(Product product);
    }
}