package com.undev.donkuy;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.undev.donkuy.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private String[] judul,subjudul,terkumpul,sisahari,targetterkumpul,donasi;
    private TypedArray image;
    AdapterDonasi adapter;
    ArrayList<ModelDonasi> arrayList;
    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        adapter = new AdapterDonasi(getContext());
        judul = getResources().getStringArray(R.array.judul);
        subjudul = getResources().getStringArray(R.array.subjudul);
        terkumpul = getResources().getStringArray(R.array.terkumpul);
        targetterkumpul = getResources().getStringArray(R.array.targetterkumpul);
        donasi = getResources().getStringArray(R.array.donasi);
        sisahari = getResources().getStringArray(R.array.sisahari);
        image = getResources().obtainTypedArray(R.array.gambar);
        arrayList = new ArrayList<>();
        for (int i = 0; i < judul.length; i++) {
            ModelDonasi modelDonasi = new ModelDonasi();
            modelDonasi.setJudul(judul[i]);
            modelDonasi.setSubjudul(subjudul[i]);
            modelDonasi.setTerkumpul(terkumpul[i]);
            modelDonasi.setTargetterkumpul(targetterkumpul[i]);
            modelDonasi.setDonasi(donasi[i]);
            modelDonasi.setSisahari(sisahari[i]);
            modelDonasi.setImg(image.getResourceId(i, -1));
            arrayList.add(modelDonasi);
        }
        adapter.setProfil(arrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recDonasi.setLayoutManager(gridLayoutManager);
        binding.recDonasi.setAdapter(adapter);

        adapter.setOnClick(new AdapterDonasi.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                ArrayList arrayList1 = new ArrayList<ModelDonasi>();
                ModelDonasi modelDonasi = new ModelDonasi();
                modelDonasi.setImg(image.getResourceId(position, -1));
                modelDonasi.setJudul(judul[position]);
                modelDonasi.setSubjudul(subjudul[position]);
                modelDonasi.setTerkumpul(terkumpul[position]);
                modelDonasi.setTargetterkumpul(targetterkumpul[position]);
                modelDonasi.setDonasi(donasi[position]);
                modelDonasi.setSisahari(sisahari[position]);
                arrayList1.add(modelDonasi);
                startActivity(new Intent(getContext(), ActivityDetailDonasi.class).putParcelableArrayListExtra("ads", arrayList1));
            }
        });
        return root;
    }
}
