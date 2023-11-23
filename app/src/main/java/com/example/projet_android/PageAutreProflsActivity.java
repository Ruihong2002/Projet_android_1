package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class PageAutreProflsActivity extends AppCompatActivity {


    ImageButton aBtnBack;
    FirebaseFirestore aDatabase;
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ProgressBar aProgressBar;
    ImageView aPdP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_autre_profls);
        aBtnBack=findViewById(R.id.Btnback);

        aTextId=findViewById(R.id.identite);
        aTextEmail=findViewById(R.id.email_data);
        aTextEmailAff=findViewById(R.id.email_text);
        aTextClasseAff=findViewById(R.id.classe);
        aTextLoisirAff=findViewById(R.id.loisir);
        aTextClubAff=findViewById(R.id.club);
        aTextRSAff=findViewById(R.id.res_so);
        aTextBioAff=findViewById(R.id.biographie);
        aPdP=findViewById(R.id.otheruserpdp);
        aProgressBar=findViewById(R.id.progessBar1);
        aTextBio=findViewById(R.id.bio_data);
        aTextClasse=findViewById(R.id.classe_data);
        aTextClub=findViewById(R.id.club_data);
        aTextLoisir=findViewById(R.id.hobbies_data);

        aDatabase= FirebaseFirestore.getInstance();



        aBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
                startActivity(it);
                finish();
            }



        });

    }
}