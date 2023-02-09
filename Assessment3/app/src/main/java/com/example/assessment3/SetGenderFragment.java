package com.example.assessment3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assessment3.databinding.FragmentSetGenderBinding;

public class SetGenderFragment extends Fragment {


    public SetGenderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentSetGenderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentSetGenderBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Set Gender");

        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected =binding.radioGroup.getCheckedRadioButtonId();
                String gender = "Female";
                if(selected==R.id.radioButtonMale){
                    gender ="Male";
                }
             mlistener.setgenderbacktoRegister(gender);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mlistener.cancelgender();
            }
        });

    }
    SetGenderFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(SetGenderFragmentListener)context;
    }

    interface SetGenderFragmentListener{
        void setgenderbacktoRegister(String gender);
        void cancelgender();
    }
}