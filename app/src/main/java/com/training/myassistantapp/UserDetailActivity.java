package com.training.myassistantapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.training.myassistantapp.model.User;
import com.training.myassistantapp.model.Util;

public class UserDetailActivity extends AppCompatActivity {



    ContentResolver resolver;
    User user;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    TextView txtNAME;
    TextView txtEMAIL;
    TextView txtPHONE;


    void initViews() {
        resolver = getContentResolver();


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        txtNAME =findViewById(R.id.textViewNAME);
        txtEMAIL =findViewById(R.id.textViewEMAIL);
        txtPHONE = findViewById(R.id.textViewPHONE);





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        initViews();



        if (Util.isInternetConnected(this)) {
            //fetchCustomersFromDB();
            fetchCustomersFromCloudDB();

        } else {
            Toast.makeText(UserDetailActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }

    void fetchCustomersFromCloudDB() {

        Log.i("TEST", "UID:" + firebaseUser.getUid());
        Toast.makeText(UserDetailActivity.this, "Uid" + firebaseUser.getUid(), Toast.LENGTH_LONG).show();

       DocumentReference docRef =  db.collection("users").document(firebaseUser.getUid());


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(UserDetailActivity.this, ""+document.getData(), Toast.LENGTH_LONG).show();
                        User user = task.getResult().toObject(User.class);

                           txtNAME.setText("Name :"+user.name);
                           txtEMAIL.setText("Email :"+user.email);
                           txtPHONE.setText("Phone :"+user.phone);


//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
//
//      Log.d(TAG, "No such document");
                        Toast.makeText(UserDetailActivity.this,"No doc",Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(UserDetailActivity.this,"err",Toast.LENGTH_LONG).show();
                    Log.d("error", "get failed with ", task.getException());
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,101,1,"User Profile");
        menu.add(1,102,1,"Vehicle Details");
        menu.add(1,103,1,"Admin Login");
        menu.add(1,104,1,"Log Out");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if(id == 102) {
            Intent intent = new Intent(UserDetailActivity.this, VehicleDetailActivity.class);
            startActivity(intent);
        } else if(id == 103){
                Intent intent = new Intent(UserDetailActivity.this, AdminLoginActivity.class);
                startActivity(intent);
        }else if(item.getItemId() == 104){
            auth.signOut();
            Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}

