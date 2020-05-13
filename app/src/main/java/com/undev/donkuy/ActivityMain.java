package com.undev.donkuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.undev.donkuy.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ActivityMain extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomSheetTopUp bottomSheetTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_data, R.id.navigation_akun)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        bottomSheetTopUp= BottomSheetTopUp.newInstance("Rider bottom sheet");
        binding.dompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetTopUp.show(getSupportFragmentManager(),bottomSheetTopUp.getTag());
            }
        });
    }

}
