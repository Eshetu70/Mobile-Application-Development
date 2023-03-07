package edu.uncc.midtermmakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener, CoursesFragment.CoursesListener, CreateCourseFragment.CreateCourseListener ,LetterGradesFragment.LetterListener{
    String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkIfLoggedIn()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentView, CoursesFragment.newInstance(mToken))
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentView, new LoginFragment())
                    .commit();
        }
    }

    boolean checkIfLoggedIn(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);

        if(token == null){
            return false;
        }

        mToken = token;
        return true;
    }

    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new RegisterFragment())
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new LoginFragment())
                .commit();
    }

    @Override
    public void successfulAuthGotoCoursesFragment(String token) {
        mToken = token;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, CoursesFragment.newInstance(mToken))
                .commit();
    }

    @Override
    public void gotoCreateCoures(String mToken) {
//        this.mAuth =mToken;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, CreateCourseFragment.newInstance(mToken),"create_frag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoLogout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new LoginFragment())
                .commit();

    }

    @Override
    public void submitCourse() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void gotoLetterGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView,LetterGradesFragment.newInstance(null))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void cancelCourse() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void backCreate(String s) {


        CreateCourseFragment fragment = (CreateCourseFragment) getSupportFragmentManager().findFragmentByTag("create_frag");
        if(fragment != null){
            fragment.assignLetter(s);
            getSupportFragmentManager().popBackStack();
        }
    }
}