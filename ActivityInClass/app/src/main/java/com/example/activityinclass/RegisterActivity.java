package com.example.activityinclass;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button buttonSelectID, buttonSubmitID;
    String department;
    TextView  textViewDepertementID;
    EditText NameEnteredID, EmaiEnteredID, IDentered;
    Profile profile;
    public static final String KEY_PROFILE ="PROFILE";
    private ActivityResultLauncher<Intent> startonDeptResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Intent intent =result.getData();
               department =intent.getStringExtra(DepartmentActivity.KEY_DEPRT);
               textViewDepertementID.setText(department);
            }else {
                department=null;
                textViewDepertementID.setText("");
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");

        buttonSelectID =findViewById(R.id.buttonSelectID);
        textViewDepertementID =findViewById(R.id. textViewDepertementID);
        buttonSubmitID =findViewById(R.id.buttonSubmitID);
        IDentered =findViewById(R.id.IDentered);
        EmaiEnteredID =findViewById(R.id.EmaiEnteredID);
        NameEnteredID =findViewById(R.id.NameEnteredID);
        textViewDepertementID.setText("");

        buttonSelectID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, DepartmentActivity.class);
               startonDeptResult.launch(intent);
            }
        });

        buttonSubmitID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String name = NameEnteredID.getText().toString();
                 String email = EmaiEnteredID.getText().toString();
                 String id = IDentered.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (id.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter ID", Toast.LENGTH_SHORT).show();

                } else if (department == null) {
                    Toast.makeText(RegisterActivity.this, "Select Dept", Toast.LENGTH_SHORT).show();
                }else {
                    profile = new Profile(name, email, id, department);
                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    intent.putExtra(KEY_PROFILE, profile);
                    startActivity(intent);
                }
            }
        });
    }
}