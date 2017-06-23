package com.example.addbook.date_note;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.addbook.R;
import com.example.addbook.tool.NotesDB;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class EditDateNote extends Activity {
	private TextView tv_date;
	private EditText et_content;
	private Button btn_ok;
	private Button btn_cancel;
	private NotesDB DB;
	private SQLiteDatabase dbread;
	public static int ENTER_STATE = 0;
	public static String last_content;
	public static int id;
	public static String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_date_note);
		tv_date = (TextView) findViewById(R.id.tv_date);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = sdf.format(date);
		tv_date.setText(dateString);
		et_content = (EditText) findViewById(R.id.et_content);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		DB = new NotesDB(this);
		dbread = DB.getReadableDatabase();

		Bundle myBundle = this.getIntent().getExtras();
		last_content = myBundle.getString("info");
		Log.d("LAST_CONTENT", last_content);
		et_content.setText(last_content);
		
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {				
				String content = et_content.getText().toString();
				Log.d("LOG1", content);		
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateNum = sdf.format(date);
				String sql;
				String sql_count = "SELECT COUNT(*) FROM note"+name;
				SQLiteStatement statement = dbread.compileStatement(sql_count);
				long count = statement.simpleQueryForLong();
				if (ENTER_STATE == 0) {
					if (!content.equals("")) {
						sql = "insert into " + NotesDB.TABLE_NAME_NOTES +name+ " values(" + count + "," + "'" + content + "'"
								+ "," + "'" + dateNum + "')";
						Log.d("LOG", sql);
						dbread.execSQL(sql);
					}
				} else {
					String updatesql = "update note"+name+"set content='" + content + "' where _id=" + id;
					dbread.execSQL(updatesql);
				}
				Intent data = new Intent();
				setResult(2, data);
				finish();
			}
		});
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
}
