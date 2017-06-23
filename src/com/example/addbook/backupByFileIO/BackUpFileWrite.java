package com.example.addbook.backupByFileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.PublicKey;

import android.os.Environment;
import android.text.StaticLayout;

public class BackUpFileWrite {

	public static void fileWrite(String name, String phone) {
		CreateFile createFile = new CreateFile();
		createFile.makeFile();
		// 文件路径
		String addBookPath = createFile.path;
		System.out.println(addBookPath + "&&&&&&&$$$$$$$$$$$");
		// 写入文件的内容
		String content = name + "#" + phone + "\r\n";
		File file = new File(addBookPath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			/*
			 * {@code "rwd"}</td> <td>The file is opened for reading and
			 * writing. Every change of the file's content must be written
			 * synchronously to the target device.
			 */
			// 使用RandomAccessFile是在原有的文件基础之上追加内容，
			// 而使用outputstream则是要先清空内容再写入
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
			/*
			 * Moves this file's file pointer to a new position, from where
			 * following {@code read}, {@code write} or {@code skip} operations
			 * are done
			 */
			randomAccessFile.seek(file.length());
			randomAccessFile.write(content.getBytes());
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile() {
		CreateFile createFile = new CreateFile();
		// 文件路径
		String addBookPath = createFile.path;
		File file = new File(addBookPath);
		if (file.exists()) {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(file);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write("");
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void fileWriteCanReplace(String name,String phone) {
//		CreateFile createFile = new CreateFile();
//		createFile.makeFile();
		// 文件路径
		String addBookPath = Environment.getExternalStorageDirectory().getPath() + "/addBook.txt";
		System.out.println(addBookPath + "&&&&&&&$$$$$$$$$$$");
		// 写入文件的内容
		String content = name + "#" + phone + "\r\n";
		File file = new File(addBookPath);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 为false则替换原有文件中的内容
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(file, true);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
