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

public class FragmentDataGuru_prawesti extends Fragment {
    public FragmentDataGuru_prawesti() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_guru_prawesti, container, false);

        Session_prawesti session = new Session_prawesti(getActivity());

        final RecyclerView guruView = root.findViewById(R.id.rv_guru);
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

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    guruView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getActivity(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GuruItem_prawesti>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
