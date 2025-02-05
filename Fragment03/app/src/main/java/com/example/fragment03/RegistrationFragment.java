package com.example.fragment03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragment03.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {
  Profile profile;
  private String selectedDeparment;
  public void SetSectedDepartment(String department){
      this.selectedDeparment =department;
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
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");
         binding.textViewSelectedDept.setText(selectedDeparment );

        binding.buttonSelectDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.setDepartment();

            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =binding.editTextName.getText().toString();
                String email =binding.editTextEmail.getText().toString();
                String id = binding.editTextID.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter email", Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()){
                    Toast.makeText(getActivity(), "Enter ID", Toast.LENGTH_SHORT).show();
                }else if(selectedDeparment ==null){
                    Toast.makeText(getActivity(), "select department", Toast.LENGTH_SHORT).show();
                }else {
                    Profile profile = new Profile(name, email, id, selectedDeparment);
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
        void setDepartment();
        void gotoProfile(Profile profile);
    }
}