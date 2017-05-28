package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shishicai.R;

import java.util.List;

public class ColdHotAdapter extends BaseAdapter {
	private Context context;
	private List<Integer> list;

	public ColdHotAdapter(Context context, List<Integer> list)
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
		TextView msgNum;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.cold_hot_item, null);
			holder.msgNum = (TextView) convertView
					.findViewById(R.id.msg_num);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		int number = list.get(position);
		holder.msgNum.setText(number + "");
		holder.msgNum.setBackgroundResource(getBackGround(number));
		return convertView;
	}
	

	private int getBackGround(int number)
	{
		int res = 0;
		switch (number)
		{
			case 1:
				res = R.drawable.ball_one;
				break;
			case 2:
				res = R.drawable.ball_two;
				break;
			case 3:
				res = R.drawable.ball_three;
				break;
			case 4:
				res = R.drawable.ball_four;
				break;
			case 5:
				res = R.drawable.ball_five;
				break;
			case 6:
				res = R.drawable.ball_six;
				break;
			case 7:
				res = R.drawable.ball_seven;
				break;
			case 8:
				res = R.drawable.ball_eight;
				break;
			case 9:
				res = R.drawable.ball_nine;
				break;
			case 10:
				res = R.drawable.ball_ten;
				break;
		}
		return res;
	}
	
}
