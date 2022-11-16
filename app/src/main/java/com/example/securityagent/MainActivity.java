package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import json.Benutzer;
import json.Utils;

public class MainActivity extends AppCompatActivity {

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

        // JSON lesen
        String jsonString = Utils.leseBenutzer(this, "user.json");
        Benutzer[] benutzer = new Gson().fromJson(jsonString, Benutzer[].class);
        aktuellerBenutzer = benutzer[0];

        // Benutzer-Attribute initialisieren
        anzMaxFehlversuche = aktuellerBenutzer.getAnzVersuche();
        passwort = aktuellerBenutzer.getPasswort();

        // Toasts initialisieren
        pwFalschToast = Toast.makeText(this, "Passwort fehlerhaft", Toast.LENGTH_SHORT);
        fotoGemachtToast = Toast.makeText(this, "Ein Foto von dir wurde gemacht", Toast.LENGTH_SHORT);
    }

    public void anmeldenOnClick(View view){
        if(pwText.getText().toString().equals(passwort)) {
            Intent activity = new Intent(this, NotizenActivity.class);
            startActivity(activity);
        } else {
            anzAktuelleVersuche++;
            pwText.setText("");
            pwFalschToast.show();
            if(anzAktuelleVersuche >= anzMaxFehlversuche){
                fotoGemachtToast.show();
                anmeldeButton = findViewById(R.id.buttonAnmelden);
                anmeldeButton.setEnabled(false);
            }
        }
    }

    public void erstelleListener(ImageView zahl, int viewById, String ausgabe){
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
}