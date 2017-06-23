package com.example.addbook.date_note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.addbook.R;
import com.example.addbook.tool.NotesDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DateBookActivity extends Activity implements OnScrollListener,
OnItemClickListener, OnItemLongClickListener{
	private Context mContext;
	private ListView listview;
	private SimpleAdapter simp_adapter;
	private List<Map<String, Object>> dataList;
	private Button addNote;
	private TextView tv_content;
	private NotesDB DB;
	private SQLiteDatabase dbread;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_date_note);
		tv_content = (TextView) findViewById(R.id.tv_content);
		listview = (ListView) findViewById(R.id.dateNoteListview);
		dataList = new ArrayList<Map<String, Object>>();

		addNote = (Button) findViewById(R.id.btn_editnote);
		mContext = this;
		addNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditDateNote.ENTER_STATE = 0;
				Intent intent = new Intent(mContext, EditDateNote.class);
				Bundle bundle = new Bundle();
				bundle.putString("info", "");
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});
		
		DB = new NotesDB(this);
		dbread = DB.getReadableDatabase();
		name = getIntent().getStringExtra("name");
		System.out.println(name+"&&&&&&^^^^^&&&^^&&&^&");
		// 鍒锋柊鏃ュ織鍒楄〃
		RefreshNotesList();

		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);
		listview.setOnScrollListener(this);
	}

	public void RefreshNotesList() {

		int size = dataList.size();
		if (size > 0) {
			dataList.removeAll(dataList);
			simp_adapter.notifyDataSetChanged();
			listview.setAdapter(simp_adapter);
		}
		simp_adapter = new SimpleAdapter(this, getData(), R.layout.datenote_listitem,
				new String[] { "tv_content", "tv_date" }, new int[] {
						R.id.tv_content, R.id.tv_date });
		listview.setAdapter(simp_adapter);
	}

	private List<Map<String, Object>> getData() {

		Cursor cursor = dbread.query("note"+name, null, "content!=\"\"", null, null,
				null, null);

		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("content"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tv_content", name);
			map.put("tv_date", date);
			dataList.add(map);
		}
		cursor.close();
		return dataList;

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case SCROLL_STATE_FLING:
			Log.i("main", "");
		case SCROLL_STATE_IDLE:
			Log.i("main", "");
		case SCROLL_STATE_TOUCH_SCROLL:
			Log.i("main", "");
		}
	}

	//杞诲井鐐瑰嚮list item鍚庣殑鍝嶅簲
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		EditDateNote.ENTER_STATE = 1;
		String content = listview.getItemAtPosition(arg2) + "";
		String content1 = content.substring(content.indexOf("=") + 1,
				content.indexOf(","));
		Log.d("CONTENT", content1);
		Cursor c = dbread.query("note"+name, null,
				"content=" + "'" + content1 + "'", null, null, null, null);
		while (c.moveToNext()) {
			String No = c.getString(c.getColumnIndex("_id"));
			Log.d("TEXT", No);
			Intent myIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("info", content1);
			EditDateNote.id = Integer.parseInt(No);
			myIntent.putExtras(bundle);
			myIntent.setClass(DateBookActivity.this, EditDateNote.class);
			startActivityForResult(myIntent, 1);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 2) {
			RefreshNotesList();
		}
	}

	// 闀挎椂闂寸偣鍑籨ateNoteList item鐨勫搷搴�
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		final int n=arg2;
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("删除该记录");
		builder.setMessage("确认删除吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String content = listview.getItemAtPosition(n) + "";
				String content1 = content.substring(content.indexOf("=") + 1,
						content.indexOf(","));
				Cursor c = dbread.query("note"+name, null, "content=" + "'"
						+ content1 + "'", null, null, null, null);
				while (c.moveToNext()) {
					String id = c.getString(c.getColumnIndex("_id"));
					String sql_del = "update note"+name+" set content='' where _id="
							+ id;
					dbread.execSQL(sql_del);
					RefreshNotesList();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
		builder.show();
		return true;
	}

}
