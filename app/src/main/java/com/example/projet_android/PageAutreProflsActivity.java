package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PageAutreProflsActivity extends AppCompatActivity {


    ImageButton aBtnBack;
    FirebaseFirestore aDatabase;
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir,aTextSendMessage,aTextAddFavorite;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ProgressBar aProgressBar;
    ImageView aPdP;
    Profil aProfil;
    Bundle aBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_autre_profls);
        aBtnBack=findViewById(R.id.Btnback);

        aTextId=findViewById(R.id.identite_other);
        aTextEmail=findViewById(R.id.email_data);
        aTextEmailAff=findViewById(R.id.email_text_other);
        aTextClasseAff=findViewById(R.id.classe_other);
        aTextLoisirAff=findViewById(R.id.loisir_other);
        aTextClubAff=findViewById(R.id.club_other);
        aTextRSAff=findViewById(R.id.res_so_other);
        aTextBioAff=findViewById(R.id.biographie_other);
        aPdP=findViewById(R.id.otheruserpdp);
        aProgressBar=findViewById(R.id.progessBar1);
        aTextBio=findViewById(R.id.bio_data);
        aTextClasse=findViewById(R.id.classe_data);
        aTextClub=findViewById(R.id.club_data);
        aTextLoisir=findViewById(R.id.hobbies_data);
        aTextSendMessage=findViewById(R.id.send_msg);
        aTextAddFavorite=findViewById(R.id.add_favorite);

        aBundle=getIntent().getExtras();
        aProfil=new Profil(aBundle.getString("profil_nom"),aBundle.getString("profil_name"),aBundle.getString("profil_email"),R.drawable.avatar_base);

        aDatabase= FirebaseFirestore.getInstance();
        aDatabase.collection("utilisateur").whereEqualTo("Email",aProfil.getaEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Toast.makeText(PageAutreProflsActivity.this,"Nom="+aProfil.getPersonNom(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(PageAutreProflsActivity.this,"Prenom="+aProfil.getPersonPrenom(),Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()) {
                            Toast.makeText(PageAutreProflsActivity.this,"succes",Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(PageAutreProflsActivity.this,"test"+document.toString(),Toast.LENGTH_SHORT).show();
                                String vPrenom = document.get("First Name").toString();
                                String vNom = document.get("Last Name").toString();
                                String vEmail = document.get("Email").toString();
                                String vBio = document.get("Bio").toString();
                                String vHobbies = document.get("Hobbies").toString();
                                String vClass = document.get("Class").toString();

                                aTextId.setText(vPrenom + " " + vNom);
                                aTextEmail.setText(vEmail);
                                aTextClasse.setText(vClass);
                                aTextBio.setText(vBio);
                                aTextLoisir.setText(vHobbies);}

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
                });



        aBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
                startActivity(it);
                finish();
            }



        });

        aTextSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        aTextAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFavori=new Intent(getApplicationContext(), InterfaceListConversationActivity.class);
                intentFavori.putExtra("Favori_email",aProfil.getaEmail());
            }
        });
    }
}