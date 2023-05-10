package com.example.finalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalexam.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLoginBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Login");

       binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email = binding.editTextEmail.getText().toString();
               String password = binding.editTextPassword.getText().toString();
               if(email.isEmpty() || password.isEmpty()){
                   binding.buttonLogin.setEnabled(false);
               } else {
                   binding.buttonLogin.setEnabled(true);

               }
               mAuth.signInWithEmailAndPassword(email, password)
                       .addOnCompleteListener(getActivity(), task -> {
                           if(task.isSuccessful()){
                           mlistener.authSuccessful();
                               //login successful
                               //show success message
                               Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                           } else {
                               //login failed
                               //show error message
                               Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                           }
                       });
           }
       });


       binding.buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mlistener.createNewAccountClicked();
           }
       });


    }
 LoginListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LoginListener){
            mlistener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement LoginListener");
        }
    }

    interface LoginListener{
        void createNewAccountClicked();
        void authSuccessful();
    }
}