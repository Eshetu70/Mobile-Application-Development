package com.example.managing_scrollable_lists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.managing_scrollable_lists.databinding.FragmentAppDetailsBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DataServices.App mApp;
    private String mParam2;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AppDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_APP, app);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mApp = (DataServices.App) getArguments().getSerializable(ARG_PARAM_APP);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
 FragmentAppDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentAppDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    ArrayList<String> genres;
    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("App Details");
        binding.textViewAppName.setText(mApp.getName());
        binding.textViewArtistName.setText(mApp.getArtistName());
        binding.textViewReleaseDate.setText(mApp.getReleaseDate());
        genres =mApp.getGenres();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, genres);
        binding.listView.setAdapter(adapter);
    }
}