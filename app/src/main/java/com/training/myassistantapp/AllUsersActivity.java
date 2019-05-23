package com.training.myassistantapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.training.myassistantapp.Adapter.CustomersAdapter;
import com.training.myassistantapp.Adapter.UserAdapter;
import com.training.myassistantapp.Listener.OnRecyclerItemClickListener;
import com.training.myassistantapp.model.User;
import com.training.myassistantapp.model.Util;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity implements OnRecyclerItemClickListener {


    ContentResolver resolver;
    RecyclerView recyclerView;
    User user;
    VehicleRegistration vehicleRegistration;
    int position;
    VehicleDetailActivity vehicleDetailActivity;

    ArrayList<User> users;

     UserAdapter userAdapter;
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
        setContentView(R.layout.activity_all_users);
        initViews();

        if (Util.isInternetConnected(this)) {
            //fetchCustomersFromDB();
            fetchCustomersFromCloudDB();

        } else {
            Toast.makeText(AllUsersActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }

    }

    void fetchCustomersFromCloudDB() {

        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    users = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();

                    List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        String uid = snapshot.getId();
                        User user = snapshot.toObject(User.class);
                        user.uid = uid;
                        users.add(user);


                    }


                    getSupportActionBar().setTitle("Total Users: " + users.size());

                    userAdapter = new UserAdapter(AllUsersActivity.this, R.layout.listitem, users);

                    userAdapter.setOnRecyclerItemClickListener(AllUsersActivity.this);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllUsersActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(userAdapter);

                } else {
                    Toast.makeText(AllUsersActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    void  showvehicleDetail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(vehicleRegistration.Make+" Details:");
        builder.setMessage(vehicleRegistration.toString());
        builder.setPositiveButton("Done",null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    void showuserDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(user.name+" Details:");
        builder.setMessage(user.toString());
        builder.setPositiveButton("Done",null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    void deleteCustomerFromCloudDB(){
        db.collection("users").document(user.uid)
                .delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            Toast.makeText(AllUsersActivity.this,"Deletion Finished",Toast.LENGTH_LONG).show();
                            users.remove(position);
                            userAdapter.notifyDataSetChanged(); // Refresh Your RecyclerView
                        }else{
                            Toast.makeText(AllUsersActivity.this,"Deletion Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+user.uid);
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
        String[] items = {"View ", "Update ", "Delete ","Show Vehicles", "Cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        showuserDetails();
                        break;

                    case 1:

                        Intent intent = new Intent(AllUsersActivity.this, HomeActivity.class);
                        intent.putExtra("keyuser",users);
                        startActivity(intent);

                        break;

                    case 2:
                        askForDeletion();
                        break;

                    case 3:
                        Intent intent1 = new Intent(AllUsersActivity.this, VehicleDetailActivity.class);

                        startActivity(intent1);
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
        user = users.get(position);
        //Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
        showOptions();

        }


}
