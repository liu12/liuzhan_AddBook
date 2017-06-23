package com.example.addbook.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.addbook.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SelectPicPopupWindow extends Activity implements OnClickListener {
	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private LinearLayout layout;
	private static int RESULT_LOAD_IMAGE = 1;
	private String picturePath;
	private Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialog);

		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);

		layout = (LinearLayout) findViewById(R.id.po_layout);
		layout.setBackgroundResource(R.drawable.background);
		layout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
			}
		});
		// 添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
	}

	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_take_photo:
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, 3);
			} else {
				Toast.makeText(SelectPicPopupWindow.this, "没有SD卡", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btn_pick_photo:
			Intent intent1 = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent1, 1);
			break;
		case R.id.btn_cancel:
//			finish();
			break;
		default:
			break;
		}

	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
//向AddManActivity中传送图片路径
			Intent intent = new Intent();
			intent.putExtra("picturePath", picturePath);
//向AddManActivity中结果码
//void android.app.Activity.setResult(int resultCode, Intent data)
			SelectPicPopupWindow.this.setResult(2, intent);
			finish();
			cursor.close();

		}
//当结果码为3的时候，表示执行拍照功能。
		else if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
			Uri uri = data.getData();
			if (uri != null) {
				this.photo = BitmapFactory.decodeFile(uri.getPath());
			}
			if (this.photo == null) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					this.photo = (Bitmap) bundle.get("data");
				} else {
					Toast.makeText(SelectPicPopupWindow.this, "拍照失败", Toast.LENGTH_LONG).show();
					return;
				}
			}

			FileOutputStream fileOutputStream = null;
			try {
				// 获取 SD 卡根目录
				String saveDir = Environment.getExternalStorageDirectory() + "/meitian_photos";
				// 新建目录
				File dir = new File(saveDir);
				if (!dir.exists())
					dir.mkdir();
				// 生成文件名
				SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
				String filename = "MT" + (t.format(new Date())) + ".jpg";
				// 新建文件
				File file = new File(saveDir, filename);
				// 打开文件输出流
				fileOutputStream = new FileOutputStream(file);
				// 生成图片文件
				this.photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
				// 相片的完整路径
				this.picturePath = file.getPath();

				Intent intent = new Intent();
				intent.putExtra("picturePath", picturePath);
				SelectPicPopupWindow.this.setResult(2, intent);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			finish();
		}

	}

}
