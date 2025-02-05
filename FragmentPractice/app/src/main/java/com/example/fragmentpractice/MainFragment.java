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

import com.example.fragmentpractice.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    Profile profile;
    private double setWeightvalues =0.0;
    private double values =0;
    private  String setGender ="N/A";
    public void setSetGender(String gender){
        this.setGender =gender;
    }
    public void setSetWeightvalues(double values){
        this.setWeightvalues =values;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
   FragmentMainBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =   FragmentMainBinding.inflate(inflater,container, false);
        return  binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Main");

        binding.textViewYourName.setText("Eshetu Wekjira");
          if(setWeightvalues ==0 && setGender=="N/A"){
              binding.textViewWeight.setText("N/A");
              binding.textViewGender.setText("N/A");
           }else{
              binding.textViewWeight.setText(String.valueOf(setWeightvalues)+" lbs");
              binding.textViewGender.setText(setGender);
        }
        binding.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setWeight();
            }
        });
        binding.buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mListener.setGender();
            }
        });
    binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         try {
             String gender = binding.textViewGender.getText().toString();
             if( setWeightvalues == 0){
                 Toast.makeText(getActivity(), "set up weitht", Toast.LENGTH_SHORT).show();
             }
             else if(gender=="N/A"){
                 Toast.makeText(getActivity(), "select gender", Toast.LENGTH_SHORT).show();
             }
             else {
                 Profile profile = new Profile(setWeightvalues, gender);
                 mListener.setSubmit(profile);
             }
         }catch (Exception ex){
             Toast.makeText(getActivity(), "get profiles", Toast.LENGTH_SHORT).show();
         }

           }
    });
    binding.buttonReset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.textViewGender.setText("N/A");
            binding.textViewWeight.setText("N/A");
            mListener.setReset();
        }
    });
    }
    MainFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(MainFragmentListener)context;
    }

    interface MainFragmentListener{
        void setWeight();
        void setGender();
        void setReset();

        void setSubmit(Profile profile);
    }
}