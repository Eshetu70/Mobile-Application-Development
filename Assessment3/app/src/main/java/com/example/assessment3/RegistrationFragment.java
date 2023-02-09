package com.example.assessment3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assessment3.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

  private String setGender;
  public void setSetGender(String gender){
      this.setGender =gender;
  }
  private String name;
  public void setName(String name){
      this.name =name;
  }
    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");

        binding.textViewSelectedGender.setText(setGender);
        binding.textView6.setText("Eshetu Wekjira");

        binding.buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoGender();

            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
              String gender =binding.textViewSelectedGender.getText().toString();
              if(setGender == null){
                  Toast.makeText(getActivity(), "selected Gender", Toast.LENGTH_SHORT).show();
              } else if (name ==null) {
                  Toast.makeText(getActivity(), "enter name", Toast.LENGTH_SHORT).show();
              }
              else{
                  Profile profile = new Profile(name, setGender);
                  mlistener.totoProfile(profile);
              }


            }
        });
    }
    RegistrationFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(RegistrationFragmentListener)context;
    }

    interface RegistrationFragmentListener{
        void gotoGender();
        void totoProfile(Profile profile);
    }
}