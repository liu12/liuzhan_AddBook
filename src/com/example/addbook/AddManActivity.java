package com.example.addbook;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddManActivity extends Activity{
	private Button completeBtn;
	private ImageView userImage;
	private EditText nameEdit,phoneEdit;
	public static String userPicture;
	private Bitmap bitmap;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_man);	
		completeBtn = (Button)findViewById(R.id.completeBtn);
		userImage = (ImageView)findViewById(R.id.user_image);
		nameEdit = (EditText)findViewById(R.id.nameEdit);
		phoneEdit = (EditText)findViewById(R.id.phoneEdit);
		completeBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addMan(nameEdit, phoneEdit);	
				Intent intent = new Intent(AddManActivity.this,MainActivity.class);
				intent.putExtra("userPicture", userPicture);
				intent.putExtra("name", nameEdit.getText().toString());
				startActivity(intent);
				System.out.println(">>>>>>>>>>>>>>>");
				
				finish();
			}
		});		
		userImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectUserImage();
			}
		});
	}
	public void selectUserImage() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(AddManActivity.this,com.example.addbook.tool.SelectPicPopupWindow.class);
		startActivityForResult(intent,2);
	}
	public void addMan(EditText editText1,EditText editText2) {
//向raw_contacts中添加数据，并获取添加的Id号
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver contentResolver = getContentResolver();
		ContentValues contentValues = new ContentValues();
		Long contactId = ContentUris.parseId(contentResolver.insert(uri, contentValues));
//向data中添加数据（要根据前面获取的id号）
//添加姓名
		uri = Uri.parse("content://com.android.contacts/data");
		contentValues.put("raw_contact_id", contactId);
		contentValues.put("mimetype", "vnd.android.cursor.item/name");
		contentValues.put("data2", editText1.getText().toString());
		contentResolver.insert(uri, contentValues);	
//添加号码		
		contentValues.clear();  
		contentValues.put("raw_contact_id", contactId);  
		contentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");  
		contentValues.put("data2", "2");  
		contentValues.put("data1", editText2.getText().toString());  
		contentResolver.insert(uri, contentValues);
		System.out.println("***************************");	
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {		
		System.out.println(resultCode);	
		if (data != null) {
			switch (resultCode) {			
			case 2:
				userPicture = null;
				//获取从SelectPicPopupWindow.java中传过来的图片路径
				userPicture = data.getExtras().getString("picturePath");
				System.out.println(userPicture+"++++=====");
				//根据图片路径直接将其转化为图片格式
			    bitmap = BitmapFactory.decodeFile(userPicture);
				userImage.setImageBitmap(bitmap);			
				break;
			default:
				break;
			}
		}
	}
}
