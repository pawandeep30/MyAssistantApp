package com.training.myassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KeyProblemActivity extends AppCompatActivity {
    Button btnkey,btnlost;

    void initViews(){
        btnkey = findViewById(R.id.buttonKey);
        btnlost = findViewById(R.id.buttonLostKey);

        btnkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeyProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeyProblemActivity.this,MyLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_problem);
        initViews();
    }
}
