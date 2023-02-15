package com.example.assessmentfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.assessmentfragment.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {
   UserAdapter adapter;
   private double selectedWeight;
   public void setSelectedWeight(double weight){
       this.selectedWeight = weight;
   }
   private String selectedGender ="N/A";
   public void setSelectedGender(String gender){
       this.selectedGender =gender;
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
        binding =FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Main");
        binding.textViewYourName.setText("Eshetu Wekjira");

        if ((selectedWeight ==0) &&(selectedGender =="N/A") )
        {
            binding.textViewWeight.setText("N/A");
            binding.textViewGender.setText("N/A");
        }else {
            binding.textViewWeight.setText(String.valueOf(selectedWeight)+" lbs");
            binding.textViewGender.setText(selectedGender);
        }

        binding.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoWeight();

            }
        });
        binding.buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoGender();

            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String gender =binding.textViewGender.getText().toString();
                     if (selectedWeight ==0) {
                        Toast.makeText(getActivity(), "enter Weight", Toast.LENGTH_SHORT).show();
                    }
                    else if(gender =="N/A"){
                        Toast.makeText(getActivity(), "Enter gender", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Profile profile = new Profile(selectedWeight, gender);
                         mlistener.submitToProfile(profile);
                     }
                }catch (NumberFormatException ex){
                    Toast.makeText(getActivity(), "enter weight", Toast.LENGTH_SHORT).show();
                }



            }
        });
        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textViewGender.setText("N/A");
                binding.textViewWeight.setText("N/A");
                mlistener.reset();

            }
        });
    }
    MainFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =( MainFragmentListener)context;
    }

    interface MainFragmentListener{
        void gotoWeight();
        void gotoGender();
        void submitToProfile(Profile profile);
        void reset();

    }
}