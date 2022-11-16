package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import json.Benutzer;

public class RegistrierenActivity extends AppCompatActivity {

    private EditText mailTextRegistrieren;
    private EditText pwTextRegistrieren;
    private EditText pwBestaetigenTextRegistrieren;

    private Toast pwsStimmenNicht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);

        // Widgets initialisieren
        mailTextRegistrieren = findViewById(R.id.mailTextRegistrieren);
        pwTextRegistrieren = findViewById(R.id.pwTextRegistrieren);
        pwBestaetigenTextRegistrieren = findViewById(R.id.pwBestaetigenTextRegistrieren);

        pwsStimmenNicht = Toast.makeText(this, "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT);
    }

    public void registrierenOnClick(View view){
        if(pwTextRegistrieren.getText().toString().equals(pwBestaetigenTextRegistrieren.getText().toString())) {
            erstelleBenutzer();
            Intent activity = new Intent(this, NotizenActivity.class);
            startActivity(activity);
        } else {
            pwsStimmenNicht.show();
            pwTextRegistrieren.setText("");
            pwBestaetigenTextRegistrieren.setText("");
        }
    }

    public void erstelleBenutzer(){
        Benutzer b = new Benutzer(3, mailTextRegistrieren.getText().toString(), true, true, pwTextRegistrieren.getText().toString());
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(b);
        editor.putString("Benutzer", jsonString);
        editor.apply();
    }
}