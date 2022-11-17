package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import model.Benutzer;

public class EinstellungenActivity extends AppCompatActivity {

    // Widgets
    private Switch securityManagerSwitch;
    private Switch lokalSwitch;
    private EditText emailTextfield;
    private SeekBar seekBar;
    private Button pwAendernButtonEIN;
    private Button zurueckButton;

    // Benutzer
    private Benutzer aktuellerBenutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        // Widgets initialisieren
        securityManagerSwitch = findViewById(R.id.securityManagerSwitch);
        lokalSwitch = findViewById(R.id.lokalSwitch);
        emailTextfield = findViewById(R.id.emailTextfield);
        seekBar = findViewById(R.id.seekBar);
        pwAendernButtonEIN = findViewById(R.id.pwAendernButtonEIN);
        zurueckButton = findViewById(R.id.zurueckButton);

        // Benutzer laden
        ladeBenutzer();

        // Neue Werte des Benutzers anzeigen
        securityManagerSwitch.setChecked(aktuellerBenutzer.isAktiv());
        lokalSwitch.setChecked(aktuellerBenutzer.isLokalSpeichern());
        emailTextfield.setText(aktuellerBenutzer.getEmail());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(3);
        }
        seekBar.setMax(10);
        seekBar.setProgress(aktuellerBenutzer.getAnzVersuche());
    }

    // Methode des Zueruck-Button
    public void einstellungenOnClick(){
        updateBenutzer();
        Intent activityNotizen = new Intent(this, NotizenActivity.class);
        startActivity(activityNotizen);
    }

    // Methode des PasswortsAendern-Button
    public void passwortAendernOnClick(){
        Intent activityPWAendern = new Intent(this, PWAendernActivity.class);
        startActivity(activityPWAendern);
    }

    // Aktueller Benutzer wird im Attribut aktuellerBenutzer geladen
    public void ladeBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>(){}.getType();

        aktuellerBenutzer = gson.fromJson(json, type);
    }

    // Benutzer wird updated und in SharedPreferences gespeichert
    public void updateBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        aktuellerBenutzer.setAktiv(securityManagerSwitch.isChecked());
        aktuellerBenutzer.setLokalSpeichern(lokalSwitch.isChecked());
        aktuellerBenutzer.setAnzVersuche(seekBar.getProgress());
        aktuellerBenutzer.setEmail(emailTextfield.getText().toString());

        String jsonString = gson.toJson(aktuellerBenutzer);
        editor.putString("Benutzer", jsonString);
        editor.apply();
    }
}