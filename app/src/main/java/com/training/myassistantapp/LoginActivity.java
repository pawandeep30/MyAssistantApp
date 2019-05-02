package com.training.myassistantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.training.myassistantapp.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eTxtEmail, eTxtPassword;

    Button btnLogin;

    User user;

    ProgressDialog progressDialog;

    FirebaseAuth auth;

    void initViews(){
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        user = new User();

        btnLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    public void onClick(View v) {
        user.email = eTxtEmail.getText().toString();
        user.password = eTxtPassword.getText().toString();
        loginUser();
    }

    void loginUser(){

        progressDialog.show();

        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isComplete()){
                            Toast.makeText(LoginActivity.this,user.email+ "Login Successfully", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, SubmitAnIncidentActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }

                    }
                });

    }

}