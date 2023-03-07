package com.example.assessment4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assessment4.Models.Student;

public class MainActivity extends AppCompatActivity implements StudentsFragment.StudentFragmentListener {
    TextView textViewStudentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new StudentsFragment())
                .commit();
    }

    @Override
    public void SendtoHistory(Student stud) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, StudentHistoryFragment.newInstance(stud))
                .addToBackStack(null)
                .commit();

    }
}