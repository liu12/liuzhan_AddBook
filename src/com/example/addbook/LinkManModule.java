package com.example.addbook;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class LinkManModule {
	private int manImag;
	private String manName;
	private String number;
	private Bitmap userBitmap;

	
	public Bitmap getUserBitmap() {
		return userBitmap;
	}
	public void setUserBitmap(Bitmap userBitmap) {
		this.userBitmap = userBitmap;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getManImag() {
		return manImag;
	}
	public void setManImag(int manImag) {
		this.manImag = manImag;
	}
	public String getManName() {
		return manName;
	}
	public void setManName(String manName) {
		this.manName = manName;
	}
	public LinkManModule(int manImag, String manName,String number) {
		super();
		this.manImag = manImag;
		this.manName = manName;
		this.number = number;
	}
	public LinkManModule(String manName, String number, Bitmap userBitmap) {
		super();
		this.manName = manName;
		this.number = number;
		this.userBitmap = userBitmap;
	}
//	public LinkManModule(String manName, String number, Drawable userDrawable) {
//		super();
//		this.manName = manName;
//		this.number = number;
//		this.userDrawable = userDrawable;
//	}
//	public Drawable getUserDrawable() {
//		return userDrawable;
//	}
//	public void setUserDrawable(Drawable userDrawable) {
//		this.userDrawable = userDrawable;
//	}
	
	
	
	

	
}
