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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.training.myassistantapp.model.Util;

import org.w3c.dom.Text;

public class SubmitAnIncidentActivity extends AppCompatActivity  implements View.OnClickListener {
    Button btnaccident,btntow,btnbattery,btntyre,btnlockedout,btnfuel,btnother;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;

    Complaints complaints;

    FirebaseInstanceId firebaseInstanceId;

    void initViews(){
        btnaccident = findViewById(R.id.buttonAccident);
        btntow = findViewById(R.id.buttonTow);
        btnbattery = findViewById(R.id.buttonBattery);
        btntyre = findViewById(R.id.buttonTyre);
        btnlockedout = findViewById(R.id.buttonLockedout);
        btnfuel = findViewById(R.id.buttonFuel);
        btnother = findViewById(R.id.buttonOther);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        complaints = new Complaints();

        // firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseInstanceId = FirebaseInstanceId.getInstance();

        btnaccident.setOnClickListener(this);
        btntow.setOnClickListener(this);
        btnbattery.setOnClickListener(this);
        btntyre.setOnClickListener(this);
        btnlockedout.setOnClickListener(this);
        btnfuel.setOnClickListener(this);
        btnother.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_an_incident);
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
            Intent intent = new Intent(SubmitAnIncidentActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if(id == 102){
            Intent intent = new Intent(SubmitAnIncidentActivity.this, AddVehicleActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == 103){
            auth.signOut();
            Intent intent = new Intent(SubmitAnIncidentActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.buttonAccident) {
            complaints.issue = btnaccident.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
            }

        } else if (id == R.id.buttonTow) {
            complaints.issue = btntow.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
                //Intent intent = new Intent(SubmitAnIncidentActivity.this, MyLocationActivity.class);
                // startActivity(intent);
            }
        } else if (id == R.id.buttonBattery) {
            complaints.issue = btnbattery.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
            }
        }

        else if (id == R.id.buttonTyre) {

            Intent intent = new Intent(SubmitAnIncidentActivity.this,TyreProblemActivity.class);
            startActivity(intent);
            finish();

        }


        else if (id == R.id.buttonLockedout) {
        Intent intent = new Intent(SubmitAnIncidentActivity.this,KeyProblemActivity.class);
        startActivity(intent);
        finish();

    }

        else if (id == R.id.buttonFuel) {
            Intent intent = new Intent(SubmitAnIncidentActivity.this,FuelProblemActivity.class);
            startActivity(intent);
            finish();

        }

        else if (id == R.id.buttonOther)
        {
            Intent intent = new Intent(SubmitAnIncidentActivity.this,OthersIncidentActivity.class);
                startActivity(intent);
                finish();

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
                    Toast.makeText(SubmitAnIncidentActivity.this, complaints.issue + "Save Complaints Successfully", Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                   // Intent intent = new Intent(SubmitAnIncidentActivity.this, SubmitAnIncidentActivity.class);
                   // startActivity(intent);
                  //  finish();


                }
            }
        });
    }
}

