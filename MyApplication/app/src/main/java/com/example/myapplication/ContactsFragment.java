package com.example.myapplication;

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

import com.example.myapplication.databinding.ContactListItemBinding;
import com.example.myapplication.databinding.FragmentContactsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        binding =FragmentContactsBinding.inflate(inflater, container , false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ContactViewAdapter();
        binding.recyclerView.setAdapter(adapter);
        getContact();



        binding.buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoAddContact();

            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();
  ArrayList<Contact> contacts = new ArrayList<>();
    ContactViewAdapter adapter;

    void getContact(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                  if(response.isSuccessful()){
                      String body =response.body().string();


                      try {
                          JSONObject rootJson = new JSONObject(body);
                          JSONArray contactJsonArray =rootJson.getJSONArray("contacts");

                          for (int i = 0; i < contactJsonArray.length(); i++) {
                              JSONObject JsonContact =contactJsonArray.getJSONObject(i);
                              Contact contact = new Contact(JsonContact);
                              contacts.add(contact);

                              getActivity().runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      adapter.notifyDataSetChanged();

                                  }
                              });



                          }

                      } catch (JSONException e) {
                          throw new RuntimeException(e);
                      }


                  }else{

                  }
            }
        });

    }

    class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ContactViewHolder>{

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactViewHolder(ContactListItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
              Contact contact =contacts.get(position);
              holder.setUPContact(contact);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            ContactListItemBinding mBinding;
            Contact mContact;
            public ContactViewHolder(@NonNull ContactListItemBinding vhBinding ) {
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

                    }
                });
            }



            void setUPContact(Contact contact){
                this.mContact =contact;
                mBinding.textViewName.setText(mContact.getName());
                mBinding.textViewEmail.setText(mContact.getEmail());
                mBinding.textViewPhone.setText(mContact.getPhone());
                mBinding.textViewPhoneType.setText(mContact.getPhoneType());
            }
        }
    }
    ContactsListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener=(ContactsListener)context;
    }

    interface ContactsListener{
        void gotoAddContact();
        void gotoContactDetails(Contact mContact);
     }

}