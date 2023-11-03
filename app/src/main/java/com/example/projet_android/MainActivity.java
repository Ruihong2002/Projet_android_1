package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        final Button aBtnLogin = findViewById(R.id.button2);
        aBtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.interface_principal);
            }
        });
        final Button aBtnCreate = findViewById(R.id.button);
        aBtnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.create_account);
            }
        });

    }
}