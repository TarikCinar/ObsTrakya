package com.example.obs.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Notlarim{

	@SerializedName("total")
	private int total;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private Object message;

	public int getTotal(){
		return total;
	}

	public List<DataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public Object getMessage(){
		return message;
	}
}