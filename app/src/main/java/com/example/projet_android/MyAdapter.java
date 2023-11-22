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
    public MyAdapter(Context context, List<Profil> pProfil) {
        this.context = context;
        this.aProfil = pProfil;
    }


    @NonNull
    @Override
    public List_Profil onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new List_Profil(LayoutInflater.from(context).inflate(R.layout.personne_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull List_Profil holder, int position) {
        holder.prenomView.setText(aProfil.get(position).getPersonPrenom());
        holder.nomView.setText(aProfil.get(position).getPersonNom());
        holder.imageView.setImageResource(aProfil.get(position).getPdP());
    }

    @Override
    public int getItemCount() {
        return aProfil.size();
    }
}
