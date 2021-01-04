package com.example.android.apma_work1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextfirstName, textInputEditTextlastName, textInputEditTextemail, textInputEditTextpassword;
    Button buttonSignUp;
    TextView loginTextViewOnSignUp;
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //-------------------------------------------------------------------------------------
        textInputEditTextfirstName = findViewById(R.id.firstName);
        textInputEditTextlastName = findViewById(R.id.lastName);
        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextpassword = findViewById(R.id.signUpPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        loginTextViewOnSignUp = findViewById(R.id.loginText);
        ProgressBar = findViewById(R.id.progress);

        loginTextViewOnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });



        //--------------------------------------------------------------------------------------
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName, lastName, email, password;
                firstName = textInputEditTextfirstName.getText().toString();
                lastName = textInputEditTextlastName.getText().toString();
                email = textInputEditTextemail.getText().toString();
                password = textInputEditTextpassword.getText().toString();

                if (!firstName.equals("") && !lastName.equals("") && !email.equals("") && !password.equals("")) {
                    ProgressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "firstName";
                            field[1] = "email";
                            field[2] = "lastName";
                            field[3] = "password";


                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = firstName;
                            data[1] = lastName;
                            data[2] = email;
                            data[3] = password;


                            PutData putData = new PutData("http://192.168.191.1/LogIn-SignUp/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();

                                    if (!result.equals("Sign Up Success")) {

                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }

                                    ProgressBar.setVisibility(View.GONE);
                                    //---------------------------------------------------------------------

                                }
                            }
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }

            ;


        });
    }

}