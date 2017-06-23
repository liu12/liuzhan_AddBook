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
/**通过复制除某个name之外的选项，然后替换原来的文本文件实现删除某一项的功能*/
public class DeleteFileItem {
	private String oldContent = " ";
	private List<BackUpUserModule> lists = new ArrayList<BackUpUserModule>();
	
	
	public List<BackUpUserModule> delete(String name) {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/addBook.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);		
			while ((oldContent = bufferedReader.readLine()) != null) {
				BackUpUserModule backUpUserModule = new BackUpUserModule();
				String[] oldContents = oldContent.split("#");
				if (!oldContents[0] .equals(name) ) {
					backUpUserModule.setName(oldContents[0]);
					backUpUserModule.setPhone(oldContents[1]);
					backUpUserModule.setDate("2017-6-9");
					lists.add(backUpUserModule);
				}
			}
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
	public void replaceOldFile(List<BackUpUserModule> lists) {
		System.out.println(lists.size());	
		for (int i=0;i<lists.size();i++) {				
			BackUpFileWrite.fileWriteCanReplace(lists.get(i).getName(),lists.get(i).getPhone());
			System.out.println(lists.get(i).getName()+"%%%%%%%%%%********");
		}
	}
}
