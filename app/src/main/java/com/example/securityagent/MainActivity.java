package com.example.securityagent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

import model.Benutzer;

public class MainActivity extends AppCompatActivity {

    //Zahlentasten
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

    private String passwort;
    private int anzMaxFehlversuche;
    private int anzAktuelleVersuche = 0;

    //Toasts
    Toast pwFalschToast;
    Toast fotoGemachtToast;

    private Benutzer aktuellerBenutzer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pwText = findViewById(R.id.textAnmeldenId);

        ladeBenutzer();

        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        if (aktuellerBenutzer != null) {
            if (!aktuellerBenutzer.isAktiv()) {
                Intent activity = new Intent(this, NotizenActivity.class);
                startActivity(activity);
            }
        }
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

    public void anmeldenOnClick(View view) {
        if (pwText.getText().toString().equals(passwort)) {
            Intent activity = new Intent(this, NotizenActivity.class);
            startActivity(activity);
        } else {
            anzAktuelleVersuche++;
            pwText.setText("");
            pwFalschToast.show();
            if (anzAktuelleVersuche >= anzMaxFehlversuche) {
                Intent machBild = new Intent(this, MakePhotoActivity.class);
                startActivity(machBild);

                fotoGemachtToast.show();
                anmeldeButton = findViewById(R.id.buttonAnmelden);
                anmeldeButton.setEnabled(false);
            }
        }
    }

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

    //Lade Benutzer Einstellungen
    public void ladeBenutzer() {
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>() {
        }.getType();

        aktuellerBenutzer = gson.fromJson(json, type);
    }
}