package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    TextView aTextId,aTextEmail,aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ProgressBar aProgressBar;
    ImageView aPdP;

    FirebaseFirestore aDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_mon_profil);

        aAuth=FirebaseAuth.getInstance();
        aUser=aAuth.getCurrentUser();
        aBtnBack = findViewById(R.id.Btnback);
        aBtnEdit=findViewById(R.id.BtnEdit);
        aTextId=findViewById(R.id.identite);
        aTextEmail=findViewById(R.id.email_edit);
        aTextEmailAff=findViewById(R.id.email_text);
        aTextClasseAff=findViewById(R.id.classe);
        aTextLoisirAff=findViewById(R.id.loisir);
        aTextClubAff=findViewById(R.id.club);
        aTextRSAff=findViewById(R.id.res_so);
        aTextBioAff=findViewById(R.id.biographie);
        aPdP=findViewById(R.id.userpdp);
        aProgressBar=findViewById(R.id.progessBar1);

        aDatabase=FirebaseFirestore.getInstance();


        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String aPrenom = document.get("First Name").toString();
                                String aNom=document.get("Last Name").toString();
                                String aEmail=document.get("Email").toString();
                            aTextId.setText(aPrenom+" "+aNom);
                            aTextEmail.setText(aEmail);


                            aProgressBar.setVisibility(View.GONE);
                            aTextId.setVisibility(View.VISIBLE);
                            aTextEmail.setVisibility(View.VISIBLE);
                            aTextEmailAff.setVisibility(View.VISIBLE);
                            aTextClasseAff.setVisibility(View.VISIBLE);
                            aTextLoisirAff.setVisibility(View.VISIBLE);
                            aTextClubAff.setVisibility(View.VISIBLE);
                            aTextRSAff.setVisibility(View.VISIBLE);
                            aTextBioAff.setVisibility(View.VISIBLE);
                            aPdP.setVisibility(View.VISIBLE);


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