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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PageAutreProflsActivity extends AppCompatActivity {


    ImageButton aBtnBack;
    FirebaseFirestore aDatabase;
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir,aTextSendMessage,aTextAddFavorite;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ImageView aPdP;
    Profil aProfil;
    Bundle aBundle;
    TextView aTextGit,aTextSnap,aTextDiscord,aTextInsta,aTextWhatsapp,aTextLinkedIn;
    ImageView aImgGit,aImgSnap,aImgDiscord,aImgInsta,aImgWhatsapp,aImgLinkedIn;

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

        aTextBio=findViewById(R.id.bio_data);
        aTextClasse=findViewById(R.id.classe_data);
        aTextClub=findViewById(R.id.club_data);
        aTextLoisir=findViewById(R.id.hobbies_data);
        aTextSendMessage=findViewById(R.id.send_msg);
        aTextAddFavorite=findViewById(R.id.add_favorite);


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


        aBundle=getIntent().getExtras();
        aProfil=new Profil(aBundle.getString("profil_nom"),aBundle.getString("profil_name"),aBundle.getString("profil_email"),R.drawable.avatar_base);

        aDatabase= FirebaseFirestore.getInstance();
        aDatabase.collection("utilisateur").whereEqualTo("Email",aProfil.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PageAutreProflsActivity.this,"succes",Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String vPrenom = document.get("First Name").toString();
                                String vNom=document.get("Last Name").toString();
                                String vEmail=document.get("Email").toString();
                                String vBio=document.get("Bio").toString();
                                String vHobbies=document.get("Hobbies").toString();
                                String vClass=document.get("Class").toString();
                               // String vPdp=document.get("PdP").toString();
                                String vClub=document.get("Club").toString();
                                Map<String,Object> vSocial= (Map<String, Object>) document.get("Social Network");

                                if (!vSocial.get("Snapchat").equals("snapchat")){
                                    aTextSnap.setText(vSocial.get("Snapchat").toString());
                                    aTextSnap.setVisibility(View.VISIBLE);
                                    aImgSnap.setVisibility(View.VISIBLE);
                                }
                                if (!vSocial.get("GitHub").equals("github")){
                                    aTextGit.setText(vSocial.get("GitHub").toString());
                                    aTextGit.setVisibility(View.VISIBLE);
                                    aImgGit.setVisibility(View.VISIBLE);
                                }
                                if (!vSocial.get("Discord").equals("discord")){
                                    aTextDiscord.setText(vSocial.get("Discord").toString());
                                    aTextDiscord.setVisibility(View.VISIBLE);
                                    aImgDiscord.setVisibility(View.VISIBLE);
                                }
                                if (!vSocial.get("Instagramm").equals("instagram")){
                                    aTextInsta.setText(vSocial.get("Instagramm").toString());
                                    aTextInsta.setVisibility(View.VISIBLE);
                                    aImgInsta.setVisibility(View.VISIBLE);
                                }
                                if (!vSocial.get("LinkedIn").equals("linkedin")){
                                    aTextLinkedIn.setText(vSocial.get("LinkedIn").toString());
                                    aTextLinkedIn.setVisibility(View.VISIBLE);
                                    aImgLinkedIn.setVisibility(View.VISIBLE);
                                }
                                if (!vSocial.get("Whatsapp").equals("whatsapp")){
                                    aTextWhatsapp.setText(vSocial.get("Whatsapp").toString());
                                    aTextWhatsapp.setVisibility(View.VISIBLE);
                                    aImgWhatsapp.setVisibility(View.VISIBLE);
                                }

                                aTextId.setText(vPrenom + " " + vNom);
                                aTextEmail.setText(vEmail);
                                aTextClasse.setText(vClass);
                                aTextBio.setText(vBio);
                                aTextLoisir.setText(vHobbies);
                                aTextClub.setText(vClub);


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
                Bundle vFavori=new Bundle();
                vFavori.putString("Favori_email",aProfil.getEmail());
                vFavori.putString("Favori_Prenom",aProfil.getPersonPrenom());
                vFavori.putString("Favori_Nom",aProfil.getPersonNom());
                intentFavori.putExtras(vFavori);
                aTextAddFavorite.setText("Favorite Added");
            }
        });
    }
}