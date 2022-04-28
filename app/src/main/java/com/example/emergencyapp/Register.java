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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText etFullName, etPassword, etEmail;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //banner = (TextView) findViewById(R.id.banner);
        //banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.button_register);
        registerUser.setOnClickListener(this);

        etFullName = (EditText) findViewById(R.id.etFullName_Register);
        etPassword = (EditText) findViewById(R.id.etPassword_Register);
        etEmail = (EditText) findViewById(R.id.etEmail_Register);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_register);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        if(email.isEmpty()) {
            etEmail.setError("Παρακαλώ καταχωρήστε ένα email");
            etFullName.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Παρακαλώ καταχωρήστε ένα έγκυρο email");
            etFullName.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            etPassword.setError(("Παρακαλώ καταχωρήστε ένα password"));
            etPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            etPassword.setError("Το password πρέπει να έχει τουλάχιστον 7 χαρακτήρες");
            etPassword.requestFocus();
            return;
        }

        if(fullName.isEmpty()) {
            etFullName.setError("Παρακαλώ καταχωρήστε ένα όνομα");
            etFullName.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        User user = new User(fullName, email, "tbd", "tbd", "tbd", "tbd", "tbd", "tbd", 999, 999999);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText( Register.this, "Επιτυχής εγγραφή!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.VISIBLE);

                                    startActivity(new Intent(Register.this, MainActivity.class));
                                }else {
                                    Toast.makeText(Register.this, "Αποτυχία εγγραφής", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else {
                        Toast.makeText(Register.this, "Αποτυχία εγγραφής", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
    }
}


