package com.example.sebastiensandroidlabs.data;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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
    private int radioButtonNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        getLayoutInflater();

        TextView myText = variableBinding.textviewId;
        EditText myEditText = variableBinding.edittextId; //useless
        Button myButton = variableBinding.buttonId;



        setContentView(variableBinding.getRoot());



        model.checkbox.observe( this, selected -> {
            variableBinding.checkbox.setChecked(selected);
            variableBinding.radioButton1.setChecked(selected);
            variableBinding.radioButton2.setChecked(selected);
            variableBinding.radioButton3.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

        });
        model.radioButton1.observe( this, selected -> {
            variableBinding.radioButton1.setChecked(selected);
            variableBinding.radioButton2.setChecked(selected);
            variableBinding.checkbox.setChecked(selected);
            variableBinding.radioButton3.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

        });
        model.radioButton2.observe( this, selected -> {
            variableBinding.radioButton2.setChecked(selected);
            variableBinding.radioButton1.setChecked(selected);
            variableBinding.checkbox.setChecked(selected);
            variableBinding.radioButton3.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

        });
        model.radioButton3.observe( this, selected -> {
            variableBinding.radioButton3.setChecked(selected);
            variableBinding.radioButton1.setChecked(selected);
            variableBinding.radioButton2.setChecked(selected);
            variableBinding.checkbox.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

        });
        model.switch1.observe( this, selected -> {
            variableBinding.switch1.setChecked(selected);
            variableBinding.radioButton1.setChecked(selected);
            variableBinding.radioButton2.setChecked(selected);
            variableBinding.checkbox.setChecked(selected);
            variableBinding.radioButton3.setChecked(selected);

        });
            variableBinding.buttonId.setOnClickListener((button) -> {
                model.textView.postValue("your edit text has: " + variableBinding.edittextId.getText().toString());
                variableBinding.textviewId.setText(model.textView.getValue());
            });

            variableBinding.radioButton1.setOnCheckedChangeListener ((button, isChecked) -> {
                model.radioButton1.postValue(button.isChecked());
                Toast.makeText(this, "Radio button #1 value is now: " + button.isChecked(), Toast.LENGTH_SHORT).show();
            });

            variableBinding.radioButton2.setOnCheckedChangeListener ((button, isChecked) -> {
                model.radioButton2.postValue(button.isChecked());
                Toast.makeText(this, "Radio button #2 value is now: " + button.isChecked(), Toast.LENGTH_SHORT).show();
            });


            variableBinding.radioButton3.setOnCheckedChangeListener ((button, isChecked) -> {
                model.radioButton3.postValue(button.isChecked());
                Toast.makeText(this, "Radio button #3 value is now: " + button.isChecked(), Toast.LENGTH_SHORT).show();
            });

            variableBinding.switch1.setOnCheckedChangeListener ((button, isChecked) -> {
                model.switch1.setValue(button.isChecked());
                Toast.makeText(this, "Switch value is now: " + button.isChecked(), Toast.LENGTH_SHORT).show();
            });

            variableBinding.checkbox.setOnCheckedChangeListener ((button, isChecked) -> {
                variableBinding.switch1.setChecked(isChecked);
                Toast.makeText(this, "Checkbox value is now: " + button.isChecked(), Toast.LENGTH_SHORT).show();
            });





    }
}
