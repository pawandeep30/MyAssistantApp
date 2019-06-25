package com.training.myassistantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.training.myassistantapp.model.User;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FirebaseAuth auth;



    public FloatingActionButton btnfab;
        double lat;
        double lng;

    ProgressDialog progressDialog;
// LocationListener locationListener;
    FirebaseUser firebaseUser;
      User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



       btnfab = (FloatingActionButton) findViewById(R.id.fab);

        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MapsActivity.this, "Location Saved Successfully", Toast.LENGTH_LONG).show();
            }

        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(MapsActivity.this,AddVehicleActivity.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lng);
                startActivity(intent);
                finish();
            }

        });


        Intent rcv = getIntent();
        lat = rcv.getDoubleExtra("latitude", 30.9024825);
        lng = rcv.getDoubleExtra("longitude", 75.8201934);
        String text = rcv.getStringExtra("address");
        LatLng ldh = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(ldh).title(text).snippet("SET INCIDENT LOCATION"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ldh, 12));


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setTrafficEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //saveUserInCloudDB();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1,101,1,"Log Out");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(item.getItemId() == 101){
            auth.signOut();
            Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    }



