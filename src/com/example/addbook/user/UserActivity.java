package com.example.addbook.user;

import java.util.Date;
import com.example.addbook.AddManActivity;
import com.example.addbook.MainActivity;
import com.example.addbook.R;
import com.example.addbook.backupByFileIO.BackUpFileWrite;
import com.example.addbook.backup_user.BackUpActivity;
import com.example.addbook.bean.SendMessageActivity;
import com.example.addbook.call_log.CallLogActivity;
import com.example.addbook.date_note.DateBookActivity;
import com.example.addbook.date_note.EditDateNote;
import com.example.addbook.tool.NetWorkConnect;
import com.example.addbook.tool.NotesDB;
import com.example.addbook.tool.TransmitBackupUserService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class UserActivity extends Activity implements OnClickListener {
	private TextView userNameDetailText, phoneNumberDetailText, isIntimateText;
	private Button addButton, phoneCallButton, phoneMessageButton, backUpButton, callLogButton, dateNoteBtn;
	private ImageView userDetailImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity);

		userNameDetailText = (TextView) findViewById(R.id.userNameDetail_text);
		phoneNumberDetailText = (TextView) findViewById(R.id.phoneNumberDetail_text);
		isIntimateText = (TextView) findViewById(R.id.isIntimateText);

		addButton = (Button) findViewById(R.id.addButton);
		phoneCallButton = (Button) findViewById(R.id.phoneCallButton);
		phoneMessageButton = (Button) findViewById(R.id.phoneMessageButton);

		backUpButton = (Button) findViewById(R.id.backUpButton);
		callLogButton = (Button) findViewById(R.id.callLogButton);
		userDetailImage = (ImageView) findViewById(R.id.userDetail_image);
		dateNoteBtn = (Button) findViewById(R.id.dateNoteBtn);

		initTextAndImage(userNameDetailText, phoneNumberDetailText, userDetailImage, getIntent());
		// ifIntimate(isIntimateText);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

		addButton.setOnClickListener(this);
		phoneCallButton.setOnClickListener(this);
		phoneMessageButton.setOnClickListener(this);

		backUpButton.setOnClickListener(this);
		callLogButton.setOnClickListener(this);
		dateNoteBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addButton:
			Intent intent = new Intent(UserActivity.this, AddManActivity.class);
			startActivity(intent);
			break;
		case R.id.phoneCallButton:
			phoneCall(getIntent());
			break;
		case R.id.phoneMessageButton:
			Intent intent1 = new Intent(UserActivity.this, SendMessageActivity.class);
			intent1.putExtra("userPhone", getIntent().getStringExtra("phone"));
			startActivity(intent1);
			break;
		case R.id.backUpButton:
			NetWorkConnect netWorkConnect = new NetWorkConnect(UserActivity.this);
			if (netWorkConnect.isConnectInternet()) {
				Date date = new Date();
				// 鐐瑰嚮鎸夐挳锛屼細灏嗚仈绯讳汉淇℃伅涓婁紶鍒版暟鎹簱锛屽緟1鍒嗛挓寤舵椂鍚庯紝璺宠浆鍒癰ackupActivity鐣岄潰
				String response = insertBackUpUser(userNameDetailText.getText().toString(),
						phoneNumberDetailText.getText().toString(), date);
				System.out.println(userNameDetailText.getText().toString() + "{{{{{{{{{"
						+ phoneNumberDetailText.getText().toString());
				if (response.equals("success")) {
					Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				BackUpFileWrite backUpFileWrite = new BackUpFileWrite();
				backUpFileWrite.fileWrite(userNameDetailText.getText().toString(),
						phoneNumberDetailText.getText().toString());
				Toast.makeText(getApplicationContext(), "上传成功!!!!!!", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.dateNoteBtn:
			Intent intent3 = new Intent(UserActivity.this, DateBookActivity.class);
			intent3.putExtra("name", userNameDetailText.getText().toString());
			NotesDB.name = userNameDetailText.getText().toString();
			EditDateNote.name = userNameDetailText.getText().toString();
			startActivity(intent3);
			break;
		default:
			Intent intent2 = new Intent(UserActivity.this, CallLogActivity.class);
			intent2.putExtra("callName", userNameDetailText.getText().toString());
			startActivityForResult(intent2, 1);
			break;
		}
	}

	public void phoneCall(Intent data) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri uri = Uri.parse("tel:" + data.getStringExtra("phone"));
		intent.setData(uri);
		startActivity(intent);
	}

	public void initTextAndImage(TextView userNameText, TextView userPhoneText, ImageView userImage, Intent data) {
		Bitmap bitmap = BitmapFactory.decodeFile(AddManActivity.userPicture);
		userNameText.setText(data.getStringExtra("name"));
		userPhoneText.setText(data.getStringExtra("phone"));
		// 鐢ㄤ簬鍒ゆ柇鏄嚜宸遍�夋嫨鐨勫浘鐗囪繕鏄郴缁熼粯璁ょ殑鍥剧墖
		if (bitmap != null) {
			if (data.getIntExtra("userImage", R.drawable.wode) == R.drawable.pt17) {
				userImage.setImageResource(data.getIntExtra("userImage", R.drawable.wode));
			} else {
				userImage.setImageBitmap(bitmap);
			}
		} else {
			userImage.setImageResource(data.getIntExtra("userImage", R.drawable.wode));
		}
		System.out.println(data.getStringExtra("name") + "=================" + bitmap);
	}

	public String insertBackUpUser(String name, String phone, Date date) {
		return TransmitBackupUserService.httpPostMethod(name, phone, date);
	}

	// 濡傛灉鑱旂郴浜轰翰瀵嗭紝鏂囨。妗嗗彉鍖栥��
	public void ifIntimate(TextView textView, Intent intent) {
		if (intent.getIntExtra("intimateFlag", 0) == 1) {
			System.out.println(intent.getIntExtra("intimateFlag", 0) + "|||||||||||||||||||||||||||PPPPPP");
			textView.setText("亲密");
		} else {
			textView.setText("未亲密");
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 1 || requestCode == 1) {
			ifIntimate(isIntimateText, data);
			System.out.println(data.getIntExtra("intimateFlag", 0) + "\\\\\\\\\\\\\\\\\\");
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 杩囨护鎸夐敭鍔ㄤ綔
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(UserActivity.this,MainActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
