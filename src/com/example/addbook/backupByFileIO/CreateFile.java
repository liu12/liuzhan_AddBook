package com.example.addbook.backupByFileIO;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class CreateFile {
	public String path;
	public CreateFile() {
		path = Environment.getExternalStorageDirectory().getPath()+"/addBook.txt";
	}
	public void makeFile() {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
