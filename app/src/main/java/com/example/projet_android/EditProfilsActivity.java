package com.example.projet_android;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseAuth aAuth;
    FirebaseUser aUser;
    TextView aTextEmailAff,aTextClasseAff,aTextLoisirAff,aTextClubAff,aTextRSAff,aTextBioAff;

    TextView aTextId,aTextEmail;
    ProgressBar aProgressBar;

    ImageButton aBtnEdit;
    ImageView aPdP;
    FirebaseFirestore aDatabase;

    TextInputEditText aBio,aHobbies;

    Spinner aClasse;
    Uri aImagePdP;

    FirebaseStorage aStorage;
    StorageReference aStorageRef;
    DatabaseReference aDbRef;

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
        aClasse=findViewById(R.id.class_select);
        aProgressBar=findViewById(R.id.progessBar1);

        aDatabase=FirebaseFirestore.getInstance();
        aDbRef = FirebaseDatabase.getInstance().getReference().child("Image");
        aStorage=FirebaseStorage.getInstance();

        aStorageRef=FirebaseStorage.getInstance().getReference();


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
                    Toast.makeText(EditProfilsActivity.this,"image selected",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditProfilsActivity.this,"No image selected",Toast.LENGTH_SHORT).show();
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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pPrenom = document.get("First Name").toString();
                                String pNom = document.get("Last Name").toString();
                                String pEmail = document.get("Email").toString();
                                String pHobbies=document.get("Hobbies").toString();
                                String pBio=document.get("Bio").toString();
                                String pClass=document.get("Class").toString();

                                aTextId.setText(pPrenom + " " + pNom);
                                aTextEmail.setText(pEmail);
                                aHobbies.setText(pHobbies);
                                aBio.setText(pBio);

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
                                aClasse.setVisibility(View.VISIBLE);



                            }
                        }
                    }
                });

            aClasse.setOnItemSelectedListener(this);

            aBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vHobbies,vBio,vEmail;
                vHobbies=aHobbies.getText().toString();
                vBio=aBio.getText().toString();
                vEmail=aUser.getEmail();
                if (TextUtils.isEmpty(vBio)==false) {
                    UpdateProfil(vEmail,"Bio",vBio);
                }
                if (TextUtils.isEmpty(vHobbies)==false) {
                    UpdateProfil(vEmail,"Hobbies",vHobbies);
                }
                if (aImagePdP!=null) {
                    uploadIntoStorage(aImagePdP);
                }

                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
    }
/*
    private void selectImageFromGallery() {
        Intent vImage=new Intent();
        vImage.setType("image/*");
        vImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(vImage,PICK_IMAGE_REQUEST);
        //aPdP.setImageURI(aImagePdP);
    }
*/
    private void uploadIntoStorage(Uri pUri) {
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
                             Intent it=new Intent(EditProfilsActivity.this, PageMonProfilActivity.class);
                             startActivity(it);
                             finish();

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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            aImagePdP=data.getData();

        }
    }
*/
    private void UpdateProfil(String pEmail, String pDataID, String pData){
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