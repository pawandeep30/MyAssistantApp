package com.training.myassistantapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.training.myassistantapp.model.Util;

public class KeyProblemActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnkey,btnlost;
    FirebaseAuth auth;

    FirebaseUser firebaseUser;
    Complaints complaints;
    FirebaseInstanceId firebaseInstanceId;

    FirebaseFirestore db;

    void initViews(){
        btnkey = findViewById(R.id.buttonKey);
        btnlost = findViewById(R.id.buttonLostKey);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        complaints = new Complaints();

        firebaseInstanceId = FirebaseInstanceId.getInstance();


        btnkey.setOnClickListener(this);
        btnlost.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_problem);
        initViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,101,1,"User Profile");
        menu.add(1,102,1,"Vehicle Details");
        menu.add(1,103,1,"Log Out");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent intent = new Intent(KeyProblemActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if(id == 102){
            Intent intent = new Intent(KeyProblemActivity.this, AddVehicleActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == 103){
            auth.signOut();
            Intent intent = new Intent(KeyProblemActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
            if (id == R.id.buttonKey) {
                complaints.issue = btnkey.getText().toString();
                if (Util.isInternetConnected(this)) {
                    saveComplaintInCloudDB();
                }

            } else if (id == R.id.buttonLostKey) {
                complaints.issue = btnlost.getText().toString();
                if (Util.isInternetConnected(this)) {
                    saveComplaintInCloudDB();
                    //Intent intent = new Intent(SubmitAnIncidentActivity.this, MyLocationActivity.class);
                    // startActivity(intent);
                }
            }


        }

        void saveComplaintInCloudDB () {

            firebaseUser = auth.getCurrentUser();
            db.collection("users").document(auth.getCurrentUser().getUid()).collection("vehicles").
                    document(auth.getCurrentUser().getUid()).collection("complaints").
                    add(complaints).addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isComplete()) {
                        Toast.makeText(KeyProblemActivity.this, complaints.issue + "Save Complaints Successfully", Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                         Intent intent = new Intent(KeyProblemActivity.this, ConfirmActivity.class);
                         startActivity(intent);
                          finish();


                    }
                }
            });

        }
    }

