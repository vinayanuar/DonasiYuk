package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.undev.donkuy.databinding.ActivityDetailDonasiBinding;

import java.util.ArrayList;

public class ActivityDetailDonasi extends AppCompatActivity {

    ActivityDetailDonasiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDonasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final ArrayList<ModelDonasi> arrayList = getIntent().getParcelableArrayListExtra("ads");
        binding.imageView.setImageResource(arrayList.get(0).getImg());
        binding.dana.setText(arrayList.get(0).getTerkumpul() + " terkumpul dari " + arrayList.get(0).getTargetterkumpul());
        binding.totaldonasi.setText(arrayList.get(0).getDonasi() + " Donasi");
        binding.sisaHari.setText("Sisa Hari " + arrayList.get(0).getSisahari());
        binding.judul.setText(arrayList.get(0).getJudul());
        binding.subjudul.setText(arrayList.get(0).getSubjudul());
        binding.donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(ActivityDetailDonasi.this);
                Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(arrayList);
                editor.putString("donasiku", json);
                editor.commit();
                Toast.makeText(ActivityDetailDonasi.this, "Anda berhasil berdonasi", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
