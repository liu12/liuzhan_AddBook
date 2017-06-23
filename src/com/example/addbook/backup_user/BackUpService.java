package com.example.addbook.backup_user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.addbook.tool.FinalClass;

import android.net.Uri;
/**查看所有的备份联系人，而不是单纯的某一个联系人。UserActivity.java中的备份，是向服务器提交要备份的联系人
 * 所以你可以看到httpGetMethod()并没有传参*/
public class BackUpService {
	public static List<BackUpUserModule> httpGetMethod() {
		String path = FinalClass.IP+"BackUpServlet";
		try {
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode()==200) {
				InputStream inputStream = httpURLConnection.getInputStream();
				return parseJson(inputStream);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<BackUpUserModule> parseJson(InputStream in) {
		List<BackUpUserModule> lists = new ArrayList<BackUpUserModule>();
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String readline;
		String result = "";
		try {
			while ((readline = bufferedReader.readLine())!=null) {
				result = result + readline;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0;i<jsonArray.length();i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				BackUpUserModule backUpUserModule = new BackUpUserModule();
				backUpUserModule.setName(jsonObject.getString("name"));
				backUpUserModule.setPhone(jsonObject.getString("phone"));		
				backUpUserModule.setDate(jsonObject.getString("date"));
				lists.add(backUpUserModule);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return lists;
	}
}
