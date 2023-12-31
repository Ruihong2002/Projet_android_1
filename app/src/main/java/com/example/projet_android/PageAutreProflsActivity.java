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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageAutreProflsActivity extends AppCompatActivity {


    ImageButton aBtnBack;
    FirebaseAuth aAuth;
    FirebaseUser User;
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

        aAuth= FirebaseAuth.getInstance();
        User = aAuth.getCurrentUser();
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
        aProfil=new Profil(aBundle.getString("profil_nom"),aBundle.getString("profil_name"),aBundle.getString("profil_email"),"ff");

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
                                String vPdp=document.get("PdP").toString();
                                String vClub=document.get("Club").toString();
                                Map<String,Object> vSocial= (Map<String, Object>) document.get("Social Network");

                                Glide.with(PageAutreProflsActivity.this).load(vPdp).into(aPdP);

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
        aDatabase.collection("userDataConv").whereEqualTo("Email",User.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String,Object> vFavori = (HashMap<String, Object>) document.get("Favori");

                                if (vFavori.containsKey(aProfil.getEmail())) {
                                    aTextAddFavorite.setText("Favorite Added");
                                }
                                else {
                                    aTextAddFavorite.setText("Add Favorite");
                                }
                            }
                        }
                    }});
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
                Toast.makeText(PageAutreProflsActivity.this,"Chat function in developpement",Toast.LENGTH_SHORT).show();
                /*aDatabase.collection("userDataConv").whereEqualTo("Email",User.getEmail()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        HashMap<String,Object> vConv = (HashMap<String,Object>) document.get("LastConv");
                                        if (!vConv.containsKey(aProfil.getEmail())) {
                                            vConv.put(aProfil.getEmail(),aProfil.getPersonPrenom()+" "+aProfil.getPersonNom());
                                            UpdateUserData("LastConv",vConv);
                                        }
                                    }
                                }
                            }});
                Intent it = new Intent(getApplicationContext(),Chat.class);
                startActivity(it);*/
            }
        });

        aTextAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavorite();
            }
        });
    }

    private void UpdateUserData( String pDataID, Object pData) {
        aDatabase=FirebaseFirestore.getInstance();
        Map<String,Object> userConvData=new HashMap<>();
        userConvData.put(pDataID,pData);
        aDatabase.collection("userDataConv")
                .whereEqualTo("Email",User.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                        String vDocID=documentSnapshot.getId();
                        DocumentReference docRef= aDatabase.collection("userDataConv").document(vDocID);
                        docRef.update(userConvData);
                    }
                });
    }

    public void addFavorite(){
        aDatabase.collection("userDataConv").whereEqualTo("Email",User.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String,Object> vFavori = (HashMap<String,Object>) document.get("Favori");
                                if (!vFavori.containsKey(aProfil.getEmail())) {
                                    vFavori.put(aProfil.getEmail(),aProfil.getPersonPrenom()+" "+aProfil.getPersonNom());
                                    aTextAddFavorite.setText("Favorite Added");
                                    UpdateUserData("Favori",vFavori);
                                }
                                else{
                                    vFavori.remove(aProfil.getEmail());
                                    aTextAddFavorite.setText("Add Favorite");
                                    UpdateUserData("Favori",vFavori);
                                }
                            }
                        }
                    }});
    }

}