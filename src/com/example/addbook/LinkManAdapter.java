package com.example.addbook;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import junit.framework.Test;

public class LinkManAdapter extends BaseAdapter implements Filterable {
	private List<LinkManModule> lists;
	private Context context;
	private LinkManFilter filter;

	public LinkManAdapter(List<LinkManModule> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;

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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder;
		if (convertView != null) {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		} else {
			view = View.inflate(context, R.layout.linkman_listitem, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view.findViewById(R.id.linkManImage);
			viewHolder.textNumberView = (TextView) view.findViewById(R.id.number);
			viewHolder.textView = (TextView) view.findViewById(R.id.linkManName);
			view.setTag(viewHolder);
		}
		if (lists.get(position).getUserBitmap() != null) {
			viewHolder.imageView.setImageBitmap(lists.get(position).getUserBitmap());
			System.out.println("GOOD Method！！！！");
		} else {
			viewHolder.imageView.setImageResource(lists.get(position).getManImag());
		}
		viewHolder.textView.setText(lists.get(position).getManName());
		viewHolder.textNumberView.setText(lists.get(position).getNumber());
		return view;
	}

	public class ViewHolder {
		ImageView imageView;
		TextView textView;
		TextView textNumberView;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (filter == null) {
			filter = new LinkManFilter(lists);
		}
		return filter;
	}

	private class LinkManFilter extends Filter {
		private List<LinkManModule> originLists;

		public LinkManFilter(List<LinkManModule> originLists) {
			super();
			this.originLists = originLists;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				results.values = originLists;
				results.count = originLists.size();
			} else {
				List<LinkManModule> mList = new ArrayList<LinkManModule>();
				for (LinkManModule p : originLists) {
					if (p.getManName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
						mList.add(p);
					}
				}
				results.values = mList;
				results.count = mList.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// TODO Auto-generated method stub
			lists = (List<LinkManModule>)results.values;  
            notifyDataSetChanged();
		}

	}
}
