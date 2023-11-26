package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InterfaceListConversationActivity extends AppCompatActivity {

    ImageButton aBtnProfil,aBtnRecherche,aBtnGroupe;
    Button aLogOut;
    FirebaseAuth aAuth;
    FirebaseFirestore aDatabase;
    FirebaseUser User;

    ListView aFavoriView,aConvView;
    RecyclerView aRecyclerView,aFavoriRecyclerView;
    ArrayList<Profil> aListProfil,aListFavori;
    ArrayList<String> aListProf,aListFav;
    ArrayList<String> aListProfInt,aListFavInt;
    ArrayAdapter<String> aAdapterFavori,aAdapterConv;
    Map<String,Object> aConvData,aFavoriData;
    MyAdapter adapterLastConv,adapterFdszavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conversation);

        aAuth = FirebaseAuth.getInstance();
        User = aAuth.getCurrentUser();

        aDatabase = FirebaseFirestore.getInstance();

        aLogOut = findViewById(R.id.button6);
        aBtnProfil = findViewById(R.id.imageButtonMonProfil2);
        /*aRecyclerView = findViewById(R.id.list_conversation);
        aFavoriRecyclerView = findViewById(R.id.list_favori);*/
        aBtnRecherche=findViewById(R.id.imageButtonRechercheProfil);
        aBtnGroupe=findViewById(R.id.imageButtonGroupe);

        /*aListFavori = new ArrayList<Profil>();
        aListProfil = new ArrayList<Profil>();*/

        aListProf=new ArrayList<>();
        aListFav=new ArrayList<>();
        aListProfInt=new ArrayList<>();
        aListFavInt=new ArrayList<>();

        aConvData = new HashMap<>();
        aFavoriData = new HashMap<>();

        aFavoriView= (ListView) findViewById(R.id.ListViewFavori);
        aConvView= (ListView) findViewById(R.id.ListViewLastConv);
        setListView();
        /*aRecyclerView.setHasFixedSize(true);
        aFavoriRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aFavoriRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/


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
                                aConvData = (HashMap<String,Object>) document.get("LastConv");
                                aFavoriData = (HashMap<String,Object>) document.get("Favori");
                                if (!aConvData.isEmpty()) {
                                    Set<String> ensembleDesClés = aConvData.keySet();
                                    for (Object email : ensembleDesClés) {
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email.toString()).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                aListProf.add(vName+" "+vSurname);
                                                                aListProfInt.add(vEmail);
                                                                /*String vPdp = document.get("PdP").toString();
                                                                aListProfil.add(new Profil(vSurname, vName, vEmail, vPdp));*/
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }

                                if (!aFavoriData.isEmpty()) {
                                    Set<String> ensembleDesClés = aFavoriData.keySet();
                                    for (Object email : ensembleDesClés) {
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                aListFav.add(vName+" "+vSurname);
                                                                aListFavInt.add(vEmail);
                                                                /*String vPdp = document.get("PdP").toString();
                                                                //Profil pProfil = new Profil(vSurname, vName, vEmail, vPdp);
                                                                //aListFavori.add(pProfil);*/

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

        /*adapterLastConv=new MyAdapter(this,aListProfil);
        adapterFavorite=new MyAdapter(this,aListFavori);
        aRecyclerView.setAdapter(adapterLastConv);
        aFavoriRecyclerView.setAdapter(adapterFavorite);*/
    }

    private void setListView() {
        aDatabase.collection("userDataConv").whereEqualTo("Email", User.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                aConvData = (HashMap<String,Object>) document.get("LastConv");
                                aFavoriData = (HashMap<String,Object>) document.get("Favori");

                                if (!aConvData.isEmpty()) {
                                    Set<String> ensembleDesClés = aConvData.keySet();
                                    for (Object email : ensembleDesClés) {
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email.toString()).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                aListProf.add(vName+" "+vSurname);
                                                                aListProfInt.add(vEmail);
                                                                aAdapterConv= new ArrayAdapter<String>(InterfaceListConversationActivity.this,android.R.layout.simple_list_item_1,aListProf);
                                                                aConvView.setAdapter(aAdapterConv);
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }

                                if (!aFavoriData.isEmpty()) {
                                    Set<String> ensembleDesClés = aFavoriData.keySet();
                                    for (Object email : ensembleDesClés) {
                                        aDatabase.collection("utilisateur").whereEqualTo("Email", email).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                aListFav.add(vName+" "+vSurname);
                                                                aListFavInt.add(vEmail);
                                                                aAdapterFavori=new ArrayAdapter<String>(InterfaceListConversationActivity.this,android.R.layout.simple_list_item_1,aListFav );
                                                                aFavoriView.setAdapter(aAdapterFavori);
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


    }

    /*@Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getApplicationContext(), PageAutreProflsActivity.class);
        Bundle vAffProfil=new Bundle();
        vAffProfil.putString("profil_email",aListProfil.get(position).getEmail());
        vAffProfil.putString("profil_name",aListProfil.get(position).getPersonPrenom());
        vAffProfil.putString("profil_nom",aListProfil.get(position).getPersonNom());
        intent.putExtras(vAffProfil);
        startActivity(intent);
    }*/

}