package com.example.addbook.backup_user;

import java.util.List;

import com.example.addbook.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BackUpAdapter extends BaseAdapter {
	private Context context;
	private List<BackUpUserModule> lists;
	
	public BackUpAdapter(Context context, List<BackUpUserModule> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(context, R.layout.backup_item, null);
		TextView nameText = (TextView)convertView.findViewById(R.id.name);
		TextView phoneText = (TextView)convertView.findViewById(R.id.phone);
		TextView dateText = (TextView)convertView.findViewById(R.id.date);
		
		nameText.setText(lists.get(position).getName());
		phoneText.setText(lists.get(position).getPhone());
		dateText.setText(lists.get(position).getDate());
		
		return convertView;
	}

}
