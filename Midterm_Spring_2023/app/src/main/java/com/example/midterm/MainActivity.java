package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.midterm.models.Product;
import com.example.midterm.models.Review;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductsFragment.ProductListener, ReviewsFragment.ReviewListener, CreateReviewFragment.CreateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ProductsFragment())
                .commit();
    }

    @Override
    public void gotoReview(Product product) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ReviewsFragment.newInstance(product))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoCreateRew(Product product, ArrayList<Review> mReviews) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CreateReviewFragment.newInstance(product, mReviews))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoBack() {
        getSupportFragmentManager().popBackStack();
    }
}