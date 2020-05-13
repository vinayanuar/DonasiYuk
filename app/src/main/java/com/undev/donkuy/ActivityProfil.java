package com.undev.donkuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.undev.donkuy.databinding.ActivityProfilBinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfil extends AppCompatActivity {

    ActivityProfilBinding binding;
    DataHelper dataHelper;
    SQLiteDatabase dbR;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_UNAME = "username";
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
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
            binding.username.setText(cursor.getString(0));
            binding.nama.setText(cursor.getString(1));
            binding.spinjk.setSelection(cursor.getInt(2));
            binding.npm.setText(cursor.getString(3));
            binding.telepon.setText(cursor.getString(4));
            byte[] image = cursor.getBlob(6);

            if (image != null) {
                Bitmap bm = byteArrayToBitmap(image);
                binding.fotoProfil.setImageBitmap(bm);
            }
        }

        binding.fotoProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] dialogitem = {"Gunakan Kamera", "Pilih gambar di Galeri"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityProfil.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Dexter.withActivity(ActivityProfil.this)
                                        .withPermission(Manifest.permission.CAMERA)
                                        .withListener(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
                                            }

                                            @Override
                                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                                Toast.makeText(ActivityProfil.this, "Izin ditolak!", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                                token.continuePermissionRequest();
                                            }
                                        }).check();
                                break;
                            case 1:
                                startActivityForResult(Intent.createChooser(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                                        "Pilih Foto: "), 2);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        binding.simpanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.nama.getText().toString()))
                    Toast.makeText(ActivityProfil.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(binding.username.getText().toString()))
                    Toast.makeText(ActivityProfil.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(binding.npm.getText().toString()))
                    Toast.makeText(ActivityProfil.this, "NPM/NIDN tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(binding.telepon.getText().toString()))
                    Toast.makeText(ActivityProfil.this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else {
                    updateProfil(user, binding.nama, binding.spinjk, binding.npm, binding.telepon);
                    Toast.makeText(ActivityProfil.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                binding.fotoProfil.setImageBitmap(bitmap);
                if (binding.fotoProfil.getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) binding.fotoProfil.getDrawable();
                    Bitmap bmp = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] byteArray = stream.toByteArray();
                    SQLiteDatabase db = dataHelper.getWritableDatabase();
                    String sql = "Update user set foto=? where username=?";
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindBlob(1, byteArray);
                    statement.bindString(2, user);
                    statement.executeInsert();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.fotoProfil.setImageBitmap(photo);
            if (binding.fotoProfil.getDrawable() != null) {
                byte[] imagex = imageToByte(binding.fotoProfil);
                SQLiteDatabase db = dataHelper.getWritableDatabase();
                String sql = "Update user set foto=? where username=?";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindBlob(1, imagex);
                statement.bindString(2, user);
                statement.executeInsert();
            }
        }
    }

    public byte[] imageToByte(CircleImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void updateProfil(String username, TextInputEditText nama, Spinner
            spinjk, TextInputEditText npm, TextInputEditText telepon) {
        SQLiteDatabase dbW = dataHelper.getWritableDatabase();
        String sql = "update user set nama=?, jeniskel=?, npm=?, hp=? where username=?";
        SQLiteStatement statement = dbW.compileStatement(sql);
        statement.bindString(1, nama.getText().toString());
        statement.bindLong(2, spinjk.getSelectedItemPosition());
        statement.bindString(3, npm.getText().toString());
        statement.bindString(4, telepon.getText().toString());
        statement.bindString(5, username);
        statement.executeInsert();
    }
}
