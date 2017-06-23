package com.example.addbook.tool;

import java.util.Date;

public class TransmitBackupUser {
	public String  submitBackupUser(String name,String phone) {
		Date date = new Date();
		String response = TransmitBackupUserService.httpPostMethod(name, phone, date);
		return response;	
	}
}
