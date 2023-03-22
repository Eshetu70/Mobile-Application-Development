package edu.uncc.inclass08;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.inclass08.databinding.FragmentGradesBinding;
import edu.uncc.inclass08.databinding.GradeRowItemBinding;

public class GradesFragment extends Fragment {
    public GradesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentGradesBinding binding;
    GradeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Grade> mGrades = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Grades");
        adapter = new GradeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        setUpGrade();
//        getdata();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        listenerRegistration= db.collection("grades")
                .whereEqualTo("own_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                mGrades.clear();
                for (QueryDocumentSnapshot doc : value) {
                Grade grade =doc.toObject(Grade.class);
                mGrades.add(grade);
                }
                adapter.notifyDataSetChanged();
                setUpGrade();

            }
        });


    }

    void setUpGrade(){
        if(mGrades.size()==0){
         binding.textViewGPA.setText("GPA: 0.0");
         binding.textView2.setText("Hours: 0.0");
        }else{
            double creditHours =0.0;
            double totalPoint = 0.0;
            double temp =0.0;

            for (Grade grade:mGrades) {
                if(grade.getLetterGrade().equalsIgnoreCase("A")){
                    temp=4.0;
                }else if(grade.getLetterGrade().equalsIgnoreCase("B")){
                    temp=3.0;
                }else if(grade.getLetterGrade().equalsIgnoreCase("C")){
                    temp=2.0;
                }else if(grade.getLetterGrade().equalsIgnoreCase("D")){
                    temp=1.0;
                }else if(grade.getLetterGrade().equalsIgnoreCase("F")){
                    temp=0.0;
                }


                    creditHours= creditHours+ grade.getCredit();
                    totalPoint =totalPoint+ grade.getCredit()*temp;
                    double finalpoint =totalPoint/creditHours;

                    if(creditHours==0){
                        binding.textViewGPA.setText("GPA: 4.0");
                        binding.textView2.setText("Hours: 0.0");

                    }else{
                        binding.textViewGPA.setText("GPA" + String.format("%.2f", finalpoint));
                        binding.textView2.setText("Hours:"+creditHours);
                    }


            }

        }





    }

//    void getdata(){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("grades").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    mGrades.clear();
//                    for (QueryDocumentSnapshot doc :  task.getResult()) {
//                        Grade grade =doc.toObject(Grade.class);
//                        mGrades.add(grade);
//                    }
//                    adapter.notifyDataSetChanged();
//                    setUpGrade();
//
//                }else{
//
//                }
//
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration !=null){
            listenerRegistration.remove();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_add){
           mListener.gotoAddCourse();
            return  true;
        }else if(item.getItemId()==R.id.action_logout){
            mListener.logout();
            return  true;
        }
        return super.onOptionsItemSelected(item);

    }

    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder>{
        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding rowBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradeViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradeViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding mBinding;
            Grade mGrade;
            public GradeViewHolder(@NonNull GradeRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }

            public void setupUI(Grade grade){
                this.mGrade = grade;
                mBinding.textViewCourseName.setText(mGrade.getName());
                mBinding.textViewCourseHours.setText(String.valueOf(mGrade.getCredit()));
                mBinding.textViewCourseLetterGrade.setText(mGrade.getLetterGrade());
                mBinding.textViewCourseNumber.setText(mGrade.getNumber());

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore.getInstance().collection("grades")
                                .document(mGrade.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
//                                               getdata();
                                           }

                                    }
                                });
                    }
                });

            }
        }
    }

    GradesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GradesListener) {
            mListener = (GradesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GradesListener");
        }
    }

    interface GradesListener{
        void logout();
        void gotoAddCourse();
    }
}