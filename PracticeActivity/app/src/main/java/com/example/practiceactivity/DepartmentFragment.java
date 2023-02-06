package com.example.practiceactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practiceactivity.databinding.FragmentDepartmentBinding;


public class DepartmentFragment extends Fragment {


    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentDepartmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDepartmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String department = "Computer Science";
                int selected =binding.radioGroup.getCheckedRadioButtonId();
                if(selected == R.id.radioButtonSIS){
                    department ="Software Info System";
                } else if (selected == R.id.radioButtonBIO) {
                    department ="Bio Informatics";
                }
                else if (selected == R.id.radioButtonDS) {
                    department ="Data Science";
                }

                mlistener.backToRegistration(department);

            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.cancelDepartment();

            }
        });
    }

    DepartmentFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(DepartmentFragmentListener)context;
    }

    interface DepartmentFragmentListener{
        void backToRegistration(String department);
        void cancelDepartment();
    }

}