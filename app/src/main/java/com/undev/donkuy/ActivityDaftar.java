package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.undev.donkuy.databinding.ActivityDaftarBinding;

public class ActivityDaftar extends AppCompatActivity {

    ActivityDaftarBinding binding;
    DataHelper dataHelper;
    SQLiteDatabase dbW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataHelper = new DataHelper(this);
        dbW = dataHelper.getWritableDatabase();
        binding.daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.nama.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.username.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.npm.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "NPM/NIDN tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.telepon.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.password.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.ulangipassword.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (!binding.password.getText().toString().equals(binding.ulangipassword.getText().toString())) {
                    Toast.makeText(ActivityDaftar.this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    if (CheckIsDataAlreadyInDBorNot("user", "username", binding.username.getText().toString()))
                        Toast.makeText(ActivityDaftar.this, "Username telah digunakan", Toast.LENGTH_SHORT).show();
                    else {
                        insertProfil(binding.username, binding.nama, binding.spinjk, binding.npm, binding.telepon, binding.password);
                        startActivity(new Intent(ActivityDaftar.this, ActivityLogin.class));
                    }
                }
            }
        });
    }

    public void insertProfil(TextInputEditText username, TextInputEditText nama, Spinner jenisKel,
                             TextInputEditText npm, TextInputEditText hp, TextInputEditText password) {
        SQLiteDatabase dbW = dataHelper.getWritableDatabase();
        String sqlInsertProfile = "insert into user(username, nama, jeniskel, npm, hp, password) values(?,?,?,?,?,?)";
        SQLiteStatement statementInsert = dbW.compileStatement(sqlInsertProfile);
        statementInsert.bindString(1, username.getText().toString());
        statementInsert.bindString(2, nama.getText().toString());
        statementInsert.bindLong(3, jenisKel.getSelectedItemPosition());
        statementInsert.bindString(4, npm.getText().toString());
        statementInsert.bindString(5, hp.getText().toString());
        statementInsert.bindString(6, password.getText().toString());
        statementInsert.executeInsert();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {
        String Query = "Select * from " + TableName + " where " + dbfield + "=" + "'" + fieldValue + "'";
        Cursor cursor = dbW.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
