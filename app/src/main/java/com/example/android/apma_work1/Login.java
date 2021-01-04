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

public class Login extends AppCompatActivity {

    TextInputEditText   textInputEditTextemail, textInputEditTextpassword;
    Button buttonLogin;
    TextView loginTextSignUp;
    ProgressBar ProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextemail = findViewById(R.id.loginEmail);
        textInputEditTextpassword = findViewById(R.id.loginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        loginTextSignUp = findViewById(R.id.signUptext);
        ProgressBar = findViewById(R.id.progress);

        loginTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;

                email = textInputEditTextemail.getText().toString();
                password = textInputEditTextpassword.getText().toString();

                if (!email.equals("") && !password.equals("") ) {

                    ProgressBar.setVisibility(View.VISIBLE);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;


                            PutData putData = new PutData("http://192.168.191.1/LogIn-SignUp/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    String success = "Login Success";

                                    int value = success.length();

                                    if (result.length() == value)
                                    {



                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    }else
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();





                                    ProgressBar.setVisibility(View.GONE);
                                }
                            }
                            //End Write and Read data with URL


                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }

            };

        });

    }
}