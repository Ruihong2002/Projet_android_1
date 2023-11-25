package com.example.projet_android;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;


public class EditProfilsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    FirebaseAuth aAuth;
    FirebaseUser aUser;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;
    TextView aTextId,aTextEmail;
    ImageButton aBtnEdit;
    ImageView aPdP;
    FirebaseFirestore aDatabase;

    TextInputEditText aBio,aHobbies,aTextClub;
    Spinner aClasse;
    Uri aImagePdP;
    FirebaseStorage aStorage;
    StorageReference aStorageRef;
    DatabaseReference aDbRef;

    TextInputEditText aTextGit,aTextSnap,aTextDiscord,aTextInsta,aTextWhatsapp,aTextLinkedIn;
    Map<String,Object> aSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_profils);

        aAuth = FirebaseAuth.getInstance();
        aUser = aAuth.getCurrentUser();

        aSocial=new HashMap<String,Object>();

        aBtnEdit = findViewById(R.id.BtnEdit);


        aTextId = findViewById(R.id.identite);
        aTextEmail = findViewById(R.id.email_edit);


        aTextEmailAff = findViewById(R.id.email_text);
        aTextClasseAff = findViewById(R.id.classe);
        aTextLoisirAff = findViewById(R.id.loisir);
        aTextClubAff = findViewById(R.id.club);
        aTextRSAff = findViewById(R.id.res_so);
        aTextBioAff = findViewById(R.id.biographie);


        aHobbies = findViewById(R.id.hobbies_edit);
        aBio = findViewById(R.id.bio_change);
        aPdP = findViewById(R.id.userpdp);
        aClasse = findViewById(R.id.class_select);
        aTextClub = findViewById(R.id.club_edit);

        aTextGit=findViewById(R.id.userGitHub);
        aTextSnap=findViewById(R.id.userSnapChat);
        aTextDiscord=findViewById(R.id.userDiscord);
        aTextInsta=findViewById(R.id.userInstagramm);
        aTextWhatsapp=findViewById(R.id.userWhatsapp);
        aTextLinkedIn=findViewById(R.id.userLinkedIn);

        aDatabase = FirebaseFirestore.getInstance();
        aDbRef = FirebaseDatabase.getInstance().getReference().child("image/");
        aStorage = FirebaseStorage.getInstance();

        aStorageRef = FirebaseStorage.getInstance().getReference();


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.ListeClasse,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aClasse.setAdapter(adapter);



        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data= result.getData();
                    aImagePdP=data.getData();
                    aPdP.setImageURI(aImagePdP);
                }
            }
        });
        aPdP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vPhoto=new Intent();
                vPhoto.setAction(Intent.ACTION_GET_CONTENT);
                vPhoto.setType("image/*");
                activityResultLauncher.launch(vPhoto);
            }
        });

        aDatabase.collection("utilisateur").whereEqualTo("Email",aUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pPrenom = document.get("First Name").toString();
                                String pNom = document.get("Last Name").toString();
                                String pEmail = document.get("Email").toString();
                                String pHobbies=document.get("Hobbies").toString();
                                String pBio=document.get("Bio").toString();
                                String vClub=document.get("Club").toString();
                                aSocial= (Map<String, Object>) document.get("Social Network");

                                if (aSocial.get("Snapchat").equals("snapchat")==false){
                                    aTextSnap.setText(aSocial.get("Snapchat").toString());
                                }
                                if (aSocial.get("GitHub").equals("github")==false){
                                    aTextGit.setText(aSocial.get("GitHub").toString());

                                }
                                if (aSocial.get("Discord").equals("discord")==false){
                                    aTextDiscord.setText(aSocial.get("Discord").toString());
                                }
                                if (aSocial.get("Instagramm").equals("instagram")==false){
                                    aTextInsta.setText(aSocial.get("Instagramm").toString());
                                }
                                if (aSocial.get("LinkedIn").equals("linkedin")==false){
                                    aTextLinkedIn.setText(aSocial.get("LinkedIn").toString());
                                }
                                if (aSocial.get("Whatsapp").equals("whatsapp")==false){
                                    aTextWhatsapp.setText(aSocial.get("Whatsapp").toString());
                                }
                                aTextId.setText(pPrenom + " " + pNom);
                                aTextEmail.setText(pEmail);
                                aHobbies.setText(pHobbies);
                                aBio.setText(pBio);
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

                                aHobbies.setVisibility(View.VISIBLE);
                                aBio.setVisibility(View.VISIBLE);
                                aClasse.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

            aBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vHobbies,vBio,vEmail,vDiscord,vSnap,vInsta,vGit,vLinked,vWhatsapp;
                vHobbies=aHobbies.getText().toString();
                vBio=aBio.getText().toString();
                vEmail=aUser.getEmail();
                vDiscord=aTextDiscord.getText().toString();
                vSnap=aTextSnap.getText().toString();
                vInsta=aTextInsta.getText().toString();
                vGit =aTextGit.getText().toString();
                vLinked=aTextLinkedIn.getText().toString();
                vWhatsapp=aTextWhatsapp.getText().toString();
                aClasse.setOnItemSelectedListener(EditProfilsActivity.this);
                if (TextUtils.isEmpty(vDiscord)==false) {
                    aSocial.replace("Discord",vDiscord);
                }
                else{
                    aSocial.replace("Discord","discord");
                }
                if (TextUtils.isEmpty(vSnap)==false) {
                    aSocial.replace("Snapchat",vSnap);
                }
                else{
                    aSocial.replace("Snapchat","snapchat");
                }
                if (TextUtils.isEmpty(vInsta)==false) {
                    aSocial.replace("Instagramm",vInsta);
                }
                else{
                    aSocial.replace("Instagramm","instagram");
                }
                if (TextUtils.isEmpty(vGit)==false) {
                    aSocial.replace("GitHub",vGit);
                }
                else{
                    aSocial.replace("GitHub","github");
                }
                if (TextUtils.isEmpty(vLinked)==false) {
                    aSocial.replace("LinkedIn",vLinked);
                }
                else{
                    aSocial.replace("LinkedIn","linkedin");
                }
                if (TextUtils.isEmpty(vWhatsapp)==false) {
                    aSocial.replace("Whatsapp",vWhatsapp);
                }
                else{
                    aSocial.replace("Whatsapp","whatsapp");
                }
                UpdateProfil(vEmail,"Bio",vBio);
                UpdateProfil(vEmail,"Hobbies",vHobbies);
                UpdateProfil(vEmail,"Social Network",aSocial);

                    if (aImagePdP!=null) {
                    uploadIntoStorage(aImagePdP);
                }
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
    }
    private void uploadIntoStorage(Uri pUri) {
        String vEmail=aUser.getEmail();
        Toast.makeText(EditProfilsActivity.this,"uploading",Toast.LENGTH_SHORT).show();
             StorageReference vImageRef=aStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(pUri));
             vImageRef.putFile(pUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     vImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             Toast.makeText(EditProfilsActivity.this,"upload Succes",Toast.LENGTH_SHORT).show();
                             Model vModel=new Model(uri.toString());
                             String vModelID=aDbRef.push().getKey();
                             aDbRef.child(vModelID).setValue(vModel);
                             UpdateProfil(vEmail,"PdP",vImageRef.getDownloadUrl().toString());
                         }
                     });
                 }
             }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                     Toast.makeText(EditProfilsActivity.this,"upload process",Toast.LENGTH_SHORT).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(EditProfilsActivity.this,"upload failed",Toast.LENGTH_SHORT).show();
                 }
             });

    }

    private String getFileExtention(Uri pUri) {
        ContentResolver vContentResolver=getContentResolver();
        MimeTypeMap vMTM=MimeTypeMap.getSingleton();
        return vMTM.getExtensionFromMimeType(vContentResolver.getType(pUri));
    }

    private void UpdateProfil(String pEmail, String pDataID, Object pData){
        aDatabase=FirebaseFirestore.getInstance();
        Map<String,Object> userData=new HashMap<>();
        userData.put(pDataID,pData);

        aDatabase.collection("utilisateur")
                .whereEqualTo("Email",pEmail)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String vDocID=documentSnapshot.getId();
                            DocumentReference docRef= aDatabase.collection("utilisateur").document(vDocID);
                            docRef.update(userData);
                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String vClass=adapterView.getItemAtPosition(i).toString();
        String vEmail=aUser.getEmail();
        UpdateProfil(vEmail,"Class",vClass);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {



    }


}