package com.example.permission;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonRequest =findViewById(R.id.buttonRequest);

        buttonRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //do i have permission

            if(hasCameraPermission()){
                Toast.makeText(MainActivity.this, "Camera Permission Granted!!", Toast.LENGTH_SHORT).show();
            }else{
         if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
             showCustomDialog("Camera Permission", "This app needs camera permission to enable you to store photos to your profile",
                     "Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     requestPermissionLauncher.launch(Manifest.permission.CAMERA);

                 }
             },"Cancel", null);

         }else{
             requestPermissionLauncher.launch(Manifest.permission.CAMERA);
         }
            }
            }
        });
    }

private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
    @Override
    public void onActivityResult(Boolean isGranted) {

     if(isGranted){
         Log.d("demo", "onActivityResult: granted");
     }else {

         if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
             showCustomDialog("Camera Permission", "the app need camera permission please goto setting to set up camera permission",
                     "Goto Setting", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package" + BuildConfig.APPLICATION_ID));
                     startActivity(intent);
                 }
             },"Cancel", null);
         }
         Log.d("demo", "onActivityResult: shouldShowRequestPermissionRationale"+ shouldShowRequestPermissionRationale(Manifest.permission.CAMERA));
     }
    }
});
    private boolean hasCameraPermission(){
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    void showCustomDialog(String title, String message,
                          String positiveBtnTitle, DialogInterface.OnClickListener positiveListener,
                          String negativeBntTitle, DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnTitle, positiveListener)
                .setNegativeButton(negativeBntTitle, negativeListener);
        builder.create().show();
    }
}