package com.undev.donkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.undev.donkuy.databinding.ActivityGantiSandiBinding;

public class ActivityGantiSandi extends AppCompatActivity {

    ActivityGantiSandiBinding binding;
    DataHelper dataHelper;
    SQLiteDatabase dbR;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_UNAME = "username";
    String user;
    String currentPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGantiSandiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataHelper = new DataHelper(this);
        dbR = dataHelper.getReadableDatabase();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_UNAME, "");
        String query = "SELECT*FROM user WHERE username = ?";
        Cursor cursor = dbR.rawQuery(query, new String[]{user});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            currentPass = cursor.getString(5);
        }
        binding.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.password.getText().toString()))
                    Toast.makeText(ActivityGantiSandi.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(binding.newPass.getText().toString()))
                    Toast.makeText(ActivityGantiSandi.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(binding.ulangipassword.getText().toString()))
                    Toast.makeText(ActivityGantiSandi.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (!binding.newPass.getText().toString().equals(binding.ulangipassword.getText().toString()))
                    Toast.makeText(ActivityGantiSandi.this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                else if (!binding.password.getText().toString().equals(currentPass))
                    Toast.makeText(ActivityGantiSandi.this, "Kata Sandi Lama salah", Toast.LENGTH_SHORT).show();
                else {
                    updatePass(user, binding.newPass);
                    Toast.makeText(ActivityGantiSandi.this, "Kata Sandi Berhasil diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void updatePass(String username, TextInputEditText newPass) {
        SQLiteDatabase dbW = dataHelper.getWritableDatabase();
        String sql = "update user set password=? where username=?";
        SQLiteStatement statement = dbW.compileStatement(sql);
        statement.bindString(1, newPass.getText().toString());
        statement.bindString(2, username);
        statement.executeInsert();
    }
}
