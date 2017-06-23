package com.example.addbook.backupByFileIO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.addbook.backup_user.BackUpUserModule;

import android.os.Environment;

public class BackUpFileRead {
	static String readContent = "";
	static List<BackUpUserModule> lists = new ArrayList<BackUpUserModule>();

	public static List<BackUpUserModule> fileRead() {
		try {
			FileInputStream fileInputStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/addBook.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((readContent = bufferedReader.readLine()) != null) {			
				String[] contentString = readContent.split("#");
				BackUpUserModule backUpUserModule = new BackUpUserModule();
				backUpUserModule.setName(contentString[0]);
				backUpUserModule.setPhone(contentString[1]);
				backUpUserModule.setDate("2017-6-9");
				System.out.println(readContent+"@@@@@@#$#$##"+contentString.length);
				lists.add(backUpUserModule);
			}
			bufferedReader.close();
			inputStreamReader.close();
			fileInputStream.close();
			return lists;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
}
