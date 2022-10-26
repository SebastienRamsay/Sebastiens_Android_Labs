package com.example.sebastiensandroidlabs.data;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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





            Switch switch1 = findViewById(R.id.spinSwitch);
            ImageView flag = findViewById(R.id.imageView);

            switch1.setOnCheckedChangeListener((Switch, isChecked) -> {
                if (Switch.isChecked()){
                    RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(5000);
                    rotate.setRepeatCount(Animation.INFINITE);
                    rotate.setInterpolator(new LinearInterpolator());
                    Toast.makeText(getApplicationContext(), "spin", Toast.LENGTH_SHORT).show();
                    flag.startAnimation(rotate);
                }else {
                    flag.clearAnimation();
                    Toast.makeText(getApplicationContext(), "stop spinning", Toast.LENGTH_SHORT).show();

                }

            });







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
