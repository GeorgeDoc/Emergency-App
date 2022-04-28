package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText etEmail, etPassword;
    private Button login, first;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(this);

        first = (Button) findViewById(R.id.button_1st);
        first.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.etEmail_Login);
        etPassword = (EditText) findViewById(R.id.etPassword_Login);

        forgotPassword = (TextView) findViewById(R.id.tvForgotPass_login);
        forgotPassword.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_login);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override                                                                                       // Navigation
    public void onClick(View v) {                                                                   // Which button was pressed
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.button_login:
                userLogin();
                break;
            case R.id.tvForgotPass_login:
                startActivity(new Intent(this, ForgotPass.class));
                break;
            case R.id.button_1st:
                startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();                                         // Trim eliminates spaces at beginning & end
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()) {                                                                       //email validation
            etEmail.setError("Παρακαλώ εισάγετε ένα email");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {                                     // check if valid email valid pattern
            etEmail.setError("Το email δεν είναι έγκυρο");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {                                                                    //password validation
            etPassword.setError("Παρακαλώ εισάγετε ένα password");
            etPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            etPassword.setError("Το password πρέπει να περιέχει τουλάχιστον 7 χαρακτήρες");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {                                                    //verify email
                        startActivity(new Intent(Login.this, MainActivity.class));   //redirect to main
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Σας έχει σταλεί mail για επαλήθευση της εγγραφής", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Login.this, "Login failed, ελεγξτε τα στοιχεία", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}