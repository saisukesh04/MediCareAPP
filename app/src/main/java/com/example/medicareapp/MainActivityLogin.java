package com.example.medicareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityLogin extends AppCompatActivity {

    public void Register(View view){
           Intent intent = new Intent(getApplicationContext(), MainActivityRegister.class);

           startActivity(intent);

    }

    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.clickRegister) TextView clickRegister;
    @BindView(R.id.loginProgressBar) ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        clickRegister.setOnClickListener(view -> {
            startActivity(new Intent(MainActivityLogin.this, MainActivityRegister.class));
            finish();
        });

        loginButton.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View focusedView = MainActivityLogin.this.getCurrentFocus();
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            String email = emailEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty())
                Snackbar.make(view, "Please fill all the fields", Snackbar.LENGTH_LONG).show();
            else {
                loginProgressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivityLogin.this, MainActivityValues.class));
                        finish();
                    } else {
                        Snackbar.make(view, "There was an error. Please try again!", Snackbar.LENGTH_LONG).show();
                        Log.e("Error", "Login Error : " + task.getException());
                        loginProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            });
    }
}
