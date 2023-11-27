package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InterfacePrincipalActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    Button aLogOut,aOtherProfile;
    FirebaseUser user;
    FirebaseFirestore aDatabase;
    ImageButton aBtnProfil,aBtnConv;
    RecyclerView aRecyclerView;
    ListView aListeV;
    ArrayList<Profil> aListProfil;
    ArrayAdapter<String> listViewAdapter;

    Map<String,Object> aProfilData;
    ArrayList<String> aListProfilLV,aListProfilNom;
    ArrayList<String> aListProfilInt,aListProfilPrenom;
    ArrayAdapter<String> aAdapterListe;

    ArrayList<String> aListe;
    Map<String,String> aHashmap;
    DatabaseReference aDatabaseRef= FirebaseDatabase.getInstance().getReference("image");
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        aLogOut = findViewById(R.id.button5);
        aBtnConv= (ImageButton) findViewById(R.id.imageButtonConversation);
        aBtnProfil = (ImageButton)findViewById(R.id.imageButtonMonProfil);
        aListeV=(ListView) findViewById(R.id.ListView);
        aAuth=FirebaseAuth.getInstance();
        user = aAuth.getCurrentUser();


        aDatabase= FirebaseFirestore.getInstance();

        aHashmap=new HashMap<>();
        aListe=new ArrayList<>();

        aListProfilLV=new ArrayList<>();
        aListProfilInt=new ArrayList<>();
        aListProfilPrenom=new ArrayList<>();
        aListProfilNom=new ArrayList<>();

        setListView();



        /* aRecyclerView=findViewById(R.id.list_profil);
        aRecyclerView.setHasFixedSize(true);
        aListProfil= new ArrayList<Profil>();
        aDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Profil vProfil=dataSnapshot.getValue(Profil.class);
                    aListProfil.add(vProfil);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

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
                Intent it= new Intent(getApplicationContext(), InterfaceListConversationActivity.class );
                startActivity(it);
            }
        });

        aDatabase.collection("utilisateur").whereEqualTo("Email",user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String vPdp = document.get("PdP").toString();
                        Glide.with(InterfacePrincipalActivity.this).load(vPdp).into(aBtnProfil);
                    }
                }
            }});
       /* aListProfil.add(pProfil);

        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         adapter=new MyAdapter(this,aListProfil);
         aRecyclerView.setAdapter(adapter);*/
        aListProfilLV.add("Laurent Zhang");
        aListProfilNom.add("Zhang");
        aListProfilPrenom.add("Laurent");
        aListProfilInt.add("laurent.zhang@edu.esiee.fr");
        listViewAdapter=new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, aListProfilLV);
        aListeV.setAdapter(listViewAdapter);

        aListeV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it=new Intent(getApplicationContext(), PageAutreProflsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("profil_nom",aListProfilNom.get(i));
                bundle.putString("profil_name",aListProfilPrenom.get(i));
                bundle.putString("profil_email",aListProfilInt.get(i));
                it.putExtras(bundle);
                startActivity(it);
                finish();
            }
        });
    }

    private void setListView() {
        aDatabase.collection("userDataConv").whereEqualTo("Email", user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                aProfilData = (HashMap<String,Object>) document.get("LastConv");

                                if (!aProfilData.isEmpty()) {
                                    Set<String> ensembleDesClés = aProfilData.keySet();
                                    for (Object email : ensembleDesClés) {
                                        aDatabase.collection("utilisateur").get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String vName = document.getString("First Name").toString();
                                                                String vSurname = document.getString("Last Name").toString();
                                                                String vEmail = document.getString("Email").toString();
                                                                aListProfilLV.add(vName+" "+vSurname);
                                                                aListProfilInt.add(vEmail);
                                                                aListProfilNom.add(vSurname);
                                                                aListProfilPrenom.add(vName);
                                                            }
                                                            listViewAdapter= new ArrayAdapter<String>(InterfacePrincipalActivity.this,android.R.layout.simple_list_item_1,aListProfilLV);
                                                            aListeV.setAdapter(listViewAdapter);
                                                        }
                                                    }
                                                });
                                    }
                                }}}}});
    }
}