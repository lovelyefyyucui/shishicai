package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.CategoryInfo;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {
	private Context context;
	private List<CategoryInfo> list;
	public CategoryListAdapter(Context context, List<CategoryInfo> list)
	{
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class Holder {
		TextView tv;
		ImageView iv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.listview_item, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_text);
			holder.iv = (ImageView) convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		CategoryInfo info = list.get(position);
		holder.iv.setImageResource(info.getImgID());
		holder.tv.setText(info.getName());
		return convertView;
	}
	
}
