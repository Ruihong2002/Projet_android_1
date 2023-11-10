package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InterfacePrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        final Button pBtnBack = findViewById(R.id.button5);
        pBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
            }
        });
        final ImageButton pBtnProfil = (ImageButton)findViewById(R.id.imageButtonMonProfil);
        pBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });

        final ImageButton pBtnConv= (ImageButton) findViewById(R.id.imageButtonConversation);
        pBtnConv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it= new Intent(getApplicationContext(), InterfaceConversationActivity.class );
                startActivity(it);
            }
        });
    }
}