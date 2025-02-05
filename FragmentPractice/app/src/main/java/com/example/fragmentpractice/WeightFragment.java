package com.example.fragmentpractice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentpractice.databinding.FragmentWeightBinding;


public class WeightFragment extends Fragment {
private double weight =0;
    public WeightFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWeightBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeightBinding.inflate(inflater,container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Set Weight");


        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  weight = Double.parseDouble(binding.editTextWeight.getText().toString());
                  if(weight==0.0 ){
                      Toast.makeText(getActivity(), "enter valid weight", Toast.LENGTH_SHORT).show();
                  }else {
                      mListener.setweightBackToMain(weight);
                  }
              }catch (NumberFormatException ex) {
                  Toast.makeText(getActivity(), "enter valid weight", Toast.LENGTH_SHORT).show();
              }

            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendCancel();

            }
        });
    }
    WeightFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(WeightFragmentListener)context;
    }

    interface WeightFragmentListener{
        void setweightBackToMain(double weight);
        void sendCancel();
    }

}