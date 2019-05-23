package com.training.myassistantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.Token;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.training.myassistantapp.model.User;
import com.training.myassistantapp.model.Util;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtmake,etxtmodel,etxtyear,etxtcolor;
    Button btnaddvehicle;
    double lat;
    double lng;

    VehicleRegistration vehicleRegistration;
    // MyLocationActivity myLocationActivity;
    ProgressDialog progressDialog;
     User user;

    FirebaseInstanceId firebaseInstanceId;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    void initViews(){

        Intent rcv = getIntent();
        lat = rcv.getDoubleExtra("latitude", 30.9024825);
        lng = rcv.getDoubleExtra("longitude", 75.8201934);
        String text = rcv.getStringExtra("address");
        LatLng ldh = new LatLng(lat, lng);


        etxtmake = findViewById(R.id.editTextMake);
        etxtmodel = findViewById(R.id.editTextModel);
        etxtyear = findViewById(R.id.editTextYear);
        etxtcolor = findViewById(R.id.editTextColor);
        btnaddvehicle = findViewById(R.id.buttonAddVehicle);


        btnaddvehicle.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        vehicleRegistration = new VehicleRegistration();
        firebaseInstanceId = FirebaseInstanceId.getInstance();


//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        initViews();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.buttonAddVehicle) {

            //Get the data from UI and put it into User Object
            vehicleRegistration.Make = etxtmake.getText().toString();
            vehicleRegistration.Model = etxtmodel.getText().toString();
            vehicleRegistration.Year = etxtyear.getText().toString();
            vehicleRegistration.Color = etxtcolor.getText().toString();
            vehicleRegistration.Latitude = lat;
            vehicleRegistration.Longitiude = lng;
            vehicleRegistration.Uid = auth.getCurrentUser().getUid();

            if (Util.isInternetConnected(this)) {
                saveVehicleInCloudDB();
            } else{
                Toast.makeText(this,"Please Connect to Internet and Try Again",Toast.LENGTH_LONG).show();
            }

        }
    }


    void saveVehicleInCloudDB(){

        firebaseUser = auth.getCurrentUser();
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("vehicles").
                add(vehicleRegistration).addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isComplete()) {
                    Toast.makeText(AddVehicleActivity.this, vehicleRegistration.Uid + "Save Vehicle Successfully", Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                    Intent intent = new Intent(AddVehicleActivity.this, SubmitAnIncidentActivity.class);
                    startActivity(intent);
                    finish();



                }
            }
        });


    }

}