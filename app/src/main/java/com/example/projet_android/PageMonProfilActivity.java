package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageMonProfilActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    FirebaseUser aUser;
    ImageButton aBtnBack,aBtnEdit;
    TextView aTextId,aTextEmail,aTextBio,aTextClasse,aTextClub,aTextLoisir;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    ImageView aPdP;
    FirebaseFirestore aDatabase;
    StorageReference aStorageRef,aPdPRef;

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

        aStorageRef = FirebaseStorage.getInstance().getReference();
        aPdPRef = aStorageRef.child("avatar_base.png");


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

                                StorageReference imageRef = aStorageRef.child(vPdp);

                                ViewTarget<ImageView, Drawable> aTest = Glide.with(PageMonProfilActivity.this).load(imageRef).into(aPdP);
                                Log.d("Glide:",aTest.toString());
                                imageRef.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                  @Override
                                                                  public void onSuccess(Uri uri) {
                                                                      aPdP.setImageURI(uri);
                                                                      Log.d("uri:",uri.toString());
                                                                  }
                                                });

                            aTextId.setText(vPrenom+" "+vNom);
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