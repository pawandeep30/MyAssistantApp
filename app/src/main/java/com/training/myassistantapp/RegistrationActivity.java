package com.training.myassistantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.training.myassistantapp.model.User;
import com.training.myassistantapp.model.Util;

import static android.os.Build.VERSION_CODES.P;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

   // public String name, email, password;
    EditText eTxtName,eTxtPhone, eTxtEmail, eTxtPassword;
    TextView txtLogin;

    Button btnRegister;

    User user;


    FirebaseAuth auth;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    FirebaseUser firebaseUser;


    // FirebaseMessaging firebaseMessaging;
    FirebaseInstanceId firebaseInstanceId;

    void initViews() {
        eTxtName = findViewById(R.id.editTextName);
        eTxtPhone = findViewById(R.id.editTextPhone);
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        txtLogin = findViewById(R.id.textViewLogin);

        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        user = new User();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseInstanceId = FirebaseInstanceId.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.buttonRegister) {

         //Get the data from UI and put it into User Object
            user.name = eTxtName.getText().toString();
            user.phone = eTxtPhone.getText().toString();
            user.email = eTxtEmail.getText().toString();
            user.password = eTxtPassword.getText().toString();

            if(Util.isInternetConnected(this)) {
                registerUser();


            }else{
             Toast.makeText(this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
            }

        }
    }




    void registerUser() {
        String name = eTxtName.getText().toString().trim();
        String phone = eTxtPhone.getText().toString().trim();
        String email = eTxtEmail.getText().toString().trim();
        String password = eTxtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter Valid Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           // Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
//                        else {
//                            Toast.makeText(RegistrationActivity.this, "Could not Register! Please Try Again", Toast.LENGTH_SHORT).show();
//                        }
                        saveUserInCloudDB();
                    }

                });
    }

    void saveUserInCloudDB() {

//        db.collection("users").add(user)
//                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        if (task.isComplete()) {
//                            Toast.makeText(RegistrationActivity.this, user.name + "Registered Successfully", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
//                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });



        firebaseUser = auth.getCurrentUser();
        db.collection("users").document(firebaseUser.getUid()).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationActivity.this, user.name + " Registered Successfully ", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                        Intent intent = new Intent(RegistrationActivity.this, MyLocationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

          }



}
