package com.example.addbook.backup_user;

import java.sql.Date;

public class BackUpUserModule {
	private String name;
	private String phone;
	private String date;
	public BackUpUserModule(String name, String phone, String date) {
		super();
		this.name = name;
		this.phone = phone;
		this.date = date;
	}
	public BackUpUserModule() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
