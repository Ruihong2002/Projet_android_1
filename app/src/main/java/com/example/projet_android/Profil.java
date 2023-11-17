package com.example.projet_android;

public class Profil {

    String aPersonNom;
    String aPersonPrenom;
    int aPdP;

    public Profil(String aPersonNom, String aPersonPrenom, int aPdP) {

        this.aPersonNom = aPersonNom;
        this.aPersonPrenom = aPersonPrenom;
        this.aPdP = aPdP;
    }

    public String getaPersonNom() {
        return aPersonNom;
    }

    public void setaPersonNom(String aPersonNom) {
        this.aPersonNom = aPersonNom;
    }

    public String getaPersonPrenom() {
        return aPersonPrenom;
    }

    public void setaPersonPrenom(String aPersonPrenom) {
        this.aPersonPrenom = aPersonPrenom;
    }

    public int getaPdP() {
        return aPdP;
    }

    public void setaPdP(int aPdP) {
        this.aPdP = aPdP;
    }
}
