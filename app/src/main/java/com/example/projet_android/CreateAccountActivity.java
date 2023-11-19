package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    TextInputEditText aEmail,aPrenom,aNom,aPassword;
    Button aBtnLogin, aBtnCreate;
    FirebaseAuth mAuth;
    ProgressBar aProgessBar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        mAuth=FirebaseAuth.getInstance();
        aEmail=findViewById(R.id.email_create);
        aPrenom=findViewById(R.id.Name);
        aNom=findViewById(R.id.Surname);
        aPassword=findViewById(R.id.password_create);
        aBtnLogin = findViewById(R.id.button4);
        aBtnCreate = findViewById(R.id.button3);
        aProgessBar=findViewById(R.id.progessBar);
        db=FirebaseFirestore.getInstance();

        aBtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
            }
        });
        aBtnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aProgessBar.setVisibility(View.VISIBLE);
                String email,password,nom,prenom;
                email=aEmail.getText().toString();
                password=aPassword.getText().toString();
                nom=aNom.getText().toString();
                prenom=aPrenom.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(CreateAccountActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(CreateAccountActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nom)){
                    Toast.makeText(CreateAccountActivity.this,"Enter lastname",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(prenom)){
                    Toast.makeText(CreateAccountActivity.this,"Enter name",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                aProgessBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateAccountActivity.this, "Account Creation Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Map<String,Object> user=new HashMap<>();
                                    user.put("First Name",prenom);
                                    user.put("Last Name",nom);
                                    user.put("Email",email);
                                    user.put("Hobbies","Hobbies");
                                    user.put("Bio","Biography");
                                    user.put("Class","class");

                                    db.collection("utilisateur").add(user);

                                } else {
                                    Toast.makeText(CreateAccountActivity.this, "Account Creation Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
        });

    }


}