package com.example.scrobablelistview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.scrobablelistview.databinding.FragmentDetailsBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_details = "ARG_PARAM_details";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private  DataServices.App mApp;
    //private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }



    public static DetailsFragment newInstance( DataServices.App app) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_details, app);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mApp = (DataServices.App) getArguments().getSerializable(ARG_PARAM_details);

        }
    }
    FragmentDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentDetailsBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;
    ArrayList<String> genres;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Apps Details");
        binding.textViewAppName.setText(mApp.getName());
        binding.textViewArtistName.setText(mApp.getArtistId());
        binding.textViewReleaseDate.setText(mApp.getReleaseDate());
        genres =mApp.getGenres();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,genres);
        binding.listView.setAdapter(adapter);

    }
}