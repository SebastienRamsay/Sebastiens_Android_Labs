package com.example.sebastiensandroidlabs.data;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastiensandroidlabs.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {



    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        EditText phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", phoneNumber.getText().toString());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");


        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome back " + emailAddress);


        EditText phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL);
        Button callButton = (Button) findViewById(R.id.callButton);

        callButton.setOnClickListener(clk -> {
            call.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
            startActivity(call);
        });



        Button changePictureButton = findViewById(R.id.changePictureButton);


        changePictureButton.setOnClickListener(clk -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    InvokeCamera();
                }else {
                    String[] permissionRequest = {Manifest.permission.CAMERA};
                    requestPermissions( permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }else {
                Toast.makeText(this, "SDK_INT ERROR", Toast.LENGTH_LONG).show();
            }

        });

        File file = new File( getFilesDir(), "Picture.png");

        if(file.exists())
        {
            ImageView profileImage = findViewById(R.id.imageView);
            Bitmap theImage = BitmapFactory.decodeFile("picture.png");
            profileImage.setImageBitmap(theImage);
        }

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String storedPhoneNumber = prefs.getString("phoneNumber", "");
        phoneNumber.setText(storedPhoneNumber);

    }









    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                InvokeCamera();
            }else{
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void InvokeCamera() {
        ImageView profileImage = findViewById(R.id.imageView);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {


                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;

                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (FileNotFoundException e)
                            { e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        cameraResult.launch(cameraIntent);
    }




}