package edu.uncc.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.midtermapp.models.Question;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeListener, TriviaFragment.TrivialListener , StatsFragment.StatsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new WelcomeFragment())
                .commit();
    }

    @Override
    public void gotoTrial(ArrayList<Question> mTriviaQuestions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TriviaFragment.newInstance(mTriviaQuestions))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoStatus(int stat, int size) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, StatsFragment.newInstance(stat, size))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoBack() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new WelcomeFragment())
                .addToBackStack(null)
                .commit();

    }
}