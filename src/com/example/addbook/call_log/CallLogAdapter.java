package com.example.addbook.call_log;

import java.util.List;

import com.example.addbook.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class CallLogAdapter extends BaseAdapter {
	private Context context;
	private List<CallLogModule> lists;
	
	public CallLogAdapter(Context context, List<CallLogModule> lists) {
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
		convertView = View.inflate(context, R.layout.call_log_item, null);
		TextView callName = (TextView)convertView.findViewById(R.id.callName);
		TextView callPhone = (TextView)convertView.findViewById(R.id.callPhone);
		TextView callDuration = (TextView)convertView.findViewById(R.id.callDuration);
		TextView callDate = (TextView)convertView.findViewById(R.id.callDate);
		TextView callType = (TextView)convertView.findViewById(R.id.callType);
		
		callName.setText(lists.get(position).getCallName());
		callPhone.setText(lists.get(position).getCallPhone());
		callDuration.setText(String.valueOf(lists.get(position).getCallDuration()));
		callDate.setText(lists.get(position).getCallDate());
		callType.setText(lists.get(position).getCallType());
		
		return convertView;
	}

}
