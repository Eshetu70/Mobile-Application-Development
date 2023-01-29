package com.example.activity;

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

public class SecondActivity extends AppCompatActivity {
    EditText editTextTextPersonName, editTextTextEmailAddress, editTextTextPersonNameID;
    Button buttonSubmit, buttonselect;
    String department;
    TextView textViewdepartment;
    public static final String KEY_PROFILE ="profile";

    private ActivityResultLauncher<Intent> startDeptforResult =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Intent data = result.getData();
                department =data.getStringExtra(DepartmentActivity.KEY_DEPT);
                textViewdepartment.setText(department);

            }else{
              department=null;
                textViewdepartment.setText("");
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Registration");

        editTextTextPersonName =findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        editTextTextPersonNameID =findViewById(R.id.editTextTextPersonNameID);
        textViewdepartment =findViewById(R.id.textViewdepartment);

      findViewById(R.id.buttonselect).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(SecondActivity.this, DepartmentActivity.class);
              startDeptforResult.launch(intent);

          }
      });
     findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String  name = editTextTextPersonName.getText().toString();
             String email= editTextTextEmailAddress.getText().toString();
             String ID = editTextTextPersonNameID.getText().toString();
             if(name.isEmpty()){
                 Toast.makeText(SecondActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
             }
             else if(email.isEmpty()){
                 Toast.makeText(SecondActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
             }
             else if(ID.isEmpty()){
                 Toast.makeText(SecondActivity.this, "Enter ID!", Toast.LENGTH_SHORT).show();
             } else if (department ==null) {
                 Toast.makeText(SecondActivity.this, "Select the department", Toast.LENGTH_SHORT).show();

             }else {
              Profile profile =new Profile(name, email,ID, department);
              Intent intent = new Intent(SecondActivity.this, ProfileActivity.class);
              intent.putExtra(KEY_PROFILE , profile);
              startActivity(intent);
             }

         }
     });
    }
}