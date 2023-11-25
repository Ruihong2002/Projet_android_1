package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InterfacePrincipalActivity extends AppCompatActivity {

    FirebaseAuth aAuth;
    Button aLogOut,aOtherProfile;
    FirebaseUser user;
    ImageButton aBtnProfil,aBtnConv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_principal);

        aLogOut = findViewById(R.id.button5);
        aBtnConv= (ImageButton) findViewById(R.id.imageButtonConversation);
        aBtnProfil = (ImageButton)findViewById(R.id.imageButtonMonProfil);

        aOtherProfile=findViewById(R.id.btnOther);
        aAuth=FirebaseAuth.getInstance();
        user = aAuth.getCurrentUser();

        Profil pProfil=new Profil("Hzudb","Veudbd","laurent2002laurent@hotmail.com",R.drawable.logo_complet);
        aOtherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), PageAutreProflsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("profil_nom",pProfil.getPersonNom());
                bundle.putString("profil_name",pProfil.getPersonPrenom());
                bundle.putString("profil_email",pProfil.getEmail());
                it.putExtras(bundle);
                startActivity(it);
                finish();
            }
        });

        if (user==null) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(it);
            finish();
        }
        aLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        aBtnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PageMonProfilActivity.class);
                startActivity(it);
            }
        });
        aBtnConv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it= new Intent(getApplicationContext(), InterfaceListConversationActivity.class );
                startActivity(it);
            }
        });
    }
}