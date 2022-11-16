package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if(pwTextRegistrieren.getText().toString().equals(pwBestaetigenTextRegistrieren)) {
            Intent activity = new Intent(this, NotizenActivity.class);
            startActivity(activity);
        } else {
            pwsStimmenNicht.show();
            pwTextRegistrieren.setText("");
            pwBestaetigenTextRegistrieren.setText("");
        }
    }
}