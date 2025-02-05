package com.example.weather;

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

import com.example.weather.databinding.FragmentCitiesBinding;


public class CitiesFragment extends Fragment {


    public CitiesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentCitiesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentCitiesBinding.inflate(inflater, container, false);
        return  binding.getRoot();

    }
    ArrayAdapter<DataService.City> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DataService.cities);
        binding.listView.setAdapter(adapter);


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mlistener.OncitySelected(adapter.getItem(i));

            }
        });

    }
    CitiesFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mlistener=( CitiesFragmentListener)context;
    }

    interface CitiesFragmentListener{
        void OncitySelected(DataService.City city);
    }
}