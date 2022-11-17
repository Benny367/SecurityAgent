package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import model.Benutzer;

public class NotizenActivity extends AppCompatActivity {

    // Widgets
    private FloatingActionButton settingsButton;
    private Button notizenSpeichernButton;
    private EditText notizenTextarea;

    // Benutzer
    private Benutzer aktuellerBenutzer;

    //Toasts
    Toast gespeichertToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notizen);

        // Widgets initialisieren
        settingsButton = findViewById(R.id.settingsButton);
        notizenSpeichernButton = findViewById(R.id.notizenSpeichernButton);
        notizenTextarea = findViewById(R.id.notizenTextarea);

        // Toast initialisieren
        gespeichertToast = Toast.makeText(this, "Änderungen abgespeichert", Toast.LENGTH_SHORT);

        // Benutzer laden
        ladeBenutzer();
    }

    // Methode des Speichern-Button
    public void notizenSpeichernOnClick(){
        Intent einstellungenActivity = new Intent(this, EinstellungenActivity.class);
        startActivity(einstellungenActivity);
    }

    // Methode des Einstellungen-Button
    public void zahnradOnClick(){
        gespeichertToast.show();
        updateBenutzer();
    }

    // Aktueller Benutzer wird im Attribut aktuellerBenutzer geladen
    public void ladeBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>(){}.getType();

        aktuellerBenutzer = gson.fromJson(json, type);

        notizenTextarea.setText(aktuellerBenutzer.getNotizen());
    }

    // Benutzer wird updated und in SharedPreferences gespeichert
    public void updateBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        aktuellerBenutzer.setNotizen(notizenTextarea.getText().toString());

        String jsonString = gson.toJson(aktuellerBenutzer);
        editor.putString("Benutzer", jsonString);
        editor.apply();
    }

}