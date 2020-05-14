package com.example.absensiguruprivate.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.absensiguruprivate.MapsActivity_prawesti;
import com.example.absensiguruprivate.R;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

public class AbsensiItem_prawesti extends AbstractItem<AbsensiItem_prawesti, AbsensiItem_prawesti.ViewHolder> {
    private String username;
    private String password;
    private String jam_login;
    private String jam_logout;
    private String tanggal;
    private double lokasi_latitude;
    private double lokasi_longitude;

    public AbsensiItem_prawesti(String username, String password, String jam_login, String jam_logout, String tanggal, double lokasi_latitude, double lokasi_longitude) {
        this.username = username;
        this.password = password;
        this.jam_login = jam_login;
        this.jam_logout = jam_logout;
        this.tanggal = tanggal;
    }

    public String getUsername() {

        return username;
    }

    public double getLokasi_latitude() {
        return lokasi_latitude;
    }

    public double getLokasi_longitude() {
        return lokasi_longitude;
    }
    public String getPassword() {

        return password;
    }

    public String getJam_login() {

        return jam_login;
    }

    public String getJam_logout() {
        return jam_logout;
    }

    public String getTanggal() {
        return tanggal;
    }

    @NonNull
    @Override
    public AbsensiItem_prawesti.ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.rv_absen;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_absensi_prawesti;
    }


    public class ViewHolder extends FastAdapter.ViewHolder<AbsensiItem_prawesti> {
        private TextView jam_login, jam_logout, tanggal, latitude, longitude;

        public ViewHolder(View itemView) {
            super(itemView);
            jam_login = itemView.findViewById(R.id.txt_jam_login);
            jam_logout = itemView.findViewById(R.id.txt_jam_logout);
            tanggal = itemView.findViewById(R.id.txt_tanggal);
            latitude = itemView.findViewById(R.id.txt_lokasi_latitude);
            longitude = itemView.findViewById(R.id.txt_lokasi_longitude);
        }

        @Override
        public void bindView(final AbsensiItem_prawesti item, List<Object> payloads) {
                jam_login.setText(item.jam_login);
                jam_logout.setText(item.jam_logout);
                tanggal.setText(item.tanggal);
                latitude.setText(String.valueOf(item.lokasi_latitude));
                longitude.setText(String.valueOf(item.lokasi_longitude));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, MapsActivity_prawesti.class);
                        intent.putExtra("latitude", item.lokasi_latitude);
                        intent.putExtra("longitude", item.lokasi_longitude);
                        context.startActivity(intent);
                    }
                });
            }

        @Override
        public void unbindView(AbsensiItem_prawesti item) {
            jam_login.setText(null);
            jam_logout.setText(null);
            tanggal.setText(null);
            latitude.setText(null);
            longitude.setText(null);
        }
    }
}
