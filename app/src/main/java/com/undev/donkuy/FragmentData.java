package com.undev.donkuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.undev.donkuy.databinding.FragmentDataBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentData extends Fragment {

    FragmentDataBinding binding;
    AdapterDonasi adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        Gson gson = new Gson();
        String response = appSharedPrefs.getString("donasiku", "");
        final ArrayList<ModelDonasi> lstArrayList = gson.fromJson(response,
                new TypeToken<List<ModelDonasi>>() {
                }.getType());
        adapter = new AdapterDonasi(getContext());
        adapter.setProfil(lstArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.myDonasi.setLayoutManager(gridLayoutManager);
        binding.myDonasi.setAdapter(adapter);
        adapter.setOnClick(new AdapterDonasi.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), ActivityDetailDonasi.class)
                        .putParcelableArrayListExtra("ads", lstArrayList));
            }
        });
        return root;
    }
}
