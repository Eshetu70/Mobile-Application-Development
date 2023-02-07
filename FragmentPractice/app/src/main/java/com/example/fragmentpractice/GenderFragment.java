package com.example.fragmentpractice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentpractice.databinding.FragmentGenderBinding;

public class GenderFragment extends Fragment {

    public GenderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
     FragmentGenderBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentGenderBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gender");

        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected =binding.radioGroupGender.getCheckedRadioButtonId();
                String gender ="Male";
                if(selected == R.id.radioButtonFemale){
                  gender ="Female" ;
                }
                mlistener.setGender(gender);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.setCancelGender();
            }
        });
    }
    GenderFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(GenderFragmentListener)context;
    }
    interface GenderFragmentListener{
        void setGender(String gender);
        void setCancelGender();
    }
}