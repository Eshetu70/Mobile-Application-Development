package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inclass04.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

private String selectedDepartment =null;
public void setSelectedDepartment(String department) {
    this.selectedDepartment = department;
}

    public RegistrationFragment() {
        // Required empty public constructor
    }

FragmentRegistrationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentRegistrationBinding.inflate(inflater, container,false);
        return  binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");


     binding.buttonSelectDept.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           mListener.gotoDepartment();
         }
     });
     binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String name =binding.editTextName.getText().toString();
             String email =binding.editTextEmail.getText().toString();
             String id =binding.editTextID.getText().toString();
             if(name.isEmpty()){
                 Toast.makeText(getActivity(), "enter valid name", Toast.LENGTH_SHORT).show();
             } else if (email.isEmpty()) {
                 Toast.makeText(getActivity(), "enter email", Toast.LENGTH_SHORT).show();

             } else if (id.isEmpty()) {
                 Toast.makeText(getActivity(), "enter Id", Toast.LENGTH_SHORT).show();
             }
             else if(selectedDepartment == null){
                 Toast.makeText(getActivity(), "Select a department!", Toast.LENGTH_SHORT).show();
             }else {
                 Profile profile =new Profile(name,email,id,selectedDepartment);
                 mListener.gotoProfile(profile);
             }
         }
     });

        if(selectedDepartment == null){
            binding.textViewSelectedDept.setText("");
        } else {
            binding.textViewSelectedDept.setText(selectedDepartment);
        }
    }
    RegistrationFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(RegistrationFragmentListener)context;
    }

    interface  RegistrationFragmentListener{
        void gotoDepartment();
        void gotoProfile(Profile profile);
    }
}