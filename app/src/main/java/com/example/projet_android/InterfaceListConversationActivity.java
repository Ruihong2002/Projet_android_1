package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InterfaceListConversationActivity extends AppCompatActivity  implements  RecyclerViewInterface{

    ImageButton aBtnProfil;
    Button aBtnBack;
    RecyclerView aRecyclerView,aFavoriRecyclerView;
    List<Profil> aListProfil,aListFavori;
    Bundle aBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conversation);

        aBtnBack = findViewById(R.id.button6);
        aBtnProfil =findViewById(R.id.imageButtonMonProfil2);
        aRecyclerView=findViewById(R.id.list_conversation);
        aBundle=getIntent().getExtras();
        aFavoriRecyclerView=findViewById(R.id.list_favori);
        aListFavori=new ArrayList<Profil>();
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
        aListProfil.add(new Profil("cadz","cdzq","email",R.drawable.logo_complet));
        aListFavori.add(new Profil("Tran","Patrick","patrick.tran@edu.esiee.fr",R.drawable.logo_complet));

        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aRecyclerView.setAdapter(new MyAdapter(getApplicationContext(),aListProfil,this));

    }


    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getApplicationContext(), PageAutreProflsActivity.class);
        Bundle vAffProfil=new Bundle();
        vAffProfil.putString("profil_email",aListProfil.get(position).getEmail());
        vAffProfil.putString("profil_name",aListProfil.get(position).getPersonPrenom());
        vAffProfil.putString("profil_nom",aListProfil.get(position).getPersonNom());
        intent.putExtras(vAffProfil);
        startActivity(intent);
    }
}