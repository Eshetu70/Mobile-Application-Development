package com.example.assessmentfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assessmentfragment.databinding.FragmentSetWeightBinding;

public class SetWeightFragment extends Fragment {

 private double weight;
    public SetWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
   FragmentSetWeightBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetWeightBinding.inflate(inflater,container, false);
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

                    if(weight ==0){
                        Toast.makeText(getActivity(), "enter weight", Toast.LENGTH_SHORT).show();
                     }else{
                         mlistener.setBackWeight(weight);
                     }

                }catch (NumberFormatException ex){
                    Toast.makeText(getActivity(), "Enter Correct Weight", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mlistener.cancelWeight();
            }
        });

    }
    SetWeightFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(SetWeightFragmentListener)context;
    }

    interface SetWeightFragmentListener{
        void setBackWeight(double weight);
        void cancelWeight();
    }
}