package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import model.Benutzer;

public class PWAendernActivity extends AppCompatActivity {

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
    private EditText textPW;

    // Model
    private Benutzer aktuellerBenutzer;

    // Toast
    private Toast pwGeandertToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwaendern);

        // Buttons mit Zahlen initialisieren
        erstelleListener(kreisZahl1, R.id.kreisZahl1, "1");
        erstelleListener(kreisZahl2, R.id.kreisZahl2, "2");
        erstelleListener(kreisZahl3, R.id.kreisZahl3, "3");
        erstelleListener(kreisZahl4, R.id.kreisZahl4, "4");
        erstelleListener(kreisZahl5, R.id.kreisZahl5, "5");
        erstelleListener(kreisZahl6, R.id.kreisZahl6, "6");
        erstelleListener(kreisZahl7, R.id.kreisZahl7, "7");
        erstelleListener(kreisZahl8, R.id.kreisZahl8, "8");
        erstelleListener(kreisZahl9, R.id.kreisZahl9, "9");

        // Widgets
        textPW = findViewById(R.id.textPW);

        //Toasts initialisieren
        pwGeandertToast = Toast.makeText(this, "Passwort fehlerhaft", Toast.LENGTH_SHORT);

        // Benutzer laden
        ladeBenutzer();
    }

    // Methode des Buttons
    public void onclickPWAendern(View view){
        // Update des Passworts
        updateBenutzer();

        // Toast anzeigen
        pwGeandertToast.show();

        // Zurueck zur NotizenActivity
        Intent notizenActivity = new Intent(this, NotizenActivity.class);
        startActivity(notizenActivity);
    }

    // Listener für Zahlenfelder
    public void erstelleListener(ImageView zahl, int viewById, String ausgabe){
        zahl = (ImageView) findViewById(viewById);
        zahl.setClickable(true);

        zahl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aktuellerWert = textPW.getText().toString();
                aktuellerWert = aktuellerWert.concat(ausgabe);
                textPW.setText(aktuellerWert);
            }
        });
    }

    // Aktueller Benutzer wird im Attribut aktuellerBenutzer geladen
    public void ladeBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>(){}.getType();

        aktuellerBenutzer = gson.fromJson(json, type);
    }

    // Benutzer wird mit neuem Passwort geupdated
    public void updateBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        aktuellerBenutzer.setPasswort(textPW.getText().toString());

        String jsonString = gson.toJson(aktuellerBenutzer);
        editor.putString("Benutzer", jsonString);
        editor.apply();
    }
}