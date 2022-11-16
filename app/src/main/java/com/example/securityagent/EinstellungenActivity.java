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

import json.Benutzer;

public class EinstellungenActivity extends AppCompatActivity {

    private Switch securityManagerSwitch;
    private Switch lokalSwitch;
    private Button emailBearbeitenButton;
    private EditText emailTextfield;
    private SeekBar seekBar;
    private Button pwAendernButtonEIN;
    private Button zurueckButton;

    private Benutzer aktuellerBenutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        securityManagerSwitch = findViewById(R.id.securityManagerSwitch);
        lokalSwitch = findViewById(R.id.lokalSwitch);
        emailBearbeitenButton = findViewById(R.id.emailBearbeitenButton);
        emailTextfield = findViewById(R.id.emailTextfield);
        seekBar = findViewById(R.id.seekBar);
        pwAendernButtonEIN = findViewById(R.id.pwAendernButtonEIN);
        zurueckButton = findViewById(R.id.zurueckButton);

        ladeBenutzer();

        Intent activity = new Intent(this, NotizenActivity.class);
        zurueckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBenutzer();
                startActivity(activity);
            }
        });

        securityManagerSwitch.setChecked(getSecurityManagerOnOff());
        lokalSwitch.setChecked(getLokalSwitchOnOff());
        emailTextfield.setText(aktuellerBenutzer.getEmail());
        seekBar.setMax(10);
        seekBar.setProgress(aktuellerBenutzer.getAnzVersuche());
    }

    public void ladeBenutzer(){
        SharedPreferences sharedPreferences = getSharedPreferences("benutzerSpeichern", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Benutzer", null);
        Type type = new TypeToken<Benutzer>(){}.getType();

        aktuellerBenutzer = gson.fromJson(json, type);
    }

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

    public boolean getSecurityManagerOnOff(){
        return aktuellerBenutzer.isAktiv();
    }

    public boolean getLokalSwitchOnOff(){
        return aktuellerBenutzer.isLokalSpeichern();
    }
}