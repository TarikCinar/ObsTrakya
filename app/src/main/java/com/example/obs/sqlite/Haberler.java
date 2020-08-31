package com.example.obs.sqlite;

public class Haberler {
    private String resim;
    private String baslik;
    private String altBaslik;
    private String link;

    public Haberler() {
    }

    public Haberler(String resim, String baslik, String altBaslik, String link) {
        this.resim = resim;
        this.baslik = baslik;
        this.altBaslik = altBaslik;
        this.link = link;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAltBaslik() {
        return altBaslik;
    }

    public void setAltBaslik(String altBaslik) {
        this.altBaslik = altBaslik;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
