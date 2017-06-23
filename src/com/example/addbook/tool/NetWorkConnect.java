package com.example.addbook.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkConnect {
	private Context context;
	
	public NetWorkConnect(Context context) {
		super();
		this.context = context;
	}

	public boolean isConnectInternet() {
		boolean netSataus = false;
		ConnectivityManager conManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错
		    netSataus = networkInfo.isAvailable();		 
		}
		return netSataus;
		}
}
