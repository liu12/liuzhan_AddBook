package com.example.addbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.addbook.backupByFileIO.BackUpFileWrite;
import com.example.addbook.backup_user.BackUpActivity;
import com.example.addbook.bean.SendMessageActivity;
import com.example.addbook.tool.CompressJPEG;
import com.example.addbook.tool.DeleteItem;
import com.example.addbook.tool.NetWorkConnect;
import com.example.addbook.tool.SearchTextWatcher;
import com.example.addbook.tool.TransmitBackupUser;
import com.example.addbook.user.UserActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener{
	private ListView linkManListView;
	private Button addManBtn, lookBackUpUser;
	// 鐢ㄤ簬涓嶴endMessageActivity.java涓紶鏉ョ殑flag姣旇緝锛屼粠鑰屽垽鏂槸鍚︾偣鍑籹electUserButton
	private boolean flag;
	private boolean filterFlag=true;
	private String name, phoneNumber;
	private int userImage;
	private LinkManAdapter linkManAdapter;
	private List<LinkManModule> lists = new ArrayList<LinkManModule>();
	List<LinkManModule> searchList = new ArrayList<LinkManModule>();
	private int position;
	private Bitmap userBitmap;
	private String response;
	SearchTextWatcher searchTextWatch;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText searchEdit = (EditText)findViewById(R.id.searchEdit);
		linkManListView = (ListView) findViewById(R.id.linkManList);
		addManBtn = (Button) findViewById(R.id.addLinkman);
		lookBackUpUser = (Button) findViewById(R.id.lookBackUpUser);
		if (filterFlag) {
			linkManAdapter = new LinkManAdapter(initList(), MainActivity.this);
		} else {
			linkManAdapter.notifyDataSetChanged();
			linkManAdapter = new LinkManAdapter(searchList, MainActivity.this);
		}
		
		searchEdit.clearFocus();
		searchEdit.addTextChangedListener(filterTextWatcher);
		
		
		linkManListView.setAdapter(linkManAdapter);
		linkManListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				// TODO Auto-generated method stub
				MainActivity.this.position = position;						
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("查看联系人");
				builder.setMessage("是查看联系人还是删除联系人？");		
				builder.setPositiveButton("查看联系人", new DialogInterface.OnClickListener() {				
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this,UserActivity.class);
						transmitValue(position, intent);
						startActivity(intent);
					}
				});
				
				builder.setNegativeButton("删除联系人", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
//						if (response.equals("涓婁紶鎴愬姛锛�")) {							
							try {
								Thread thread = new Thread(runnable);
								thread.start();
											
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

										
				});
				
				builder.create();
				builder.show();
			}

		});

		flag = getIntent().getBooleanExtra("flag", false);
		if (flag == true) {
			// 褰搕rue鐨勬椂鍊欒〃绀虹偣鍑讳簡selectUserButton
			linkManListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					String phone = initList().get(position).getNumber();
					Intent intent = new Intent();
					intent.putExtra("selectPhone", phone);
					MainActivity.this.setResult(0, intent);
					MainActivity.this.finish();
					return true;
				}
			});
		} else {
			// 褰揻alse鐨勬椂鍊欒〃绀猴紝娌℃湁鐐瑰嚮selectUserButton
			linkManListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					String phone = initList().get(position).getNumber();
					Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
					intent.putExtra("selectPhone", phone);
					startActivity(intent);
					MainActivity.this.finish();
					return true;
				}
			});
		}
		addManBtn.setOnClickListener(this);
		lookBackUpUser.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	public List<LinkManModule> initList() {
		//娓呯┖鍐呭瓨锛屽惁鍒欐瘡娆￠噸鏂板埛鏂帮紝閮戒細閲嶆柊鍔犺浇鍐呭瓨涓師鏈夌殑鏁版嵁銆俵istitem鎴愬�嶅鍔�
		lists.clear();
		String name1 = "", phoneNumber1 = "";		
		Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		// moveToNext鏂规硶杩斿洖鐨勬槸涓�涓猙oolean绫诲瀷鐨勬暟鎹�
		while (cursor.moveToNext()) {
			// 璇诲彇閫氳褰曠殑濮撳悕
			name1 = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
			// 璇诲彇閫氳褰曠殑鍙风爜
			phoneNumber1 = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			
			
			
//			//鑾峰彇绯荤粺鑱旂郴浜哄浘鐗�
//			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//			Uri contactUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, Long.parseLong(contactId));
//			//鑱旂郴浜哄浘鐗囪矾寰�
//			Uri photoUri  = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DATA15);
//			//鑾峰緱鑱旂郴浜哄浘鐗囷紝璇诲彇鐨勬槸Data绫讳腑鐨刣ata15瀛楁
//			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
//			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), uri);
//			Bitmap contactPhoto = BitmapFactory.decodeStream(input);
			
			
			
			//鍙彉鎹㈡柊娣诲姞鐨刬tem鐨勫浘鐗囷紝鍏朵粬浠嶆湭榛樿鍥剧墖
			if (!name1.equals(getIntent().getStringExtra("name"))) {
				LinkManModule linkManModule1 = new LinkManModule(R.drawable.pt17, name1, phoneNumber1);
				lists.add(linkManModule1);				
			}
		}		
		//鍏朵粬鐨刬tem鍥剧墖涓嶅彉锛屽彧鍙樺叾涓竴涓�傛墍浠ヨ鎶婅娈典唬鐮佸啓鍦ㄥ杈广��
		if (name1.equals(getIntent().getStringExtra("name"))) {
			userBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("userPicture"));
			if (userBitmap==null) {
				LinkManModule linkManModule = new LinkManModule(R.drawable.pt17, name1, phoneNumber1);
				lists.add(linkManModule);
			} else {
				System.out.println(userBitmap+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				LinkManModule linkManModule = new LinkManModule(name1, phoneNumber1,
						CompressJPEG.compressImage(userBitmap));
				lists.add(linkManModule);			
			}
			
		}
		System.out.println(userBitmap);
		System.out.println(getIntent().getStringExtra("name") + "锛燂紵锛燂紵锛燂紵锛燂紵锛�" + name + "||||||||||||"
				+ getIntent().getStringExtra("userPicture"));
		
		return lists;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.addLinkman) {
			Intent intent = new Intent(MainActivity.this, AddManActivity.class);
			startActivityForResult(intent, 3);
		} else if (v.getId() == R.id.lookBackUpUser) {
				Intent intent = new Intent(MainActivity.this,BackUpActivity.class);
				startActivity(intent);
		}
	}

	public void transmitValue(int position, Intent intent) {
		if (filterFlag) {
			name = initList().get(position).getManName();
			phoneNumber = initList().get(position).getNumber();
			userImage = initList().get(position).getManImag();
		}else {
			name = searchList.get(position).getManName();
			phoneNumber = searchList.get(position).getNumber();
			userImage = searchList.get(position).getManImag();
		}
		
		intent.putExtra("name", name);
		intent.putExtra("phone", phoneNumber);
		intent.putExtra("userImage", userImage);
		System.out.println(name + "UUUUUUUUUUUUU");
	}
	
	TextWatcher filterTextWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String aa = s.toString();
			Pattern pattern = Pattern.compile(aa);			
			for (int i = 0; i < initList().size(); i++) {
				LinkManModule linkManModule = initList().get(i);
				Matcher matcher = pattern.matcher(linkManModule.getManName()
						+ linkManModule.getNumber());
				if (matcher.find()) {
					searchList.add(linkManModule);
				}
			}
			 LinkManAdapter adapter = new LinkManAdapter(searchList,
			 getApplicationContext());
			 linkManListView.setAdapter(adapter);
			 filterFlag = false;
			
		}
	};	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			onCreate(null);
		}		
	};
	Runnable runnable = new Runnable() {		
		@Override
		public void run() {
			//鏈夌綉鎴栬�呮棤缃戯紝涓ょ鎯呭喌涓嬬殑澶囦唤鎿嶄綔銆�
			NetWorkConnect netWorkConnect = new NetWorkConnect(MainActivity.this);
			if (netWorkConnect.isConnectInternet()) {
				TransmitBackupUser  transmitBackupUser  = new TransmitBackupUser();				
				response = transmitBackupUser.submitBackupUser(initList().get(position).getManName(), initList().get(position).getNumber());
			}else {
				BackUpFileWrite backUpFileWrite = new BackUpFileWrite();
				backUpFileWrite.fileWrite(initList().get(position).getManName(), initList().get(position).getNumber());
			}				
			name = initList().get(position).getManName();
			DeleteItem deleteItem = new DeleteItem();
			try {
				deleteItem.deleteListItem(name, MainActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lists.remove(position);
//			linkManAdapter.notifyDataSetChanged();
			if(response.equals("success")) {
				Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
			}		
		}
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onCreate(null);
	}
}
