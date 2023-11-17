package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InterfacePrincipalActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    Button aLogOut;
    FirebaseUser user;
    ImageButton aBtnProfil,aBtnConv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        aLogOut = findViewById(R.id.button5);
        aBtnConv= (ImageButton) findViewById(R.id.imageButtonConversation);
        aBtnProfil = (ImageButton)findViewById(R.id.imageButtonMonProfil);
        aAuth=FirebaseAuth.getInstance();
        user = aAuth.getCurrentUser();

        if (user==null) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(it);
            finish();
        }
        aLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        aBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
        aBtnConv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it= new Intent(getApplicationContext(), InterfaceConversationActivity.class );
                startActivity(it);
            }
        });
    }
}