package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.Luminance;

import java.util.ArrayList;
import java.util.List;

public class InterfaceListConversationActivity extends AppCompatActivity  implements  RecyclerViewInterface{

    ImageButton aBtnProfil,aBtnRecherche,aBtnGroupe;
    Button aLogOut;
    FirebaseAuth aAuth;
    FirebaseFirestore aDatabase;
    FirebaseUser User;
    RecyclerView aRecyclerView,aFavoriRecyclerView;
    ArrayList<Profil> aListProfil,aListFavori;
    ArrayList<Object> aConvData,aFavoriData;
    MyAdapter adapterLastConv,adapterFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conversation);

        aLogOut = findViewById(R.id.button6);
        aBtnProfil = findViewById(R.id.imageButtonMonProfil2);
        aRecyclerView = findViewById(R.id.list_conversation);
        aFavoriRecyclerView = findViewById(R.id.list_favori);
        aBtnRecherche=findViewById(R.id.imageButtonRechercheProfil);
        aBtnGroupe=findViewById(R.id.imageButtonGroupe);


        aListFavori = new ArrayList<Profil>();
        aListProfil = new ArrayList<Profil>();

        aConvData = new ArrayList<>();
        aFavoriData = new ArrayList<>();


        aRecyclerView.setHasFixedSize(true);
        aFavoriRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aFavoriRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aListFavori = new ArrayList<Profil>();
        aListProfil = new ArrayList<Profil>();

        aAuth = FirebaseAuth.getInstance();
        User = aAuth.getCurrentUser();

        aDatabase = FirebaseFirestore.getInstance();

        aDatabase.collection("utilisateur").whereEqualTo("Email", User.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String vPdp = document.get("PdP").toString();
                                Glide.with(InterfaceListConversationActivity.this).load(vPdp).into(aBtnProfil);
                            }
                        }
                    }
                });

        aDatabase.collection("userDataConv").whereEqualTo("Email", User.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                aConvData = (ArrayList<Object>) document.get("LastConv");
                                aFavoriData = (ArrayList<Object>) document.get("Favori");
                                if (!aConvData.isEmpty()) {
                                    for (Object email : aConvData) {
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email.toString()).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                String vPdp = document.get("PdP").toString();
                                                                aListProfil.add(new Profil(vSurname, vName, vEmail, vPdp));
                                                                Tasks.whenAllSuccess(task).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<List<Object>> task) {
                                                                        adapterLastConv.notifyDataSetChanged();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }

                                if (!aFavoriData.isEmpty()) {
                                    for (Object email : aFavoriData) {
                                        Toast.makeText(InterfaceListConversationActivity.this, " la dans favo:" + email, Toast.LENGTH_SHORT).show();
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(InterfaceListConversationActivity.this, " la favo:", Toast.LENGTH_SHORT).show();
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                Toast.makeText(InterfaceListConversationActivity.this, " la d:" + document, Toast.LENGTH_SHORT).show();
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                String vPdp = document.get("PdP").toString();
                                                                Profil pProfil = new Profil(vSurname, vName, vEmail, vPdp);
                                                                aListFavori.add(pProfil);
                                                                Log.d("desde",aListFavori.toString());
                                                                Tasks.whenAllSuccess(task).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<List<Object>> task) {
                                                                        adapterFavorite.notifyDataSetChanged();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });
                                    }

                                }
                            }
                        }
                    }
                });
        aLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
            }
        });
        aBtnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
                startActivity(it);
            }
        });
        aBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });

        adapterLastConv=new MyAdapter(this,aListProfil);
        adapterFavorite=new MyAdapter(this,aListFavori);
        aRecyclerView.setAdapter(adapterLastConv);
        aFavoriRecyclerView.setAdapter(adapterFavorite);
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