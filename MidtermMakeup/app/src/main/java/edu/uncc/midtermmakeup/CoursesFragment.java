package edu.uncc.midtermmakeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uncc.midtermmakeup.databinding.CourseHistoryItemBinding;
import edu.uncc.midtermmakeup.databinding.FragmentCoursesBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoursesFragment extends Fragment {
    private static final String ARG_PARAM_TOKEN = "ARG_PARAM_TOKEN";
    private String mToken;
    private Auth mAuth;
    ArrayList<Course> courses =new ArrayList<>();
    FragmentCoursesBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    CoureAdapter adapter;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance(String token) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_PARAM_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Courses");


        adapter = new CoureAdapter(getActivity(), R.layout.course_history_item, courses);
        binding.listView.setAdapter(adapter);
        getCourse();



        binding.buttonlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoLogout();
            }
        });

      binding.imageViewAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mlistener.gotoCreateCoures(mToken);

          }
      });


    }


    void getCourse(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/courses")
                .addHeader("Authorization", "BEARER " +mToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){

                    String body =response.body().string();
                    Double totalGradePoints = 0.0;
                    Double totalCreditHours = 0.0;

                    try {
                        courses.clear();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray =jsonObject.getJSONArray("grades");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonGrade =jsonArray.getJSONObject(i);
                            Course course =new Course(jsonGrade);
                            totalCreditHours += course.getCredit_hours();
                            String temp = course.getLetter_grade();
                            Double tempValue = 0.0;
                            if(temp.equalsIgnoreCase("A")){
                                tempValue = 4.0;
                            }else if(temp.equalsIgnoreCase("B")){
                                tempValue = 3.0;
                            }else if(temp.equalsIgnoreCase("C")){
                                tempValue = 2.0;
                            }else if(temp.equalsIgnoreCase("D")){
                                tempValue = 1.0;
                            }else if(temp.equalsIgnoreCase("F")){
                                tempValue = 0.0;
                            }
                            Double gradePoint = course.credit_hours * tempValue;
                            totalGradePoints += gradePoint;

                            courses.add(course);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  adapter.notifyDataSetChanged();
                                }
                            });

                        }
                        Double finalvalue = totalGradePoints / totalCreditHours;
                        Double finalHours = totalCreditHours;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                binding.textViewGPD.setText("GPA "+String.valueOf(finalvalue));
                                binding.textView.setText("Credit Hours "+String.valueOf(finalHours));
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else{

                }

            }
        });


    }

    class CoureAdapter extends ArrayAdapter<Course>{

        public CoureAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CourseHistoryItemBinding mBinding;

            if(convertView==null){

                mBinding = CourseHistoryItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView= mBinding.getRoot();
                convertView.setTag(mBinding);

            }else{
                mBinding= (CourseHistoryItemBinding) convertView.getTag();
            }
                 Course course =getItem(position);
                 mBinding.textViewCourseLetter.setText(course.getLetter_grade());
                 mBinding.textViewCourseNumber.setText(course.getCourse_number());
                 mBinding.textViewName.setText(course.getCourse_name());
                 mBinding.textViewhourss.setText(course.getCredit_hours()+" Credit hours");

                 mBinding.imageView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         RequestBody formBody = new FormBody.Builder()
                                 .add("course_id", course.getCourse_id())
                                 .build();
                         Request request = new Request.Builder()
                                 .url("https://www.theappsdr.com/api/course/delete")
                                 .addHeader("Authorization", "BEARER " + mToken)
                                 .post(formBody)
                                 .build();

                         client.newCall(request).enqueue(new Callback() {
                             @Override
                             public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                 e.printStackTrace();
                             }

                             @Override
                             public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                 if(response.isSuccessful()){
                                     getActivity().runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             getCourse();
                                         }
                                     });

                                 }else{

                                 }

                             }
                         });

                     }
                 });

                  return convertView;
        }
    }

    CoursesListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(CoursesListener)context;
    }

    interface CoursesListener{
        void gotoCreateCoures(String mToken);
        void gotoLogout();
    }
}