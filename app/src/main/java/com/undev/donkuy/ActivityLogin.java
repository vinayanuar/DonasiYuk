package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.undev.donkuy.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {

    ActivityLoginBinding binding;
    DataHelper dataHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_UNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataHelper = new DataHelper(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.username.getText().toString())) {
                    Toast.makeText(ActivityLogin.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.password.getText().toString())) {
                    Toast.makeText(ActivityLogin.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    ModelUser user = dataHelper.queryUser(binding.username.getText().toString(),
                            binding.password.getText().toString());
                    if (user != null) {
                        editor.putString(KEY_UNAME, binding.username.getText().toString());
                        editor.putString(KEY_PASSWORD, binding.password.getText().toString());
                        editor.apply();
                        UtilsPreferences.saveSharedSetting(ActivityLogin.this, "PASS", "false");
                        UtilsPreferences.SharedPrefesSAVE(getApplicationContext(), binding.username.getText().toString());
                        Intent ImLoggedIn = new Intent(getApplicationContext(), ActivityMain.class);
                        startActivity(ImLoggedIn);
                        finish();
                    } else {
                        Toast.makeText(ActivityLogin.this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                        binding.password.setText("");
                    }
                }
            }
        });
        binding.daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ActivityDaftar.class));
            }
        });
    }
}
