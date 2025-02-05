package com.example.inclass06_2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inclass06_2.databinding.FragmentCreateContactBinding;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentCreateContactBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Contact");



       binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               String name =binding.editTextName.getText().toString();
               String email =binding.editTextEmail.getText().toString();
               String phone =binding.editTextPhone.getText().toString();
               String type ="CELL";
               if(binding.radioGroup.getCheckedRadioButtonId()==R.id.radioButtonHome){
                   type ="HOME";
               }else if(binding.radioGroup.getCheckedRadioButtonId()==R.id.radioButtonHome){
                   type ="OFFICE";
               }
               if(name.isEmpty()){
                   Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
               }else if(email.isEmpty()){
                   Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();

               }else if(phone.isEmpty()){
                   Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_SHORT).show();

               }
               createContact(name, email,phone,type);
               mlistener.submitContact();

           }
       });
       binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mlistener.cancelContaact();

           }
       });

    }


    void createContact(String name, String email, String phone, String phoneType){
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email",email)
                .add("phone",phone)
                .add("phoneType",phoneType)
                .build();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/create")
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              if(response.isSuccessful()){
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          mlistener.submitContact();
                      }
                  });


              }else{
                  String body =response.body().string();
                  try {
                      JSONObject jsonObject = new JSONObject(body);
                      String  message =jsonObject.getString("message");
                      getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                          }
                      });

                  } catch (JSONException e) {
                      throw new RuntimeException(e);
                  }

              }
            }
        });

    }


    CreateContactFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =( CreateContactFragmentListener)context;

    }
    interface CreateContactFragmentListener{
        void cancelContaact();
        void submitContact();
    }
}