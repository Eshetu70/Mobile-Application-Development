package com.example.inclass06_2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass06_2.databinding.ContactListItemBinding;
import com.example.inclass06_2.databinding.FragmentContactsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ContactsFragment extends Fragment {



    public ContactsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentContactsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contacts");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ContactAdapter();
        binding.recyclerView.setAdapter(adapter);
       getContact();;



       binding.buttonAddContact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               mlistener.gotoAddContact();
           }
       });
    }
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Contact> contacts = new ArrayList<>();
    ContactAdapter adapter;

    void getContact(){
        contacts.clear();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               if(response.isSuccessful()){
                 String body =response.body().string();
                   try {
                       JSONObject rootJson =new JSONObject(body);
                       JSONArray contactsJsonArray =rootJson.getJSONArray("contacts");
                       contacts.clear();
                       for (int i = 0; i < contactsJsonArray.length(); i++) {
                           JSONObject contactsJsonObject = contactsJsonArray.getJSONObject(i);
                            Contact contact = new Contact(contactsJsonObject);
                            contacts.add(contact);
                       }
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               adapter.notifyDataSetChanged();
                           }
                       });

                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }

               }else {

               }
            }
        });
    }


    void deleteContact(String Cid){

        RequestBody formBody = new FormBody.Builder()
                .add("id", Cid)
                .build();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/delete")
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
                            getContact();
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

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactViewHolder(ContactListItemBinding.inflate(getLayoutInflater(),parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
          Contact contact =contacts.get(position);
          holder.setupUI(contact);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            ContactListItemBinding mBinding;
            Contact mContact;

            public ContactViewHolder(@NonNull ContactListItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding =vhBinding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    mlistener.gotoContactDetails(mContact);

                    }
                });

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteContact(mContact.getCid());

                    }
                });

            }
            public  void setupUI(Contact contact){
                this.mContact =contact;
                mBinding.textViewName.setText(mContact.getName());
                mBinding.textViewEmail.setText(mContact.getEmail());
                mBinding.textViewPhone.setText(mContact.getPhone());
                mBinding.textViewPhoneType.setText(mContact.getPhoneType());
            }
        }


    }

    ContactsFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener=(ContactsFragmentListener)context;
    }

    interface ContactsFragmentListener{
        void gotoAddContact();
        void gotoContactDetails(Contact contact);

  }
















}