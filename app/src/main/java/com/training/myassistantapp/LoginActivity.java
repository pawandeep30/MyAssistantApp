package com.training.myassistantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.training.myassistantapp.model.User;

import static com.training.myassistantapp.R.id.textViewforgot;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eTxtEmail, eTxtPassword;

    Button btnLogin;
    TextView txtforgot;
    TextView txtneedaccount;

    User user;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    void initViews() {
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        txtforgot = findViewById(textViewforgot);
        txtneedaccount = findViewById(R.id.textViewneedaccount);
        user = new User();

      btnLogin.setOnClickListener(this);
        txtneedaccount.setOnClickListener(this);
        txtforgot.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonLogin:
                user.email = eTxtEmail.getText().toString();
                user.password = eTxtPassword.getText().toString();
                // loginUser();
                if (TextUtils.isEmpty(user.email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(user.password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser();
                break;

            case R.id.textViewneedaccount:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.textViewforgot:
//              AlertDialog.Builder mbuilder=new AlertDialog.Builder(LoginActivity.this);
//              View mView=getLayoutInflater().inflate(R.layout.activity_forgot_password,null);

                Intent intent1 = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent1);
                finish();
                break;

        }
    }

//        user.email = eTxtEmail.getText().toString();
//        user.password = eTxtPassword.getText().toString();


    void loginUser() {

        progressDialog.show();
        firebaseUser = auth.getCurrentUser();
        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isComplete()) {
                            // there was an error

                            if (user.password.length() < 6) {
                                eTxtPassword.setError(getString(R.string.minimum_password));
                                progressDialog.dismiss();
                            } else {

                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                clearFields();
                                progressDialog.dismiss();
                            }
                        } else {

                            Toast.makeText(LoginActivity.this, " Login Successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, MyLocationActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                });

    }

    void clearFields() {
        eTxtEmail.setText("");
        eTxtPassword.setText("");
    }


}

