package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentCreateContactBinding;

public class CreateContactFragment extends Fragment {

    public CreateContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentCreateContactBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentCreateContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

             String name =binding.editTextName.getText().toString();
             String email =binding.editTextEmail.getText().toString();
             String phone =binding.editTextPhone.getText().toString();
            String type ="CELL";
            if(binding.radioGroup.getCheckedRadioButtonId()==R.id.radioButtonHome){
                type ="HOME";

            }else if(binding.radioGroup.getCheckedRadioButtonId()==R.id.radioButtonOffice){
                type ="OFFICE";
            }
            if(name.isEmpty()){
                Toast.makeText(getContext(), "Enter Name", Toast.LENGTH_SHORT).show();
            }else if(email.isEmpty()){
                Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            }else if(phone.isEmpty()){
                Toast.makeText(getContext(), "Enter Phone", Toast.LENGTH_SHORT).show();
            }


















    }
}