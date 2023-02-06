package com.example.practiceactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.practiceactivity.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {

   private  String selecedDepartment =null;
    public void setSelecedDepartment(String department){
        this.selecedDepartment =department;
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
        binding = FragmentRegistrationBinding.inflate(inflater,container, false);
        return binding.getRoot();
        // Inflate the layout for this fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registation");
        if(selecedDepartment==null){
            binding.textViewSelectedDept.setText("");
        }else {
            binding.textViewSelectedDept.setText(selecedDepartment);
        }

        binding.buttonSelectDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendDepartment();

            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String name =binding.editTextName.getText().toString();
              String email = binding.editTextEmail.getText().toString();
              String id =binding.editTextID.getText().toString();
              if(name.isEmpty()){
                  Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
              }else  if(email.isEmpty()){
                  Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
              }else  if(id.isEmpty()){
                  Toast.makeText(getActivity(), "Enter id", Toast.LENGTH_SHORT).show();
              } else  if(selecedDepartment ==null){
                  Toast.makeText(getActivity(), "picked dept", Toast.LENGTH_SHORT).show();
              }
                else {
                     Profile profile = new Profile(name, email, id, selecedDepartment);
                  mListener.gotoProfile(profile);
              }


            }
        });
    }
    RegistrationFragmentListner mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(RegistrationFragmentListner)context;
    }

    interface RegistrationFragmentListner{
        void sendDepartment();
        void gotoProfile(Profile profile);
    }
}