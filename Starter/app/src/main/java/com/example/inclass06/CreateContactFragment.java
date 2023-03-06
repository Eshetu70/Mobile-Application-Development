package com.example.inclass06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.inclass06.databinding.FragmentCreateContactBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CreateContactFragment extends Fragment {


    public CreateContactFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
  FragmentCreateContactBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding =FragmentCreateContactBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String name =binding.editTextName.getText().toString();
              String email =binding.editTextEmail.getText().toString();
              String phone =binding.editTextPhone.getText().toString();
              String type ="CELL";
              int selected = binding.radioGroup.getCheckedRadioButtonId();
              if(selected ==R.id.radioButtonHome){
                  type ="HOME";
              } else if(selected ==R.id.radioButtonOffice){
                  type ="OFFICE";
              }

              if(name.isEmpty()){
                  Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
              }else  if(email.isEmpty()){
                  Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
              }else  if(phone.isEmpty()){
                  Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_SHORT).show();
              } if(type.isEmpty()){
                    Toast.makeText(getActivity(), "Enter phone type", Toast.LENGTH_SHORT).show();
                }


                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("email",email)
                        .add("phone",phone)
                        .add("type",type)
                        .build();
                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/contact/json/create")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if(response.isSuccessful()){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mlistener.completeSubmit();
                                }
                            });

                        }else{

                        }

                    }
                });



            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.cancelContact();

            }
        });
    }

    CreateListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(CreateListener)context;
    }

    interface CreateListener{
        void completeSubmit();
        void cancelContact();
    }
}