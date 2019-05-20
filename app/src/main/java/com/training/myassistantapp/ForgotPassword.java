package com.training.myassistantapp;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText txtPasswordEmail;
    Button btnContinue,btnCancel;
    FirebaseAuth auth;

    void initViews(){
        txtPasswordEmail=findViewById(R.id.editTextPasswordEmail);
        btnContinue=findViewById(R.id.buttonContinue);
        btnCancel=findViewById(R.id.buttonCancle);
        auth=FirebaseAuth.getInstance();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=txtPasswordEmail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(ForgotPassword.this,"Please enter your registered email ID",Toast.LENGTH_LONG).show();
                }else{
                    auth.sendPasswordResetEmail(useremail).addOnCompleteListener(ForgotPassword.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Password reset email sent", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            }else{
                                Toast.makeText(ForgotPassword.this, "Error in sending password reset", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
    }
}