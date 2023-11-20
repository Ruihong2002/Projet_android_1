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
import android.widget.Toast;

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
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
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
        aTextBio=findViewById(R.id.bio_change);
        aTextClasse=findViewById(R.id.classe_edit);
        aTextClub=findViewById(R.id.club_edit);
        aTextLoisir=findViewById(R.id.hobbies_edit);

        aDatabase=FirebaseFirestore.getInstance();

        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pPrenom = document.get("First Name").toString();

                                String pNom=document.get("Last Name").toString();

                                String pEmail=document.get("Email").toString();

                                String pBio=document.get("Bio").toString();

                                String pHobbies=document.get("Hobbies").toString();

                                String pClass=document.get("Class").toString();

                            aTextId.setText(pPrenom+" "+pNom);
                            aTextEmail.setText(pEmail);
                            aTextClasse.setText(pClass);
                            aTextBio.setText(pBio);
                            aTextLoisir.setText(pHobbies);

                            aProgressBar.setVisibility(View.GONE);
                            aTextEmailAff.setVisibility(View.VISIBLE);
                            aTextClasseAff.setVisibility(View.VISIBLE);
                            aTextLoisirAff.setVisibility(View.VISIBLE);
                            aTextClubAff.setVisibility(View.VISIBLE);
                            aTextRSAff.setVisibility(View.VISIBLE);
                            aTextBioAff.setVisibility(View.VISIBLE);

                            aPdP.setVisibility(View.VISIBLE);
                            aTextId.setVisibility(View.VISIBLE);
                            aTextEmail.setVisibility(View.VISIBLE);
                            aTextBio.setVisibility(View.VISIBLE);
                            aTextClasse.setVisibility(View.VISIBLE);
                            aTextClub.setVisibility(View.VISIBLE);
                            aTextLoisir.setVisibility(View.VISIBLE);
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
                Intent it = new Intent(getApplicationContext(), EditProfilsActivity.class);
                startActivity(it);
            }
        });

    }
}