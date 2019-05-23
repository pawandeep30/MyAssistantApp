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

public class TyreProblemActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnneedrepaire,btnneedspare;
    FirebaseAuth auth;

    FirebaseUser firebaseUser;
    Complaints complaints;
    FirebaseInstanceId firebaseInstanceId;

    FirebaseFirestore db;


    void initViews(){
        btnneedrepaire = findViewById(R.id.buttonNeedRepair);
        btnneedspare = findViewById(R.id.buttonNeedSpare);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        complaints = new Complaints();

        firebaseInstanceId = FirebaseInstanceId.getInstance();

        btnneedrepaire.setOnClickListener(this);
        btnneedspare.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyre_problem);
        initViews();
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.buttonNeedRepair) {
            complaints.issue = btnneedrepaire.getText().toString();
            if (Util.isInternetConnected(this)) {
                saveComplaintInCloudDB();
            }

        } else if (id == R.id.buttonNeedSpare) {
            complaints.issue = btnneedspare.getText().toString();
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
                    Toast.makeText(TyreProblemActivity.this, complaints.issue + "Save Complaints Successfully", Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                     Intent intent = new Intent(TyreProblemActivity.this, ConfirmActivity.class);
                     startActivity(intent);
                      finish();


                }
            }
        });

    }
}
