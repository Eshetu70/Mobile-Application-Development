package com.example.managing_scrollable_lists;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.managing_scrollable_lists.databinding.FragmentAppCategoriesBinding;

import java.util.ArrayList;


public class AppCategoriesFragment extends Fragment {



    public AppCategoriesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
     FragmentAppCategoriesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppCategoriesBinding.inflate(inflater, container, false);
        return  binding.getRoot();

    }
     ArrayAdapter<String>adapter;
    ArrayList<String> categories;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories =DataServices.getAppCategories();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categories);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("App Categories");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category =categories.get(i);
                mlistener.sendSelectedCategories(category);
            }
        });
    }

    AppCategoriesListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(AppCategoriesListener)context;
    }

    interface AppCategoriesListener{
        void sendSelectedCategories(String category);
     }
}