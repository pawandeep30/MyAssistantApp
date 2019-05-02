package com.training.myassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SubmitAnIncidentActivity extends AppCompatActivity {
    Button btnaccident,btntow,btnbattery,btntyre,btnlockedout,btnfuel,btnother;

    void initViews(){
        btnaccident = findViewById(R.id.buttonAccident);
        btntow = findViewById(R.id.buttonTow);
        btnbattery = findViewById(R.id.buttonBattery);
        btntyre = findViewById(R.id.buttonTyre);
        btnlockedout = findViewById(R.id.buttonLockedout);
        btnfuel = findViewById(R.id.buttonFuel);
        btnother = findViewById(R.id.buttonOther);

        btnaccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btntow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnbattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btntyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,TyreProblemActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnlockedout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,KeyProblemActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnfuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,FuelProblemActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitAnIncidentActivity.this,OthersIncidentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_an_incident);
        initViews();
    }




}

