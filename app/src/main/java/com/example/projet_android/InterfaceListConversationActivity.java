package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class InterfaceListConversationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conversation);

        final Button aBtnBack = findViewById(R.id.button6);
        aBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
            }
        });

        final ImageButton aBtnProfil =findViewById(R.id.imageButtonMonProfil2);
        aBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.list_conversation);
        List<Profil> aProfil= new ArrayList<Profil>();

// ici aProfil va etre rempli avec la firebase.
        aProfil.add(new Profil("Zhang","Laurent",R.drawable.logo_complet));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),aProfil));
    }
}