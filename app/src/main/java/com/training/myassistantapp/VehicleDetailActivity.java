package com.training.myassistantapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.training.myassistantapp.Adapter.CustomersAdapter;
import com.training.myassistantapp.Listener.OnRecyclerItemClickListener;
import com.training.myassistantapp.model.User;
import com.training.myassistantapp.model.Util;

import java.util.ArrayList;
import java.util.List;

public class VehicleDetailActivity extends AppCompatActivity implements OnRecyclerItemClickListener {

    ContentResolver resolver;
    RecyclerView recyclerView;
    //Complaints complaints;

//    ArrayList<User> users;
    ArrayList<VehicleRegistration> vehicles;
    CustomersAdapter customersAdapter;
    VehicleRegistration vehicleRegistration;
    int position;
    User user;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    void initViews() {
        resolver = getContentResolver();
        recyclerView = findViewById(R.id.recyclerView);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vehicles);
        initViews();

        if (Util.isInternetConnected(this)) {
            //fetchCustomersFromDB();
            fetchCustomersFromCloudDB();

        } else {
            Toast.makeText(VehicleDetailActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }


    }
    void fetchCustomersFromCloudDB(){

        Log.i("TEST","UID:"+firebaseUser.getUid());
        Toast.makeText(VehicleDetailActivity.this,"Uid"+firebaseUser.getUid(),Toast.LENGTH_LONG).show();

        db.collection("users").document(firebaseUser.getUid())
                .collection("vehicles").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){

                            //users = new ArrayList<>();
                            vehicles = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();
                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();

                            for(DocumentSnapshot snapshot : documentSnapshots){
                                String Uid = snapshot.getId();


                                VehicleRegistration vehicle = snapshot.toObject(VehicleRegistration.class);
                                vehicle.Uid = Uid;

                                //users.add(user);
                                vehicles.add(vehicle);
                            }

                            getSupportActionBar().setTitle("Total Vehicles: "+vehicles.size());

                            customersAdapter = new CustomersAdapter(VehicleDetailActivity.this,R.layout.list_item,vehicles);

                            customersAdapter.setOnRecyclerItemClickListener(VehicleDetailActivity.this);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VehicleDetailActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(customersAdapter);

                        }else{
                            Toast.makeText(VehicleDetailActivity.this,"Some Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


    void showVehicleDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(vehicleRegistration.Make+" Details:");
        builder.setMessage(vehicleRegistration.toString());
        builder.setPositiveButton("Done",null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    void deleteCustomerFromCloudDB(){
        db.collection("users").document(firebaseUser.getUid())
                .collection("vehicles").document(vehicleRegistration.Uid)
                .delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            Toast.makeText(VehicleDetailActivity.this,"Deletion Finished",Toast.LENGTH_LONG).show();
                            vehicles.remove(position);
                            customersAdapter.notifyDataSetChanged(); // Refresh Your RecyclerView
                        }else{
                            Toast.makeText(VehicleDetailActivity.this,"Deletion Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+vehicleRegistration.Make);
        builder.setMessage("Are You Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // deleteCustomerFromDB();

                deleteCustomerFromCloudDB();
            }
        });

        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"View "+vehicleRegistration.Make, "Update "+vehicleRegistration.Make, "Delete "+vehicleRegistration.Make, "Cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        showVehicleDetails();
                        break;

                    case 1:

                        Intent intent = new Intent(VehicleDetailActivity.this, HomeActivity.class);
                        intent.putExtra("keyvehicles",vehicles);
                        startActivity(intent);

                        break;

                    case 2:
                        askForDeletion();
                        break;

                }

            }
        });

        //builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onItemClick(int position) {
        this.position = position;
        vehicleRegistration = vehicles.get(position);
        //Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
        showOptions();
    }

}