package com.example.addbook.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class TransmitBackupUserService {
	static String path = FinalClass.IP+"InsertBackUpUserServlet";

	public static String httpPostMethod(String name, String phone, Date date) {
		HttpPost httpPost = new HttpPost(path);
		List<NameValuePair> lists = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair1 = new BasicNameValuePair("name", name);
		NameValuePair nameValuePair2 = new BasicNameValuePair("phone", phone);
		long thime = date.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = simpleDateFormat.format(date);
		NameValuePair nameValuePair3 = new BasicNameValuePair("date", dateString);

		// java.text.DateFormat dateFormat =
		// java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.FULL,
		// java.text.DateFormat.FULL, Locale.CHINA);
		// String curDate = dateFormat.format(new java.util.Date());
		lists.add(nameValuePair1);
		lists.add(nameValuePair2);
		lists.add(nameValuePair3);

		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(lists, HTTP.UTF_8);
			httpPost.setEntity(httpEntity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String response = "";
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					response = response + readLine;
				}

				inputStream.close();
				br.close();
			
				return response;
			} else {
				return "error";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception";
		}
	}
}
