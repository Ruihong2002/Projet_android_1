package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfilsActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    FirebaseUser aUser;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;

    TextView aTextId,aTextEmail;
    ProgressBar aProgressBar;
    ImageView aPdP;

    ImageButton aBtnEdit;
    FirebaseFirestore aDatabase;

    TextInputEditText aBio,aHobbies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profils);

        aAuth=FirebaseAuth.getInstance();
        aUser=aAuth.getCurrentUser();

        aBtnEdit=findViewById(R.id.BtnEdit);

        aTextId=findViewById(R.id.identite);
        aTextEmail=findViewById(R.id.email_edit);

        aTextEmailAff=findViewById(R.id.email_text);
        aTextClasseAff=findViewById(R.id.classe);
        aTextLoisirAff=findViewById(R.id.loisir);
        aTextClubAff=findViewById(R.id.club);
        aTextRSAff=findViewById(R.id.res_so);
        aTextBioAff=findViewById(R.id.biographie);

        aHobbies=findViewById(R.id.hobbies_edit);
        aBio=findViewById(R.id.bio_change);

        aPdP=findViewById(R.id.userpdp);

        aProgressBar=findViewById(R.id.progessBar1);

        aDatabase=FirebaseFirestore.getInstance();


        aDatabase=FirebaseFirestore.getInstance();

        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pPrenom = document.get("First Name").toString();
                                String pNom = document.get("Last Name").toString();
                                String pEmail = document.get("Email").toString();
                                aTextId.setText(pPrenom + " " + pNom);
                                aTextEmail.setText(pEmail);

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

                                aHobbies.setVisibility(View.VISIBLE);
                                aBio.setVisibility(View.VISIBLE);

                            }
                        }
                    }
                });

            aBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pHobbies,pBio,pEmail;
                pHobbies=aHobbies.getText().toString();
                pBio=aBio.getText().toString();
                pEmail=aUser.getEmail();
                aHobbies.setText("");
                aBio.setText("");
                UpdateProfil(pEmail,pHobbies,pBio);

                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
    }


    private void UpdateProfil(String pEmail, String pHobbies,String pBio){
        aDatabase=FirebaseFirestore.getInstance();
        Map<String,Object> userData=new HashMap<>();
        userData.put("Hobbies",pHobbies);
        userData.put("Bio",pBio);

        aDatabase.collection("utilisateur")
                .whereEqualTo("Email",pEmail)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String vDocID=documentSnapshot.getId();
                            DocumentReference docRef= aDatabase.collection("utilisateur").document(vDocID);
                            docRef.update(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EditProfilsActivity.this, "Successful.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfilsActivity.this, "fail.",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });

                    }
                });

    }



}