package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PageMonProfilActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    FirebaseUser aUser;
    ImageButton aBtnBack,aBtnEdit;
    TextView aTextId;

    FirebaseFirestore aDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_mon_profil);

        aAuth=FirebaseAuth.getInstance();
        aUser=aAuth.getCurrentUser();
        aBtnBack = findViewById(R.id.Btnback);
        aBtnEdit=findViewById(R.id.BtnEdit);
        aTextId=findViewById(R.id.identite);
        aDatabase=FirebaseFirestore.getInstance();

        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String aPrenom = document.get("First Name").toString();
                                String aNom=document.get("Last Name").toString();
                            aTextId.setText(aPrenom+" "+aNom);

                            }
                        }
                    }
                });




        aBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
                startActivity(it);
            }
        });
        aBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}