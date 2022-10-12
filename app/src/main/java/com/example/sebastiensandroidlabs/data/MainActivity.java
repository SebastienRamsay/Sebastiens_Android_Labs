package com.example.sebastiensandroidlabs.data;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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

//    private MainViewModel model;
    //private ActivityMainBinding variableBinding;
    private MainViewModel model;
    private ActivityMainBinding variableBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch spin = findViewById(R.id.spinSwitch);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
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
}
