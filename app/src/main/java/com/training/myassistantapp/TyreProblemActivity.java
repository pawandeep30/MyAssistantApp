package com.training.myassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TyreProblemActivity extends AppCompatActivity {

    Button btnneedrepaire,btnneedspare;

    void initViews(){
        btnneedrepaire = findViewById(R.id.buttonNeedRepair);
        btnneedspare = findViewById(R.id.buttonNeedSpare);

        btnneedrepaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TyreProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnneedspare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TyreProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyre_problem);
        initViews();
    }
}
