package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.NumberBean;

import java.util.List;

public class AwardStatisticsAdapter extends BaseAdapter {
	private Context context;
	private List<NumberBean> list;
	public AwardStatisticsAdapter(Context context, List<NumberBean> list)
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
		TextView content, content1, content2, content3, content4, content5, content6;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.pkshi_statistics_item, null);
			holder.content = (TextView) convertView.findViewById(R.id.msg_one);
			holder.content1 = (TextView) convertView.findViewById(R.id.msg_two);
			holder.content2 = (TextView) convertView.findViewById(R.id.msg_three);
			holder.content3 = (TextView) convertView.findViewById(R.id.msg_four);
			holder.content4 = (TextView) convertView.findViewById(R.id.msg_five);
			holder.content5 = (TextView) convertView.findViewById(R.id.msg_six);
			holder.content6 = (TextView) convertView.findViewById(R.id.msg_seven);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		NumberBean info = list.get(position);
		int number = info.getNo();
		holder.content.setText(number + "");
		holder.content.setBackgroundResource(getBackGround(number));
		holder.content1.setText(info.getAppearrate());
		holder.content2.setText(info.getCurrent_omit() + "");
		holder.content3.setText(info.getLast_time_omit() + "");
		holder.content4.setText(info.getHistory_max_omit() + "");
		holder.content5.setText(info.getAver_omit() + "");
		holder.content6.setText(info.getWishrate());
		return convertView;
	}
	

	private int getBackGround(int num)
	{
		int res = 0;
		switch (num)
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
			case 11:
				res = R.drawable.ball_one;
				break;
			case 12:
				res = R.drawable.ball_two;
				break;
			case 13:
				res = R.drawable.ball_three;
				break;
			case 14:
				res = R.drawable.ball_four;
				break;
			case 15:
				res = R.drawable.ball_five;
				break;
			case 16:
				res = R.drawable.ball_six;
				break;
			case 17:
				res = R.drawable.ball_seven;
				break;
			case 18:
				res = R.drawable.ball_eight;
				break;
			case 19:
				res = R.drawable.ball_nine;
				break;
		}
		return res;
	}
	
}
