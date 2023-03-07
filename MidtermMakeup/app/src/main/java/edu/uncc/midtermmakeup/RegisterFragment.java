package edu.uncc.midtermmakeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.uncc.midtermmakeup.databinding.FragmentRegisterBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register");

        //binding.editTextEmail.setText("zz@zz.com");
        //binding.editTextPassword.setText("test123");
        //binding.editTextFirstName.setText("Zak");
        //binding.editTextLastName.setText("Smith");


        binding.buttonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = binding.editTextFirstName.getText().toString();
                String lname = binding.editTextLastName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if(fname.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid first name!", Toast.LENGTH_SHORT).show();
                } else if(lname.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid last name!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid email!", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid password!", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody formBody = new FormBody.Builder()
                            .add("fname", fname)
                            .add("lname", lname)
                            .add("email", email)
                            .add("password", password)
                            .build();

                    Request request = new Request.Builder()
                            .url("https://www.theappsdr.com/api/signup")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String body = response.body().string();
                            Log.d("demo", "onResponse: " + body);
                            if(response.isSuccessful()){
                                try {
                                    JSONObject json = new JSONObject(body);
                                    String token = json.getString("token");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("demo", "run: " + token);
                                            mListener.successfulAuthGotoCoursesFragment(token);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    JSONObject json = new JSONObject(body);
                                    String message = json.getString("message");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLogin();
            }
        });

    }

    RegisterFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RegisterFragmentListener) context;
    }

    interface RegisterFragmentListener{
        void gotoLogin();
        void successfulAuthGotoCoursesFragment(String token);
    }
}