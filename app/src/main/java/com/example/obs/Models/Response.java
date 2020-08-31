package com.example.obs.Models;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private Object message;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public Object getMessage(){
		return message;
	}
}