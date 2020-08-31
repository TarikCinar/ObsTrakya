package com.example.obs.sqlite;

public class Bilgiler {
    private String okulNo;
    private String isim;
    private String isimSoyisim;
    private String username;
    private String password;
    private String mail;
    private String danismanAd;
    private String danismanKurum;
    private String danismanMail;
    private String resim;
    private String giris;

    public Bilgiler() {
    }

    public Bilgiler(String okulNo, String isim, String isimSoyisim, String username, String password, String mail, String danismanAd, String danismanKurum, String danismanMail, String resim, String giris) {
        this.okulNo = okulNo;
        this.isim = isim;
        this.isimSoyisim = isimSoyisim;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.danismanAd = danismanAd;
        this.danismanKurum = danismanKurum;
        this.danismanMail = danismanMail;
        this.resim = resim;
        this.giris = giris;
    }


    public String getOkulNo() {
        return okulNo;
    }

    public void setOkulNo(String okulNo) {
        this.okulNo = okulNo;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getIsimSoyisim() {
        return isimSoyisim;
    }

    public void setIsimSoyisim(String isimSoyisim) {
        this.isimSoyisim = isimSoyisim;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDanismanAd() {
        return danismanAd;
    }

    public void setDanismanAd(String danismanAd) {
        this.danismanAd = danismanAd;
    }

    public String getDanismanKurum() {
        return danismanKurum;
    }

    public void setDanismanKurum(String danismanKurum) {
        this.danismanKurum = danismanKurum;
    }

    public String getDanismanMail() {
        return danismanMail;
    }

    public void setDanismanMail(String danismanMail) {
        this.danismanMail = danismanMail;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getGiris() {
        return giris;
    }

    public void setGiris(String giris) {
        this.giris = giris;
    }
}
