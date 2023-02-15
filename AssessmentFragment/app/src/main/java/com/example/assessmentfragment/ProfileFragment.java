package com.example.assessmentfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assessmentfragment.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";
    private static final String ARG_PARAM2 = "param2";
    //UserAdapter adapter;

    // TODO: Rename and change types of parameters
    private Profile mProfile;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PROFILE, profile);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfile = (Profile) getArguments().getSerializable(ARG_PARAM_PROFILE );
            profile.add(mProfile);


            //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String[] temp ={"bob", "dike","kill"};
    FragmentProfileBinding binding;
    //adapterView adapter;
    ArrayList<Profile> profile = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentProfileBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }
    ListView listview;

  ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

           listview =binding.listview;
           //adapter = new UserAdapter(getActivity(), profile);
            adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, temp);
           listview.setAdapter(adapter);

        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.closeProfile();
            }
        });

    }

    ProfileFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(ProfileFragmentListener)context;
    }

    interface ProfileFragmentListener{
        void closeProfile();
    }

}