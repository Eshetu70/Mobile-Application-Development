package edu.uncc.midtermmakeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.uncc.midtermmakeup.databinding.FragmentLetterGradesBinding;


public class LetterGradesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_course = "ARG_PARAM_course";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Course mCourse;
    private String mParam2;

    public LetterGradesFragment() {
        // Required empty public constructor
    }


    public static LetterGradesFragment newInstance(Course course) {
        LetterGradesFragment fragment = new LetterGradesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_course, course);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_PARAM_course);

        }
    }
    FragmentLetterGradesBinding binding;
    ArrayAdapter<String> adapter;
    ArrayList<String>courses  = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =FragmentLetterGradesBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Letter Grade");

        courses.add("A");
        courses.add("B");
        courses.add("C");
        courses.add("D");
        courses.add("F");

        adapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, courses);
        binding.listView.setAdapter(adapter);


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



              mlistener.backCreate(courses.get(i));
            }
        });

    }
    LetterListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener=(LetterListener)context;
    }

    interface  LetterListener{
        void backCreate(String s);
    }
}