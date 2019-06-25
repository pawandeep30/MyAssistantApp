package com.training.myassistantapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.training.myassistantapp.model.Util;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    Button buttonAdmin;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;


    void  initViews(){

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonAdmin = findViewById(R.id.buttonAdminLogin);

        buttonAdmin.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        initViews();
    }

    @Override
    public void onClick(View v) {


            if (Util.isInternetConnected(this)) {
                Adminlogin();
            }

    }



    void Adminlogin() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.equals("admin@mail.com")& password.equals("admin1")){
            firebaseUser = auth.getCurrentUser();
            auth.signInWithEmailAndPassword(email , password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isComplete()) {
                                Intent intent = new Intent(AdminLoginActivity.this,AllUsersActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    });
        }else{
            Toast.makeText(this,"Invalid Credential",Toast.LENGTH_LONG).show();
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1,101,1,"Log Out");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(item.getItemId() == 101){
            auth.signOut();
            Intent intent = new Intent(AdminLoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }



}

