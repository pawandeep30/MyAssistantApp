package com.training.myassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OthersIncidentActivity extends AppCompatActivity {

    Button btncarnotstart,btnbreakproblem,btnclutchproblem,btnsteering,btnwarninglight;

    void initViews(){

        btncarnotstart = findViewById(R.id.buttonCar);
        btnbreakproblem = findViewById(R.id.buttonBreak);
        btnclutchproblem = findViewById(R.id.buttonClutch);
        btnsteering = findViewById(R.id.buttonSteering);
        btnwarninglight = findViewById(R.id.buttonWarning);

        btncarnotstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnbreakproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnclutchproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnsteering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnwarninglight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersIncidentActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_incident);
        initViews();
    }
}
