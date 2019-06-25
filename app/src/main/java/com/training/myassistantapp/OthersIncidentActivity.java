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

public class OthersIncidentActivity extends AppCompatActivity implements View.OnClickListener {

    Button btncarnotstart,btnbreakproblem,btnclutchproblem,btnsteering,btnwarninglight;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    Complaints complaints;
 FirebaseInstanceId firebaseInstanceId;

    FirebaseFirestore db;


    void initViews(){

        btncarnotstart = findViewById(R.id.buttonCar);
        btnbreakproblem = findViewById(R.id.buttonBreak);
        btnclutchproblem = findViewById(R.id.buttonClutch);
        btnsteering = findViewById(R.id.buttonSteering);
        btnwarninglight = findViewById(R.id.buttonWarning);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        complaints = new Complaints();

        firebaseInstanceId = FirebaseInstanceId.getInstance();


        btncarnotstart.setOnClickListener(this);
        btnbreakproblem.setOnClickListener(this);
        btnclutchproblem.setOnClickListener(this);
        btnsteering.setOnClickListener(this);
        btnwarninglight.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_incident);
        initViews();
    }



    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.buttonCar) {
            complaints.issue = btncarnotstart.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
            }

        } else if (id == R.id.buttonBreak) {
            complaints.issue = btnbreakproblem.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
                //Intent intent = new Intent(SubmitAnIncidentActivity.this, MyLocationActivity.class);
                // startActivity(intent);
            }
        } else if (id == R.id.buttonClutch) {
            complaints.issue = btnclutchproblem.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
            }
        }

        else if (id == R.id.buttonSteering) {
            complaints.issue = btnsteering.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();

            }
        }

        else if (id == R.id.buttonWarning) {
            complaints.issue = btnwarninglight.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();

            }
        }


    }

     void saveComplaintInCloudDB() {

         firebaseUser = auth.getCurrentUser();
         db.collection("users").document(auth.getCurrentUser().getUid()).collection("vehicles").
                 document(auth.getCurrentUser().getUid()).collection("complaints").
                 add(complaints).addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
             @Override
             public void onComplete(@NonNull Task<DocumentReference> task) {
                 if (task.isComplete()) {
                     Toast.makeText(OthersIncidentActivity.this, complaints.issue + "Save Complaints Successfully", Toast.LENGTH_LONG).show();
                     //progressDialog.dismiss();
                      Intent intent = new Intent(OthersIncidentActivity.this, ConfirmActivity.class);
                      startActivity(intent);
                      finish();


                 }
             }
         });

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
            Intent intent = new Intent(OthersIncidentActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
