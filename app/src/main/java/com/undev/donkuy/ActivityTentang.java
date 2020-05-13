package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.undev.donkuy.databinding.ActivityTentangBinding;

public class ActivityTentang extends AppCompatActivity {

    ActivityTentangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTentangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
