package com.example.sebastiensandroidlabs.data;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText phoneNumber = null;
    private TextView welcomeText = null;
    private Button callButton = null;
    private Button changePictureButton = null;
    private ImageView profileImage = null;

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", phoneNumber.getText().toString());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        welcomeText = findViewById(R.id.welcomeText);
        callButton = (Button) findViewById(R.id.callButton);
        changePictureButton = findViewById(R.id.changePictureButton);
        profileImage = findViewById(R.id.imageView);

        Intent fromPrevious = getIntent();
        Intent call = new Intent(Intent.ACTION_DIAL);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        File picture = new File( getFilesDir(), "Picture.png");
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);


        welcomeText.setText("Welcome back " + emailAddress);


        if(picture.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile("picture.png");
            profileImage.setImageBitmap(theImage);
        }



        String storedPhoneNumber = prefs.getString("phoneNumber", "");
        phoneNumber.setText(storedPhoneNumber);




        callButton.setOnClickListener(clk -> {
            call.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
            startActivity(call);
        });



        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {


                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("Data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;

                            try
                            {
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );



        changePictureButton.setOnClickListener(clk -> {
            cameraResult.launch(cameraIntent);


        });



    }

    }
