package com.example.securityagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ImageView[] alleZahlen = {  kreisZahl1, kreisZahl2, kreisZahl3,
                                        kreisZahl4, kreisZahl5, kreisZahl6,
                                        kreisZahl7, kreisZahl8, kreisZahl9 };
    private EditText pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pwText = findViewById(R.id.textPW);

        erstelleListener(kreisZahl1, R.id.kreisZahl1, "1");
        erstelleListener(kreisZahl2, R.id.kreisZahl2, "2");
        erstelleListener(kreisZahl3, R.id.kreisZahl3, "3");
        erstelleListener(kreisZahl4, R.id.kreisZahl4, "4");
        erstelleListener(kreisZahl5, R.id.kreisZahl5, "5");
        erstelleListener(kreisZahl6, R.id.kreisZahl6, "6");
        erstelleListener(kreisZahl7, R.id.kreisZahl7, "7");
        erstelleListener(kreisZahl8, R.id.kreisZahl8, "8");
        erstelleListener(kreisZahl9, R.id.kreisZahl9, "9");
    }

    public void onclick(View view) {
        Intent activity = new Intent(this, NotizenActivity.class);
        startActivity(activity);
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