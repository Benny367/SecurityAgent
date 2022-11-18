package com.example.securityagent;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.file.Paths;

import model.Benutzer;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private ImageView kreisZahl1;
    private ImageView kreisZahl2;
    private ImageView kreisZahl3;
    private ImageView kreisZahl4;
    private ImageView kreisZahl5;
    private ImageView kreisZahl6;
    private ImageView kreisZahl7;
    private ImageView kreisZahl8;
    private ImageView kreisZahl9;
    private Button anmeldeButton;
    private EditText pwText;

    // Daten des Benutzers
    private String passwort;
    private int anzMaxFehlversuche;
    private int anzAktuelleVersuche = 0;

    // Bitmap für das Bild
    private Bitmap bitmap;

    //Toasts
    Toast pwFalschToast;
    Toast fotoGemachtToast;

    // Benutzer
    private Benutzer aktuellerBenutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pwText = findViewById(R.id.textAnmeldenId);

        // Benutzer laden
        ladeBenutzer();

        // Abfrage, ob der Agent aktiv ist
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        if (aktuellerBenutzer != null) {
            if (!aktuellerBenutzer.isAktiv()) {
                Intent activity = new Intent(this, NotizenActivity.class);
                startActivity(activity);
            }
        }

        // Abfrage, ob ein Benutzer existiert
        if (sharedPreferences.getString("Benutzer", "DEFAULT").equals("DEFAULT")) {
            Intent activity = new Intent(this, RegistrierenActivity.class);
            startActivity(activity);
        }

        // Buttons mit Zahlen initialisieren
        erstelleListener(kreisZahl1, R.id.kreisZahl1AN, "1");
        erstelleListener(kreisZahl2, R.id.kreisZahl2AN, "2");
        erstelleListener(kreisZahl3, R.id.kreisZahl3AN, "3");
        erstelleListener(kreisZahl4, R.id.kreisZahl4AN, "4");
        erstelleListener(kreisZahl5, R.id.kreisZahl5AN, "5");
        erstelleListener(kreisZahl6, R.id.kreisZahl6AN, "6");
        erstelleListener(kreisZahl7, R.id.kreisZahl7AN, "7");
        erstelleListener(kreisZahl8, R.id.kreisZahl8AN, "8");
        erstelleListener(kreisZahl9, R.id.kreisZahl9AN, "9");

        // Benutzer-Attribute initialisieren
        if (aktuellerBenutzer != null) {
            anzMaxFehlversuche = aktuellerBenutzer.getAnzVersuche();
            passwort = aktuellerBenutzer.getPasswort();
        }

        // Toasts initialisieren
        pwFalschToast = Toast.makeText(this, "Passwort fehlerhaft", Toast.LENGTH_SHORT);
        fotoGemachtToast = Toast.makeText(this, "Ein Foto von dir wurde gemacht", Toast.LENGTH_SHORT);
    }

    // Methode des Anmelden-Button
    public void anmeldenOnClick(View view) {
        // Abfrage, ob Passwort stimmt
        if (pwText.getText().toString().equals(passwort)) {
            Intent activity = new Intent(this, NotizenActivity.class);
            startActivity(activity);
        } else {

            // Textfeld wird zurückgesetzt
            anzAktuelleVersuche++;
            pwText.setText("");
            // Toast
            pwFalschToast.show();
            // Abfrage, ob er schon genug Versuche hatte
            if (anzAktuelleVersuche >= anzMaxFehlversuche) {
                //Öffnet Kamera
                Intent oeffneKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityIfNeeded(oeffneKamera, 100);

                // Toast & Anmeldefunktion wird nicht verfügbar gemacht
                fotoGemachtToast.show();
                anmeldeButton = findViewById(R.id.buttonAnmelden);
                anmeldeButton.setEnabled(false);
            }
        }
    }

    // Listener für Zahlenfelder
    public void erstelleListener(ImageView zahl, int viewById, String ausgabe) {
        zahl = (ImageView) findViewById(viewById);
        zahl.setClickable(true);

        zahl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aktuellerWert = pwText.getText().toString();
                aktuellerWert = aktuellerWert.concat(ausgabe);
                pwText.setText(aktuellerWert);
            }
        });
    }

    // Aktueller Benutzer wird im Attribut aktuellerBenutzer geladen
    public void ladeBenutzer() {
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>() {
        }.getType();

        aktuellerBenutzer = gson.fromJson(json, type);
    }

    //Setzt gemachtes Bild
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            assert data != null;
            bitmap = (Bitmap) data.getExtras().get("data");
            speichereGallerie();

            // Schickt Mail
            sendeMail();
        }

    }

    //Speichert Bild in der Gallerie
    public void speichereGallerie() {
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

    // Schickt E-Mail mit dem Foto des Benutzers
    public void sendeMail() {
        String[] recipientList = {aktuellerBenutzer.getEmail()};
        String subject = "Ein Dieb wollte zuschlagen!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        startActivity(Intent.createChooser(intent,
                "Send Email Using: "));
    }
}