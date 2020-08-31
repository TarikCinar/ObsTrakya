package com.example.obs.Models;

import com.google.gson.annotations.SerializedName;

public class Danisman{

	@SerializedName("CalistigiBirimAdi")
	private String calistigiBirimAdi;

	@SerializedName("Email")
	private String email;

	@SerializedName("Adi")
	private String adi;

	@SerializedName("UnvaniAdiSoyadi")
	private String unvaniAdiSoyadi;

	@SerializedName("UniversiteOdasi")
	private Object universiteOdasi;

	@SerializedName("Unvani")
	private String unvani;

	@SerializedName("CepTelefonu")
	private Object cepTelefonu;

	@SerializedName("UniversiteTelefonu")
	private Object universiteTelefonu;

	@SerializedName("TcKimlikNo")
	private String tcKimlikNo;

	@SerializedName("Resim")
	private Object resim;

	@SerializedName("CalismaDurumu")
	private int calismaDurumu;

	@SerializedName("Telefonu")
	private Object telefonu;

	@SerializedName("Id")
	private int id;

	@SerializedName("WebAdresi")
	private Object webAdresi;

	@SerializedName("Soyadi")
	private String soyadi;

	@SerializedName("CalistigiAltBirimAdi")
	private Object calistigiAltBirimAdi;

	public String getCalistigiBirimAdi(){
		return calistigiBirimAdi;
	}

	public String getEmail(){
		return email;
	}

	public String getAdi(){
		return adi;
	}

	public String getUnvaniAdiSoyadi(){
		return unvaniAdiSoyadi;
	}

	public Object getUniversiteOdasi(){
		return universiteOdasi;
	}

	public String getUnvani(){
		return unvani;
	}

	public Object getCepTelefonu(){
		return cepTelefonu;
	}

	public Object getUniversiteTelefonu(){
		return universiteTelefonu;
	}

	public String getTcKimlikNo(){
		return tcKimlikNo;
	}

	public Object getResim(){
		return resim;
	}

	public int getCalismaDurumu(){
		return calismaDurumu;
	}

	public Object getTelefonu(){
		return telefonu;
	}

	public int getId(){
		return id;
	}

	public Object getWebAdresi(){
		return webAdresi;
	}

	public String getSoyadi(){
		return soyadi;
	}

	public Object getCalistigiAltBirimAdi(){
		return calistigiAltBirimAdi;
	}
}