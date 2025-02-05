package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.assessment4.Models.CourseHistory;
import com.example.assessment4.Models.DataServices;
import com.example.assessment4.Models.Student;
import com.example.assessment4.databinding.FragmentStudentHistoryBinding;
import com.example.assessment4.databinding.FragmentStudentsBinding;

import java.util.ArrayList;
import java.util.List;


public class StudentHistoryFragment extends Fragment {

    private static final String ARG_PARAM_HISTORY = "ARG_PARAM_HISTORY";
    private static final String ARG_PARAM2 = "param2";
    private Student mStudent;
    private String mParam2;

    public StudentHistoryFragment() {
        // Required empty public constructor
    }
    public static StudentHistoryFragment newInstance(Student stud) {
        StudentHistoryFragment fragment = new StudentHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_HISTORY, stud);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable(ARG_PARAM_HISTORY);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
   FragmentStudentHistoryBinding binding;
   ArrayList<CourseHistory> courseHistories;
    userAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentHistoryBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Student History");
        binding.textViewStudentName.setText(mStudent.getName());
        courseHistories =mStudent.getCourses();
        adapter = new userAdapter(getContext(),courseHistories);
        binding.listView.setAdapter(adapter);

    }
    class userAdapter extends ArrayAdapter<CourseHistory> {
        public userAdapter(@NonNull Context context,  @NonNull List<CourseHistory> objects) {
            super(context, R.layout.history_row_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView =getLayoutInflater().inflate(R.layout.history_row_item,parent,false);

            }
            CourseHistory courseHistory =getItem(position);
            TextView textViewCourseNumber =convertView.findViewById(R.id.textViewCourseNumber);
            TextView textViewCourseName =convertView.findViewById(R.id.textViewCourseName);
            TextView textViewCourseHours =convertView.findViewById(R.id.textViewCourseHours);
            TextView textViewCourseLetterGrade =convertView.findViewById(R.id.textViewCourseLetterGrade);
             textViewCourseName.setText(courseHistory.getName());
             textViewCourseHours.setText(String.valueOf(courseHistory.getHours()));
             textViewCourseNumber.setText(courseHistory.getNumber());
            textViewCourseLetterGrade.setText(courseHistory.getLetterGrade());
            return convertView;


        }
  }
}