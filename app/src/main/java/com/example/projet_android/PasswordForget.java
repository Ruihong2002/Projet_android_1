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
import com.google.firebase.auth.FirebaseAuth;

public class PasswordForget extends AppCompatActivity {

    FirebaseAuth aAuth;
    Button aBtnSend;
    ProgressBar aProgessBar;
    TextInputEditText aEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);

        aAuth=FirebaseAuth.getInstance();
        aBtnSend=findViewById(R.id.btnSend);
        aProgessBar=findViewById(R.id.progessBar);
        aEmail=findViewById(R.id.email);

        aBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aProgessBar.setVisibility(View.VISIBLE);
                String email;
                email=aEmail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(PasswordForget.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                aAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    aProgessBar.setVisibility(View.GONE);
                                    Toast.makeText(PasswordForget.this,"Email sended at "+email,Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
            }
        });

    }
}