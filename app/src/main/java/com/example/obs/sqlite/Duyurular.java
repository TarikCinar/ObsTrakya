package com.example.obs.sqlite;

public class Duyurular {
    private int duyuru_id;
    private String duyuru_ad;
    private String duyuru_link;

    public Duyurular() {
    }

    public Duyurular(int duyuru_id, String duyuru_ad, String duyuru_link) {
        this.duyuru_id = duyuru_id;
        this.duyuru_ad = duyuru_ad;
        this.duyuru_link = duyuru_link;
    }

    public int getDuyuru_id() {
        return duyuru_id;
    }

    public void setDuyuru_id(int duyuru_id) {
        this.duyuru_id = duyuru_id;
    }

    public String getDuyuru_ad() {
        return duyuru_ad;
    }

    public void setDuyuru_ad(String duyuru_ad) {
        this.duyuru_ad = duyuru_ad;
    }

    public String getDuyuru_link() {
        return duyuru_link;
    }

    public void setDuyuru_link(String duyuru_link) {
        this.duyuru_link = duyuru_link;
    }
}
