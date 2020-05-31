package com.example.absensiguruprivate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity_prawesti extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Session_prawesti session;
    private double latitude, longitude;
    private FloatingActionButton fab, fab1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_prawesti);

        session = new Session_prawesti(getApplicationContext());

        fab = findViewById(R.id.btn_check);
        if (session.getLoggedInRole().equals("admin")) {
            fab.setVisibility(View.INVISIBLE);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        Toast.makeText(MapsActivity_prawesti.this,
                "Lat : " + latitude + " Long : " + longitude,
                Toast.LENGTH_LONG).show();

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Lokasi saat ini"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
    }
    public void onClickBack(View view) {
        Intent intent = new Intent(MapsActivity_prawesti.this, SiswaPrivateActivity_prawesti.class);
        startActivity(intent);
    }

    public void onClickCheck(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity_prawesti.this);
        builder.setCancelable(false);
        builder.setMessage("Apakah lokasi anda sudah sesuai?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MapsActivity_prawesti.this, DetailAbsen_prawesti.class);
                intent.putExtra("username", session.getUsername());
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
