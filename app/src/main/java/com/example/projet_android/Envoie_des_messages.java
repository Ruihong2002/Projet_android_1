package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Envoie_des_messages extends AppCompatActivity {
 //propriétés
     private EditText phone ;
     private EditText Message;
     private Button Envoi ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_des_messages);
        initactivity();
    }

    // initialisations
    private void initactivity(){

    phone = (EditText)findViewById(R.id.TextPhone);
    Message = (EditText)findViewById(R.id.TextMessage);
    Envoi = (Button)findViewById(R.id.BtnEnvoi);

    }



}