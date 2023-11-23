package com.example.projet_android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class List_Profil extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView prenomView,nomView;

    public List_Profil(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView3);
        prenomView=itemView.findViewById(R.id.aPersonPrenom);
        nomView=itemView.findViewById(R.id.aPersonNom);

    }
}
