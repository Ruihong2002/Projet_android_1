package com.example.projet_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profil> aProfil;

    public MyAdapter(Context context, ArrayList<Profil> aProfil) {
        this.context = context;
        this.aProfil = aProfil;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.personne_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Glide.with(context).load(aProfil.get(position).getPdP()).into(holder.recyclerImageView);
            holder.prenomView.setText(aProfil.get(position).getPersonPrenom());
            holder.nomView.setText(aProfil.get(position).getPersonNom());
    }

    @Override
    public int getItemCount() {
        return aProfil.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImageView;
        TextView prenomView, nomView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImageView = itemView.findViewById(R.id.imageView3);
            prenomView = itemView.findViewById(R.id.aPersonPrenom);
            nomView = itemView.findViewById(R.id.aPersonNom);
        }
    }

}