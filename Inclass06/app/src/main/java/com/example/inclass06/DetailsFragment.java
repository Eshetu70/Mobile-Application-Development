package com.example.inclass06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inclass06.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Contact mContant;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(Contact contact) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CONTACT, contact);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContant = (Contact) getArguments().getSerializable(ARG_PARAM_CONTACT);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater,container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Details");
        binding.textViewName.setText(mContant.getName());
        binding.textViewEmail.setText(mContant.getEmail());
        binding.textViewPhone.setText(mContant.getPhone());
        binding.textViewPhoneType.setText(mContant.getPhoneType());
    }
}