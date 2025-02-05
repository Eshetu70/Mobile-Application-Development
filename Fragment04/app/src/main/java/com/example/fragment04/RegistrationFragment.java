package com.example.fragment04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragment04.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

 private String selectDepartment;
 public void setSelectDepartment(String dept)
 {
     this.selectDepartment =dept;
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
        binding =FragmentRegistrationBinding.inflate(inflater, container, false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewSelectedDept.setText(selectDepartment);

        binding.buttonSelectDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoDepartment();
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String id = binding.editTextID.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "enter name", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "enter email", Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()){
                    Toast.makeText(getActivity(), "enter id", Toast.LENGTH_SHORT).show();
                }else if(selectDepartment ==null){
                    Toast.makeText(getActivity(), "set up department", Toast.LENGTH_SHORT).show();
                }
                else{
                    Profile profile = new Profile(name, email, id, selectDepartment);
                    mlistener.gotoProfile(profile);
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
        void gotoDepartment();
        void gotoProfile(Profile profile);
    }
}