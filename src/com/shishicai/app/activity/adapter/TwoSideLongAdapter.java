package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.DayNews;
import com.shishicai.app.utils.ImageLoaderUtils;

import java.util.List;

public class TwoSideLongAdapter extends BaseAdapter {
	private Context context;
	private List<List<String>> list;
	public TwoSideLongAdapter(Context context, List<List<String>> list)
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
		TextView categoryTv, twoSideCategoryTv, openedResultTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.two_side_long_item, null);
			holder.categoryTv = (TextView) convertView
					.findViewById(R.id.category_tv);
			holder.twoSideCategoryTv = (TextView) convertView
					.findViewById(R.id.two_side_category_tv);
			holder.openedResultTv = (TextView) convertView
					.findViewById(R.id.opened_result_tv);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		List<String> items = list.get(position);
		String[] categorys = items.get(0).split(",");
		holder.categoryTv.setText(categorys[0]);
		holder.twoSideCategoryTv.setText(categorys[1]);
		holder.openedResultTv.setText(items.get(1));
		return convertView;
	}
	

	
}
