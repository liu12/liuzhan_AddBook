package com.example.addbook.delete_backUpUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.addbook.tool.FinalClass;

public class DeleteService {
	public static void deleteGet(String name) {
		String path = FinalClass.IP+"DeleteUserServlet?name="+name;
		try {
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode()==200) {
				System.out.println(path+"$$$$$$$$$$$$$$$$$$$$$");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
