package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.projet_android.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;


public class PageMonProfilActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    FirebaseUser aUser;
    ImageButton aBtnBack,aBtnEdit;
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ImageView aPdP;
    FirebaseFirestore aDatabase;
    StorageReference aStorageRef,aPdPRef;

    TextView aTextGit,aTextSnap,aTextDiscord,aTextInsta,aTextWhatsapp,aTextLinkedIn;
    ImageView aImgGit,aImgSnap,aImgDiscord,aImgInsta,aImgWhatsapp,aImgLinkedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_mon_profil);

        aAuth=FirebaseAuth.getInstance();
        aUser=aAuth.getCurrentUser();

        aBtnBack=findViewById(R.id.Btnback);
        aBtnEdit=findViewById(R.id.BtnEdit);
        aTextId=findViewById(R.id.identite);
        aTextEmail=findViewById(R.id.email_edit);
        aTextEmailAff=findViewById(R.id.email_text);
        aTextClasseAff=findViewById(R.id.classe);
        aTextLoisirAff=findViewById(R.id.loisir);
        aTextClubAff=findViewById(R.id.club);
        aTextRSAff=findViewById(R.id.res_so);
        aTextBioAff=findViewById(R.id.biographie);
        aPdP=(ImageView) findViewById(R.id.userpdp);

        aTextBio=findViewById(R.id.bio_change);
        aTextClasse=findViewById(R.id.classe_edit);
        aTextClub=findViewById(R.id.club_edit);
        aTextLoisir=findViewById(R.id.hobbies_edit);

        aTextGit=findViewById(R.id.userGitHub);
        aTextSnap=findViewById(R.id.userSnapChat);
        aTextDiscord=findViewById(R.id.userDiscord);
        aTextInsta=findViewById(R.id.userInstagramm);
        aTextWhatsapp=findViewById(R.id.userWhatsapp);
        aTextLinkedIn=findViewById(R.id.userLinkedIn);

        aImgGit=findViewById(R.id.imgGithub);
        aImgSnap=findViewById(R.id.imgSnap);
        aImgDiscord=findViewById(R.id.imgDiscord);
        aImgInsta=findViewById(R.id.imgInsta);
        aImgWhatsapp=findViewById(R.id.imgWhatsapp);
        aImgLinkedIn=findViewById(R.id.imgLinkedIn);


        aStorageRef = FirebaseStorage.getInstance().getReference();

        aDatabase=FirebaseFirestore.getInstance();

        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String vPrenom = document.get("First Name").toString();
                                String vNom=document.get("Last Name").toString();
                                String vEmail=document.get("Email").toString();
                                String vBio=document.get("Bio").toString();
                                String vHobbies=document.get("Hobbies").toString();
                                String vClass=document.get("Class").toString();
                                String vPdp=document.get("PdP").toString();
                                String vClub=document.get("Club").toString();
                                Map<String,Object> vSocial= (Map<String, Object>) document.get("Social Network");

                                Glide.with(PageMonProfilActivity.this).load(vPdp).into(aPdP);

                            aTextId.setText(vPrenom+" "+vNom);
                            aTextEmail.setText(vEmail);
                            aTextClasse.setText(vClass);
                            aTextBio.setText(vBio);
                            aTextLoisir.setText(vHobbies);
                            aTextClub.setText(vClub);

                            if (vSocial.get("Snapchat").equals("snapchat")==false){
                                aTextSnap.setText(vSocial.get("Snapchat").toString());
                                aTextSnap.setVisibility(View.VISIBLE);
                                aImgSnap.setVisibility(View.VISIBLE);
                            }
                            if (vSocial.get("GitHub").equals("github")==false){
                                aTextGit.setText(vSocial.get("GitHub").toString());
                                aTextGit.setVisibility(View.VISIBLE);
                                aImgGit.setVisibility(View.VISIBLE);
                            }
                            if (vSocial.get("Discord").equals("discord")==false){
                                aTextDiscord.setText(vSocial.get("Discord").toString());
                                aTextDiscord.setVisibility(View.VISIBLE);
                                aImgDiscord.setVisibility(View.VISIBLE);
                            }
                            if (vSocial.get("Instagramm").equals("instagram")==false){
                                aTextInsta.setText(vSocial.get("Instagramm").toString());
                                aTextInsta.setVisibility(View.VISIBLE);
                                aImgInsta.setVisibility(View.VISIBLE);
                            }
                            if (vSocial.get("LinkedIn").equals("linkedin")==false){
                                aTextLinkedIn.setText(vSocial.get("LinkedIn").toString());
                                aTextLinkedIn.setVisibility(View.VISIBLE);
                                aImgLinkedIn.setVisibility(View.VISIBLE);
                            }
                            if (vSocial.get("Whatsapp").equals("whatsapp")==false){
                                aTextWhatsapp.setText(vSocial.get("Whatsapp").toString());
                                aTextWhatsapp.setVisibility(View.VISIBLE);
                                aImgWhatsapp.setVisibility(View.VISIBLE);
                            }

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