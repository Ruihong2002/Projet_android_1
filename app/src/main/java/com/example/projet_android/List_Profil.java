package com.example.projet_android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class List_Profil extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView prenomView,nomView;
    private final RecyclerViewInterface recyclerViewInterface;



    public List_Profil(@NonNull @NotNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView3);
        prenomView=itemView.findViewById(R.id.aPersonPrenom);
        nomView=itemView.findViewById(R.id.aPersonNom);
        this.recyclerViewInterface = recyclerViewInterface;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (List_Profil.this.recyclerViewInterface !=null){
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(position);
                    }

            }
        }
    });

    }
}
