package edu.uncc.midtermmakeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.midtermmakeup.databinding.FragmentCreateCourseBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";
    private static final String ARG_PARAM_COURSE = " ARG_PARAM_COURSE";


    // TODO: Rename and change types of parameters
    private String mToken;
    private Course mCourse;

    public CreateCourseFragment() {
        // Required empty public constructor
    }

    private String letter=null;

    public void assignLetter(String s){
        this.letter = s;
    }

    public static CreateCourseFragment newInstance(String auth) {
        CreateCourseFragment fragment = new CreateCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_AUTH,auth);
//        args.putSerializable( ARG_PARAM_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken =  getArguments().getString(ARG_PARAM_AUTH);
//            mCourse = (Course) getArguments().getSerializable( ARG_PARAM_COURSE);
        }
    }
   FragmentCreateCourseBinding binding;
    ArrayList<Course>courses = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding =FragmentCreateCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Course Create");

        binding.textView3.setText(letter);

        binding.buttonselec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoLetterGrade();
            }
        });
        binding.buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.cancelCourse();

            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CourseName= binding.editTextTextCourseName.getText().toString();
                String courseNumber =binding.editTextTextCourseNumber.getText().toString();
                String credit =binding.editTextTextCreditHours.getText().toString();
                String courseName = binding.editTextTextCourseName.getText().toString();

                if(CourseName.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid Input", Toast.LENGTH_SHORT).show();
                }else if(courseNumber.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid Input", Toast.LENGTH_SHORT).show();
                }else if(credit.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid Input", Toast.LENGTH_SHORT).show();
                }else if(letter.isEmpty()){
                    Toast.makeText(getActivity(), "Select grade", Toast.LENGTH_SHORT).show();

                }else{
                    RequestBody formBody = new FormBody.Builder()
                            .add("course_number", courseNumber)
                            .add("letter_grade", letter)
                            .add("credit_hours", credit)
                            .add("course_name", courseName)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://www.theappsdr.com/api/course/add")
                            .addHeader("Authorization", "BEARER " + mToken)
                            .post(formBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();;
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mlistener.submitCourse();

                                    }
                                });

                            }else{

                            }

                        }
                    });
                }





            }
        });



    }

    CreateCourseListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(CreateCourseListener)context;
    }

    interface CreateCourseListener{
        void submitCourse();
        void gotoLetterGrade();
        void cancelCourse();
    }
}