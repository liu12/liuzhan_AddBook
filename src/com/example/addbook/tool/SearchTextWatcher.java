package com.example.addbook.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.example.addbook.LinkManAdapter;
import com.example.addbook.LinkManModule;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;

public class SearchTextWatcher implements TextWatcher{
	private ListView listView;
	private List<LinkManModule> list;
	private Context context;
	public SearchTextWatcher(ListView listView, List<LinkManModule> list,Context context) {
		super();
		this.listView = listView;
		this.list = list;
		this.context = context;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		this.beforeTextChanged(null, 0, 0, 0);
		;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		String aa = s.toString();
		Pattern pattern = Pattern.compile(aa);
		List<LinkManModule> searchList = new ArrayList<LinkManModule>();
		for (int i = 0; i < list.size(); i++) {
			LinkManModule linkManModule = list.get(i);
			Matcher matcher = pattern.matcher(linkManModule.getManName()
					+ linkManModule.getNumber());
			if (matcher.find()) {
				searchList.add(linkManModule);
			}
		}
		LinkManAdapter linkManAdapter = new LinkManAdapter(searchList,context);
		 listView.setAdapter(linkManAdapter);
		 System.out.println("???????????KKKKKKKKKKKKK");
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		this.afterTextChanged(null);
	}

}
