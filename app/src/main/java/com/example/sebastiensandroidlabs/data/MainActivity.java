package com.example.sebastiensandroidlabs.data;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastiensandroidlabs.databinding.ActivityMainBinding;
import com.example.sebastiensandroidlabs.ui.MainViewModel;
import com.example.sebastiensandroidlabs.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.w( TAG, "In onCreate() - Loading Widgets" );

        Log.d( TAG, "onCreate() is the first function that gets created when an application is launched." +
                " There are several other functions that get called when an application is launching:");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

        com.example.sebastiensandroidlabs.databinding.ActivityMainBinding variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        getLayoutInflater();


        Button loginButton = (Button) findViewById(R.id.loginButton);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);


        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String storedEmailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(storedEmailAddress);


        loginButton.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();

            startActivity(nextPage);
        } );





    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d( TAG, "onStart() - The application is now visible on screen.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d( TAG, "onPause()- The application no longer responds to user input");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d( TAG, "onResume() - The application is now responding to user input. After these 3 function calls, your application " +
                "is up and running on the screen. When another application comes up on screen and your application disappears beneath, " +
                "there are other functions that get called:");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d( TAG, "onStop() - The application is no longer visible.\n" +
                "Normally an application will always be asleep in the background when it is not visible on screen. It's only when an app " +
                "is removed from the system by the user that the last function is called:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d( TAG, "onDestroy() - Any memory used by the application is freed.");
    }
}
