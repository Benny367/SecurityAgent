package com.example.securityagent;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MakePhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE},1);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_photo);


        imageView = findViewById(R.id.bildDieb);

        //Oeffnet Kamera
        Intent oeffneKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityIfNeeded(oeffneKamera, 100);
    }


    //Setzt gemachtes Bild
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            assert data != null;
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

    }

    public void speichereGallerie(View view) {
        try {
            String fileName = "robber" + System.currentTimeMillis() + "_.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "robber");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/");
                values.put(MediaStore.MediaColumns.IS_PENDING, 1);
            } else {
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file = new File(directory, fileName);
                values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            }
            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try (OutputStream output = this.getContentResolver().openOutputStream(uri)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                output.flush();
            }
            Toast.makeText(this, "Photo saved to Gallery", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("EXCEPTION", e.toString());
        }
    }
}
