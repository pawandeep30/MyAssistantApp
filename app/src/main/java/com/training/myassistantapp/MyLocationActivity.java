package com.training.myassistantapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MyLocationActivity extends AppCompatActivity implements LocationListener {


    TextView txtLocation;
    Button btnFetchLocation;
    FirebaseAuth auth;

    LocationManager locationManager;

    ProgressDialog progressDialog;
    String  Address;

    void initViews() {
        txtLocation = findViewById(R.id.textViewLocation);
        btnFetchLocation = findViewById(R.id.buttonFetch);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Location...");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnFetchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MyLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyLocationActivity.this,"Please Grant Permissions in Settings and Try Again", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.show();
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * 60, 10, (LocationListener) MyLocationActivity.this);
                }

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        initViews();
    }

    @Override
    public void onLocationChanged(Location location) {

        // Geo-Coding -> Getting co-ordinates form CTT/GPS
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        //txtLocation.setText("Location: "+latitude+" : "+longitude);

        // If we wish to fetch location only once
        locationManager.removeUpdates(this);


        // Lets fetch Address from Latitude and Longitude | Reverse Geo-Coding
        try {

            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);

            StringBuffer buffer = new StringBuffer();

            if(addresses!=null && addresses.size()>0) {
                Address address = addresses.get(0);



                for(int i=0;i<=address.getMaxAddressLineIndex();i++){
                    buffer.append(address.getAddressLine(i)+"\n");
                }
                Address = buffer.toString();

            }

            txtLocation.setText("Location: "+latitude+" : "+longitude+"\nAddress:"+Address);

            Intent intent = new Intent(MyLocationActivity.this, MapsActivity.class);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            intent.putExtra("address",buffer.toString());
            Toast.makeText(MyLocationActivity.this, "Location fetched Successfully", Toast.LENGTH_LONG).show();
            startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }




}