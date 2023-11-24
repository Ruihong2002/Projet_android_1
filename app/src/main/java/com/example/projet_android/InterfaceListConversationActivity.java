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

    ImageButton aBtnProfil;
    Button aBtnBack;
    RecyclerView aRecyclerView;
    List<Profil> aListProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conversation);

        aBtnBack = findViewById(R.id.button6);
        aBtnProfil =findViewById(R.id.imageButtonMonProfil2);
        aRecyclerView=findViewById(R.id.list_conversation);

        aListProfil= new ArrayList<Profil>();

        aBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
            }
        });

        aBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });


// ici aProfil va etre rempli avec la firebase.
        aListProfil.add(new Profil("Zhang","Laurent","email",R.drawable.logo_complet));
        aListProfil.add(new Profil("cadz","cdzq","email",R.drawable.logo_complet));

        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aRecyclerView.setAdapter(new MyAdapter(getApplicationContext(),aListProfil));
    }
}