package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.undev.donkuy.databinding.ActivityPengaturanBinding;

public class ActivityPengaturan extends AppCompatActivity {

    ActivityPengaturanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengaturanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.gantisandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityPengaturan.this, ActivityGantiSandi.class));
            }
        });
        binding.ubahakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityPengaturan.this, ActivityProfil.class));
            }
        });
    }
}
