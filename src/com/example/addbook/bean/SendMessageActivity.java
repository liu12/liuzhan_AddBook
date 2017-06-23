package com.example.addbook.bean;

import java.util.ArrayList;

import com.example.addbook.MainActivity;
import com.example.addbook.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SendMessageActivity extends Activity {
	private EditText sendMessageToPhoneEdit, messageEdit;
	private Button sendMessageButton;
	private ImageButton selectUserButton;
	// 鐢ㄤ簬鍒ゆ柇鏄惁鐐瑰嚮selectUserButton
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message_activity);

		sendMessageToPhoneEdit = (EditText) findViewById(R.id.sendMessageToPhoneEdit);
		messageEdit = (EditText) findViewById(R.id.messageEdit);

		sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
		selectUserButton = (ImageButton) findViewById(R.id.selectUserButton);

		/*
		 * sendMessageToPhoneEdit濉啓瑕佸彂閫佸璞＄殑鎵嬫満鍙枫�傛墜鏈哄彿鐢盪serActivity.java鎴朚ainActivity.
		 * java涓紶閫佽繃鏉� 瑕佸厛鍒ゆ柇涓�浜涖��
		 */
		String phone = getIntent().getStringExtra("selectPhone");
		if (phone == null) {
			sendMessageToPhoneEdit.setText(getIntent().getStringExtra("userPhone"));
		} else {
			sendMessageToPhoneEdit.setText(phone);
		}

		sendMessageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneNumber = sendMessageToPhoneEdit.getText().toString();
				initSMToPhone(phoneNumber, messageEdit);
				Toast.makeText(getApplicationContext(), "发送完毕", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		selectUserButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendMessageActivity.this, MainActivity.class);
				intent.putExtra("flag", flag);
				startActivityForResult(intent, 0);
			}
		});

	}

	@SuppressLint("NewApi")
	public void initSMToPhone(String phoneNumber, EditText messageEdit) {

		String message = messageEdit.getText().toString();
		// 璋冪敤SmsManager瀵硅薄瀹炵幇鍙戠煭淇�
		SmsManager smsManager = SmsManager.getDefault();

		// 鍥犱负涓�鏉＄煭淇℃湁瀛楁暟闄愬埗锛屽洜姝よ灏嗛暱鐭俊鎷嗗垎
		ArrayList<String> messageList = smsManager.divideMessage(message);
		for (String text : messageList) {
			smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String phone = data.getStringExtra("selectPhone");
		System.out.println(phone);
		switch (resultCode) {
		case 0:
			sendMessageToPhoneEdit.setText(phone);
			initSMToPhone(phone, messageEdit);
			break;

		default:
			break;
		}
	}

}
