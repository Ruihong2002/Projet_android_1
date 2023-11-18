package com.example.projet_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText aEmail, aPassword;
    Button aBtnLogin, aBtnCreate;
    FirebaseAuth mAuth;
    ProgressBar aProgessBar;
    TextView aPasswordForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_page);

        ImageView myImageView = (ImageView) findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.logo_complet);


        aBtnLogin = findViewById(R.id.button2);
        aBtnCreate = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        aEmail = findViewById(R.id.email);
        aPassword = findViewById(R.id.password);
        aProgessBar = findViewById(R.id.progessBar);
        aPasswordForget = findViewById(R.id.mdpforget);
        aBtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aProgessBar.setVisibility(View.VISIBLE);
                String email, password;
                    email = aEmail.getText().toString();
                    password = aPassword.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                        aProgessBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                        aProgessBar.setVisibility(View.GONE);
                        return;
                    }
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    aProgessBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                        Intent it = new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

        });

        aBtnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(it);
            }
        });

        aPasswordForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PasswordForget.class);
                startActivity(it);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent it = new Intent(getApplicationContext(), InterfacePrincipalActivity.class);
            startActivity(it);
            finish();
        }
    }


}