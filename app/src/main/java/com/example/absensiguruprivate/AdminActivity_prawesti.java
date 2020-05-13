package com.example.absensiguruprivate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.example.absensiguruprivate.model.GuruItem_prawesti;
import com.example.absensiguruprivate.rest.ApiClient_prawesti;
import com.example.absensiguruprivate.rest.ApiInterface_prawesti;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity_prawesti extends AppCompatActivity {
    private Session_prawesti session;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_prawesti);

        session = new Session_prawesti(getApplicationContext());

        final RecyclerView guruView = findViewById(R.id.rv_guru);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List guru = new ArrayList<>();

        ApiInterface_prawesti apiInterface = ApiClient_prawesti.getClient().create(ApiInterface_prawesti.class);
        Call<List<GuruItem_prawesti>> call = apiInterface.getGuru();

        call.enqueue(new Callback<List<GuruItem_prawesti>>() {
            @Override
            public void onResponse(Call<List<GuruItem_prawesti>> call, Response<List<GuruItem_prawesti>> response) {
                List<GuruItem_prawesti> guruItems = response.body();

                if (response.isSuccessful()) {
                    for (GuruItem_prawesti item : guruItems) {
                        guru.add(new GuruItem_prawesti(item.getId_guru(), item.getNama(), item.getAlamat(), item.getJenis_kelamin(),
                                item.getNo_telp(), item.getFoto(), item.getUsername(), item.getPassword()));
                    }

                    itemAdapter.add(guru);
                    guruView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    guruView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<GuruItem_prawesti>> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });

    }
    public void myOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FormActivity_prawesti.class);
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
