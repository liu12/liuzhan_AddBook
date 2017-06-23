package com.example.addbook.tool;

import java.util.List;

import com.example.addbook.LinkManAdapter;
import com.example.addbook.LinkManModule;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Data;


public class DeleteItem {

	public void deleteListItem( String name,Context context)throws Exception{
	    
	    //根据姓名求id
	    Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
	    ContentResolver resolver = context.getContentResolver();
	    Cursor cursor = resolver.query(uri, new String[]{Data._ID},"display_name=?", new String[]{name}, null);
	    if(cursor.moveToFirst()){
	        int id = cursor.getInt(0);
	        //根据id删除data中的相应数据
	        resolver.delete(uri, "display_name=?", new String[]{name});
	        uri = Uri.parse("content://com.android.contacts/data");
	        resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
	    }
	    System.out.println("???????????????++++++++");
	    
	}

}
