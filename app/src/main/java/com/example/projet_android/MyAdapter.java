package com.example.projet_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<List_Profil> {

    Context context;
    List<Profil> aProfil;
    public MyAdapter(Context context, List<Profil> aProfil) {
        this.context = context;
        this.aProfil = aProfil;
    }


    @NonNull
    @Override
    public List_Profil onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new List_Profil(LayoutInflater.from(context).inflate(R.layout.personne_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull List_Profil holder, int position) {
        holder.prenomView.setText(aProfil.get(position).getaPersonPrenom());
        holder.nomView.setText(aProfil.get(position).getaPersonNom());
        holder.imageView.setImageResource(aProfil.get(position).getaPdP());
    }

    @Override
    public int getItemCount() {
        return aProfil.size();
    }
}
