package com.example.absensiguruprivate;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SiswaPrivateActivity_prawesti extends AppCompatActivity {
    private Session_prawesti session;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_private_prawesti);

        session = new Session_prawesti(getApplicationContext());

        TextView result = findViewById(R.id.result);

        String nim = getIntent().getStringExtra("nim");
        String nama = getIntent().getStringExtra("nama");
        String alamat = getIntent().getStringExtra("alamat");

        session.setNimSiswa(nim);
        session.setNamaSiswa(nama);
        session.setAlamatSiswa(alamat);

        result.setText("Anda akan mengunjungi " + nama + ", di " + alamat + "\nSilahkan check in lokasi jika sudah sampai di rumah siswa");

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SiswaPrivateActivity_prawesti.this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(SiswaPrivateActivity_prawesti.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    session.setLocLatitude(latitude);
                    session.setLocLongitude(longitude);
                }
            }
        });
    }

    public void myOnClickMaps(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity_prawesti.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

}
