package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InterfacePrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        final Button pBtnBack = findViewById(R.id.button5);
        pBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });
        final Button pBtnProfil = findViewById(R.id.button6);
        pBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
    }
}