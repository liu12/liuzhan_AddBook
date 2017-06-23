package com.example.addbook.backup_user;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.addbook.MainActivity;
import com.example.addbook.R;
import com.example.addbook.backupByFileIO.BackUpFileRead;
import com.example.addbook.backupByFileIO.BackUpFileWrite;
import com.example.addbook.backupByFileIO.DeleteFileItem;
import com.example.addbook.delete_backUpUser.DeleteService;
import com.example.addbook.tool.NetWorkConnect;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

/** BackUpActivity.鐢ㄦ潵鍛堢幇鎵�鏈夊凡缁忓浠界殑鑱旂郴浜鸿�屼笉鏄煇涓�涓� */
@SuppressLint({ "HandlerLeak", "NewApi" })
public class BackUpActivity extends Activity {
	private ListView backupListView;
	private List<BackUpUserModule> lists = new ArrayList<BackUpUserModule>();
	private List<BackUpUserModule> deleteLists = new ArrayList<BackUpUserModule>();
	private BackUpAdapter backUpAdapter;
	private boolean deleteFlag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);

		backupListView = (ListView) findViewById(R.id.backupListView);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		NetWorkConnect netWorkConnect = new NetWorkConnect(BackUpActivity.this);

		if (netWorkConnect.isConnectInternet()) {
			lists.clear();
			lists = BackUpService.httpGetMethod();
			backUpAdapter = new BackUpAdapter(BackUpActivity.this, lists);
		} else {
			lists.clear();
			lists = BackUpFileRead.fileRead();
			backUpAdapter = new BackUpAdapter(BackUpActivity.this, lists);
			
		}

		backupListView.setAdapter(backUpAdapter);

		backupListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				NetWorkConnect netWorkConnect = new NetWorkConnect(BackUpActivity.this);
				String name = lists.get(position).getName();
				// 鏈夌綉鍜屾棤缃戜袱绉嶆儏鍐典笅鐨勬搷浣溿��
				if (netWorkConnect.isConnectInternet()) {
					DeleteService.deleteGet(name);
				} else {
					DeleteFileItem deleteFileItem = new DeleteFileItem();
//					BackUpFileWrite.deleteFile();
					
					deleteFileItem.replaceOldFile(deleteFileItem.delete(lists.get(position).getName()));
					Toast.makeText(BackUpActivity.this, "无网删除成功", Toast.LENGTH_SHORT).show();
				}
				lists.remove(position);
				backUpAdapter.notifyDataSetChanged();
			}
		});

		backupListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				String name = lists.get(position).getName();
				String phone = lists.get(position).getPhone();
				// 鍚憆aw_contacts涓坊鍔犳暟鎹紝骞惰幏鍙栨坊鍔犵殑Id鍙�
				Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
				ContentResolver contentResolver = getContentResolver();
				ContentValues contentValues = new ContentValues();
				Long contactId = ContentUris.parseId(contentResolver.insert(uri, contentValues));
				// 鍚慸ata涓坊鍔犳暟鎹紙瑕佹牴鎹墠闈㈣幏鍙栫殑id鍙凤級
				// 娣诲姞濮撳悕
				uri = Uri.parse("content://com.android.contacts/data");
				contentValues.put("raw_contact_id", contactId);
				contentValues.put("mimetype", "vnd.android.cursor.item/name");
				contentValues.put("data2", name);
				contentResolver.insert(uri, contentValues);
				// 娣诲姞鍙风爜
				contentValues.clear();
				contentValues.put("raw_contact_id", contactId);
				contentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
				contentValues.put("data2", "2");
				contentValues.put("data1", phone);
				contentResolver.insert(uri, contentValues);
				Toast.makeText(BackUpActivity.this, "恢复数据成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(BackUpActivity.this, MainActivity.class);
				startActivity(intent);
				return true;
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		onCreate(null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 杩囨护鎸夐敭鍔ㄤ綔
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(BackUpActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

}
