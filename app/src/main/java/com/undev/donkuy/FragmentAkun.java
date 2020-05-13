package com.undev.donkuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.undev.donkuy.databinding.FragmentAkunBinding;

import java.io.ByteArrayInputStream;

public class FragmentAkun extends Fragment {

    FragmentAkunBinding binding;
    DataHelper dataHelper;
    SQLiteDatabase dbR;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_UNAME = "username";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAkunBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        dataHelper = new DataHelper(getContext());
        dbR = dataHelper.getReadableDatabase();
        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(KEY_UNAME, "");
        String query = "SELECT*FROM user WHERE username = ?";
        Cursor cursor = dbR.rawQuery(query, new String[]{user});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            binding.namaProfil.setText(cursor.getString(1));

            byte[] image = cursor.getBlob(6);

            if (image != null) {
                Bitmap bm = byteArrayToBitmap(image);
                binding.fotoProfil.setImageBitmap(bm);
            }
        }
        binding.pengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityPengaturan.class));
            }
        });
        binding.tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityTentang.class));
            }
        });
        binding.keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityLogin.class));
            }
        });
        return root;
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }
}
