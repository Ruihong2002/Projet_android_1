package com.example.projet_android;

public class Model {
    private String aImageUri;
    public Model(String pImageUri){
        this.aImageUri=pImageUri;
    }

    public String getImageUri() {
        return aImageUri;
    }

    public void setImageUri(String aImageUri) {
        this.aImageUri = aImageUri;
    }
}

