package edu.uncc.midtermapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.midtermapp.databinding.FragmentTriviaBinding;
import edu.uncc.midtermapp.models.Answer;
import edu.uncc.midtermapp.models.Question;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();

    private static final String ARG_PARAM_TRIVIAL = "ARG_PARAM_TRIVIAL";
    private static final String ARG_PARAM2 = "param2";

    int currentQuestionIndex =0;
    Question currentquestion;
    Answer answer;
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayAdapter<Answer> adapter;
    int stat =0, size;
    boolean answerIncorrectly =false;

    public TriviaFragment() {
        // Required empty public constructor
    }


    public static TriviaFragment newInstance( ArrayList<Question> questions) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TRIVIAL, questions);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTriviaQuestions = (ArrayList<Question>) getArguments().getSerializable(ARG_PARAM_TRIVIAL);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
   FragmentTriviaBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding =FragmentTriviaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("TRIVIAL");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, answers);
        binding.listViewAnswers.setAdapter(adapter);

        if(mTriviaQuestions.size()>0){
            currentQuestionIndex =0;
            currentquestion =mTriviaQuestions.get(0);
            displayQuestion();
        }
    binding.listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Answer selectedAnswer =adapter.getItem(i);

            RequestBody formBody = new FormBody.Builder()
                    .add("question_id", currentquestion.getQuestion_id())
                    .add("answer_id",selectedAnswer.answer_id )
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.theappsdr.com/api/trivia/checkAnswer")
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
                          String body =response.body().string();
                          try {
                              JSONObject jsonObject = new JSONObject(body);
                             if(jsonObject.getBoolean("isCorrectAnswer")){
                                 getActivity().runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         if(!answerIncorrectly){
                                             stat =stat+1;
                                         }
                                         if(currentQuestionIndex == mTriviaQuestions.size()-1){

                                         mlistener.gotoStatus(stat, mTriviaQuestions.size());

                                         }else{
                                             answerIncorrectly =false;
                                             currentQuestionIndex =currentQuestionIndex+1;
                                             currentquestion =mTriviaQuestions.get(currentQuestionIndex);
                                             displayQuestion();

                                         }
                                     }
                                 });

                             }else{
                                 getActivity().runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         answerIncorrectly =true;
                                         Toast.makeText(getActivity(), "incorrect Answer", Toast.LENGTH_SHORT).show();
                                     }
                                 });

                             }
                          } catch (JSONException e) {
                              throw new RuntimeException(e);
                          }

                      }else{

                      }

                  }
              });

        }
    });
    }
    void displayQuestion(){
        binding.textViewTriviaTopStatus.setText("question "+ (currentQuestionIndex+ 1) + "of "+ mTriviaQuestions.size());
        binding.textViewTriviaQuestion.setText(currentquestion.getQuestion_text());
        answers.clear();
        answers.addAll(currentquestion.getAnswers());
        adapter.notifyDataSetChanged();
        if(currentquestion.getQuestion_url()!=null){
            Picasso.get().load(currentquestion.getQuestion_url()).into(binding.imageViewQuestion);
        } else{
            binding.imageViewQuestion.setImageResource(R.drawable.no_image_ic);

        }

    }

    TrivialListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener=(TrivialListener)context;
    }

    interface TrivialListener{
        void gotoStatus(int stat, int size);
    }
}