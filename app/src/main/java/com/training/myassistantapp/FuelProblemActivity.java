package com.training.myassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FuelProblemActivity extends AppCompatActivity {

    Button btnwrongfuel,btnoutfuel;

    void initViews(){
        btnwrongfuel = findViewById(R.id.buttonWrongFuel);
        btnoutfuel = findViewById(R.id.buttonOutFuel);

        btnwrongfuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FuelProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnoutfuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FuelProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_problem);
        initViews();
    }
}
