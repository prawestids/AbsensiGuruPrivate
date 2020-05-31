package com.example.absensiguruprivate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.example.absensiguruprivate.ui.SectionsPagerAdapter_prawesti;
import com.google.android.material.tabs.TabLayout;

public class AdminActivity_prawesti extends AppCompatActivity {
    private Session_prawesti session;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_prawesti);

        session = new Session_prawesti(getApplicationContext());
        SectionsPagerAdapter_prawesti sectionsPagerAdapter = new SectionsPagerAdapter_prawesti(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void myOnClickAddGuru(View view) {
        Intent intent = new Intent(getApplicationContext(), FormGuruActivity_prawesti.class);
        startActivity(intent);
    }

    public void myOnClickAddSiswa(View view) {
        Intent intent = new Intent(getApplicationContext(), FormSiswaActivity_prawesti.class);
        startActivity(intent);
    }

    public void myOnClickLogout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity_prawesti.this);
        builder.setCancelable(false);
        builder.setMessage("Apakah kamu ingin logout?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                session.logout();
                Toast.makeText(getApplicationContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginAdminActivity_prawesti.class);
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
