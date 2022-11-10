package com.example.sebastiensandroidlabs.data;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sebastiensandroidlabs.R;

/**
 * @author Sebastien Ramsay
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    /**
     * This holds the text at the centre of the screen
     */
    private TextView passwordTextView = null;
    /**
     * this holds the password the user is supposed to enter
     */
    private EditText passwordEditText = null;
    /**
     * this is the button the user clicks to check their password (login)
     */
    private Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordTextView = findViewById(R.id.passwordTextView);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener( clk ->{

            String password = passwordEditText.getText().toString();

            if (CheckPasswordComplexity(password)){
                passwordTextView.setText("Your password meets the requirements");
            }else{
                passwordTextView.setText("YOU SHALL NOT PASS!");
            }
        });



    }

    /**
     *
     * @param password The string being checked for complexity
     * @return the password is either complex or not complex
     */
    private boolean CheckPasswordComplexity(String password) {
        boolean complex = true;
        char c;
        int isUppercase = 0;
        int isLowercase = 0;
        int isNumber = 0;
        int isSpecial = 0;


        for (int i = 0; i < password.length(); i++){
            c = password.charAt(i);
            if (Character.isUpperCase(c)){
                isUppercase++;
            }else if (Character.isLowerCase(c)) {
                isLowercase++;
            }else if (Character.isDigit(c)){
                isNumber++;
            }else if (IsSpeciaCaracter(c)){
                isSpecial++;
            }
        }
        if (isLowercase == 0){
            complex = false;
            Toast.makeText(getApplicationContext(), "MISSING LOWERCASE", LENGTH_LONG).show();
        }else if (isUppercase == 0){
            complex = false;
            Toast.makeText(getApplicationContext(), "MISSING UPPERCASE", LENGTH_LONG).show();
        }else if (isNumber == 0){
            complex = false;
            Toast.makeText(getApplicationContext(), "MISSING NUMBER", LENGTH_LONG).show();
        }else if (isSpecial == 0){
            complex = false;
            Toast.makeText(getApplicationContext(), "MISSING SPECIAL CHARACTER", LENGTH_LONG).show();
        }

        return complex;
    }

    /**
     *
     * @param c The character getting checked for special characters
     * @return the character is either special or not special
     */
    private boolean IsSpeciaCaracter(char c) {

        switch(c){
            case '#':
                return true;
            case '?':
                return true;
            case '$':
                return true;
            case '%':
                return true;
            case '^':
                return true;
            case '&':
                return true;
            case '*':
                return true;
            case '!':
                return true;
            case '@':
                return true;
            default:
                return false;
        }
    }
}
