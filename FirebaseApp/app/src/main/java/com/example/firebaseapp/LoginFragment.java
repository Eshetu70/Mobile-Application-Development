package com.example.firebaseapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    EditText editTextTextPassword,editTextTextEmailAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_login, container, false);
         getActivity().setTitle("Login");
        editTextTextPassword = view.findViewById(R.id.editTextTextPassword);
        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        view.findViewById(R.id.buttonlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String email =editTextTextEmailAddress.getText().toString();
           String password =editTextTextPassword.getText().toString();

           if(email.isEmpty()){
               Toast.makeText(getActivity(), "enter email", Toast.LENGTH_SHORT).show();
           }else if(password.isEmpty()){
               Toast.makeText(getActivity(), "enter password", Toast.LENGTH_SHORT).show();
           }else{
               mAuth =FirebaseAuth.getInstance();
               mAuth.signInWithEmailAndPassword(email,password)
                       .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   Log.d("demo", "onComplete: logged is successfully");
                                   getParentFragmentManager().beginTransaction()
                                           .replace(R.id.rootView, new MainFragment())
                                           .commit();


                               }else{
                                   Log.d("demo", "onComplete: logged is not successfully");

                               }
                           }
                       });
           }

            }
        });

        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             getParentFragmentManager().beginTransaction()
                     .replace(R.id.rootView, new RegisterFragment())
                     .commit();

            }
        });

       return  view;
    }
}