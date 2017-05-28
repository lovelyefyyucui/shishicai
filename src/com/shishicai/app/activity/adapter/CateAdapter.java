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

public class CateAdapter extends BaseAdapter {
	private Context context;
	private List<CategoryInfo> list;
	public CateAdapter(Context context, List<CategoryInfo> list)
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
		TextView titleTv, subTitleTv;
		ImageView iv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.grid_item, null);
			holder.titleTv = (TextView) convertView.findViewById(R.id.title);
			holder.subTitleTv = (TextView) convertView.findViewById(R.id.sub_title);
			holder.iv = (ImageView) convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		CategoryInfo info = list.get(position);
		holder.iv.setImageResource(info.getImgID());
		holder.titleTv.setText(info.getName());
		holder.subTitleTv.setText(info.getUrl());
		return convertView;
	}

}
