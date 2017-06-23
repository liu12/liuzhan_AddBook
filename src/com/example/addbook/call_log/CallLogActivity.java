package com.example.addbook.call_log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.example.addbook.R;
import com.example.addbook.user.UserActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CallLogActivity extends Activity {
	private ListView callLogListView;
	private Button back;
	String callName;
	int intimateFlag = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_log);
		
		callLogListView = (ListView) findViewById(R.id.callLogListView);
		back = (Button)findViewById(R.id.back);
		
		callLogListView.setAdapter(new CallLogAdapter(CallLogActivity.this, initLists()));
		System.out.println("***********"+getMaxCallDuration(initLists()));
	
		
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				transmitIntimateFlag(initLists());
			}
		});
	}

	public List<CallLogModule> initLists() {
		// TODO Auto-generated method stub
		ContentResolver contentResolver = getContentResolver();
		String name = getIntent().getStringExtra("callName");
		Cursor cursor = null;
		List<CallLogModule> lists = new ArrayList<CallLogModule>();
		try {
			cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, new String[] { CallLog.Calls.CACHED_NAME// 閫氳瘽璁板綍鐨勮仈绯讳汉
			, CallLog.Calls.NUMBER// 閫氳瘽璁板綍鐨勭數璇濆彿鐮�
			, CallLog.Calls.DATE// 閫氳瘽璁板綍鐨勬棩鏈�
			, CallLog.Calls.DURATION// 閫氳瘽鏃堕暱
			, CallLog.Calls.TYPE }// 閫氳瘽绫诲瀷
			, "name=?", new String[] {name}, CallLog.Calls.DEFAULT_SORT_ORDER);
			if (cursor == null) {
				return null;
			}
			while (cursor.moveToNext()) {			
				callName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
				String callPhone = "";
				String callDate = "";
				int callDuration = 0;
				String callType = "";
				callPhone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
				long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
				callDate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date(date));
				callDuration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
				int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
				switch (type) {
				case CallLog.Calls.INCOMING_TYPE:
					callType = "打入";
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					callType = "打出";
					break;
				case CallLog.Calls.MISSED_TYPE:
					callType = "未接";
					break;
				default:
					break;
				}
				CallLogModule callLogModule = new CallLogModule(callName, callPhone, callDuration, callDate, callType);
				lists.add(callLogModule);
			}
			
		} finally {
			// TODO: handle exception
			if (cursor != null) {
				cursor.close();
			}
		}

		return lists;

	}
	//鍙栭�氳瘽璁板綍鍒楄〃涓�氳瘽鏃堕棿鐨勬渶澶у��
	public int getMaxCallDuration(List<CallLogModule> lists) {
		int max=0;
		int j = 0;
		for (int i = 0;i<lists.size();i++) {
			if ((int)(lists.get(i).getCallDuration())>=1000) {
				if (max<	(int)(lists.get(i).getCallDuration())/100) {
					max = (int)(lists.get(i).getCallDuration())/100;	
					j = i;
				}
			} else if ((int)(lists.get(i).getCallDuration())>=100&&(int)(lists.get(i).getCallDuration())<1000) {
				if (max<	(int)(lists.get(i).getCallDuration())/10) {
					max = ((int)lists.get(i).getCallDuration())/10;
					j =i;
				}
			} else {
				if (lists.get(i).getCallDuration()<100) {
					if (max<	lists.get(i).getCallDuration()) {
						max = lists.get(i).getCallDuration();
						j = i;
					}
				}
			}
			
		}
		return max;
		
	}
	//鍒ゆ柇涓や釜浜烘槸鍚︿翰瀵�
	public boolean isIntimate(List<CallLogModule> lists) {
		if (lists.size()>15||getMaxCallDuration(lists)>90) {
			return true;
		}
		return false;
	}
	//鍚慤serActivity涓紶鍊笺��
	public void transmitIntimateFlag(List<CallLogModule> lists) {
		Intent intent = new Intent();
		if (isIntimate(lists)) {			
			intent.putExtra("intimateFlag", intimateFlag);
		} else {		
			intent.putExtra("intimateFlag", 0);
		}
		setResult(1,intent);
		finish();
	}
}
