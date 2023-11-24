package com.example.projet_android;

public class Profil {

    String aPersonNom;
    String aPersonPrenom;
    String aEmail;
    int aPdP;

    public String getEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public Profil(String aPersonNom, String aPersonPrenom, String aEmail, int aPdP) {

        this.aPersonNom = aPersonNom;
        this.aPersonPrenom = aPersonPrenom;
        this.aPdP = aPdP;
        this.aEmail=aEmail;
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
