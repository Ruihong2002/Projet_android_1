package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.List;

public class InterfacePrincipalActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    Button aLogOut,aOtherProfile;
    FirebaseUser user;
    FirebaseFirestore aDatabase;
    ImageButton aBtnProfil,aBtnConv;
    RecyclerView aRecyclerView;
    ArrayList<Profil> aListProfil;
    DatabaseReference aDatabaseRef= FirebaseDatabase.getInstance().getReference("image");
    StorageReference aStorageRef= FirebaseStorage.getInstance().getReference("image");

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        aLogOut = findViewById(R.id.button5);
        aBtnConv= (ImageButton) findViewById(R.id.imageButtonConversation);
        aBtnProfil = (ImageButton)findViewById(R.id.imageButtonMonProfil);

        aRecyclerView=findViewById(R.id.list_profil);

        aOtherProfile=findViewById(R.id.btnOther);
        aAuth=FirebaseAuth.getInstance();
        user = aAuth.getCurrentUser();


        aDatabase= FirebaseFirestore.getInstance();


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
        });

        if (user==null) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(it);
            finish();
        }

        aDatabase.collection("utilisateur").whereEqualTo("Email",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String vPdp = document.get("PdP").toString();
                        Glide.with(InterfacePrincipalActivity.this).load(vPdp).into(aBtnProfil);
                    }
                }
            }});

        Profil pProfil=new Profil("Hzudb","Veudbd","laurent2002laurent@hotmail.com","https://firebasestorage.googleapis.com/v0/b/projet-orion-cb071.appspot.com/o/image%2F1700962824610.jpg?alt=media&token=c1b435b8-48a9-470f-93f4-dc083695f55a");
        aListProfil.add(pProfil);
        Toast.makeText(this, "1"+aListProfil.get(0).getEmail(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "2"+aListProfil.get(0).getPersonPrenom(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "3"+aListProfil.get(0).getPersonNom(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "4"+aListProfil.get(0).getPdP(), Toast.LENGTH_SHORT).show();
        aOtherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), PageAutreProflsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("profil_nom",pProfil.getPersonNom());
                bundle.putString("profil_name",pProfil.getPersonPrenom());
                bundle.putString("profil_email",pProfil.getEmail());
                it.putExtras(bundle);
                startActivity(it);
                finish();
            }
        });
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

        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,aListProfil);

        aRecyclerView.setAdapter(adapter);
        Toast.makeText(this, "adapter"+adapter, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "aRecyclerView"+aRecyclerView, Toast.LENGTH_SHORT).show();
    }
}