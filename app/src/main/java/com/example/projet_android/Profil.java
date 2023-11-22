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

    public String getPersonNom() {
        return aPersonNom;
    }

    public void setPersonNom(String aPersonNom) {
        this.aPersonNom = aPersonNom;
    }

    public String getPersonPrenom() {
        return aPersonPrenom;
    }

    public void setPersonPrenom(String aPersonPrenom) {
        this.aPersonPrenom = aPersonPrenom;
    }

    public int getPdP() {
        return aPdP;
    }

    public void setPdP(int aPdP) {
        this.aPdP = aPdP;
    }
}
