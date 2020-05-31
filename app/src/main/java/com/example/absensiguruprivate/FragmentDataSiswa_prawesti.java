package com.example.absensiguruprivate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.example.absensiguruprivate.model.SiswaItem_prawesti;
import com.example.absensiguruprivate.rest.ApiClient_prawesti;
import com.example.absensiguruprivate.rest.ApiInterface_prawesti;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDataSiswa_prawesti extends Fragment {
    public FragmentDataSiswa_prawesti() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_siswa_prawesti, container, false);

        Session_prawesti session = new Session_prawesti(getActivity());

        final RecyclerView siswaView = root.findViewById(R.id.rv_siswa);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List siswa = new ArrayList<>();

        ApiInterface_prawesti apiInterface = ApiClient_prawesti.getClient().create(ApiInterface_prawesti.class);
        Call<List<SiswaItem_prawesti>> call = apiInterface.getSiswa();

        call.enqueue(new Callback<List<SiswaItem_prawesti>>() {
            @Override
            public void onResponse(Call<List<SiswaItem_prawesti>> call, Response<List<SiswaItem_prawesti>> response) {
                List<SiswaItem_prawesti> SiswaItems = response.body();
                if (response.isSuccessful()) {
                    for (SiswaItem_prawesti item : SiswaItems) {
                        siswa.add(new SiswaItem_prawesti(item.getNim(), item.getNama(), item.getAlamat(),
                                item.getJenis_kelamin(), item.getTanggal_lahir(), item.getKelas()));
                    }

                    itemAdapter.add(siswa);
                    siswaView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    siswaView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getActivity(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SiswaItem_prawesti>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
