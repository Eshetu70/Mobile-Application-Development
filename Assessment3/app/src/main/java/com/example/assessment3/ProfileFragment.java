package com.example.assessment3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assessment3.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";
    //private static final String ARG_PARAM2 = "param2";

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
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfile= (Profile) getArguments().getSerializable(ARG_PARAM_PROFILE);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
        binding.textView7.setText("Eshetu Wekjira");
        binding.textViewGender.setText(mProfile.getGender());
        binding.textViewName.setText(mProfile.getName());

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
        mlistener =( ProfileFragmentListener)context;
    }

    interface ProfileFragmentListener{
        void closeProfile();
    }
}