package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        final Button aBtnLogin = findViewById(R.id.button4);
        aBtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });
        final Button aBtnCreate = findViewById(R.id.button3);
        aBtnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

    }

}