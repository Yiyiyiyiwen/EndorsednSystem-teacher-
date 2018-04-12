package com.example.even1.endorsedsystemteacher.Data;

public class Books {
	private String date;
	private int imageId;
	public Books(String date,int imageId) {
		this.date = date;
		this.imageId = imageId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	

}
