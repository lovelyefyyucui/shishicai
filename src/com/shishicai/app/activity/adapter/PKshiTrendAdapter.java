package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.PKshiInfo;

import java.util.List;

public class PKshiTrendAdapter extends BaseAdapter {
	private Context context;
	private List<PKshiInfo.RootBean.Pk10Bean> list;
	private int index;
	public PKshiTrendAdapter(Context context, List<PKshiInfo.RootBean.Pk10Bean> list)
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
		TextView msgTitle, time, content, content1, content2, content3, content4, content5, content6, content7, content8, content9;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.pkshi_trend_item, null);
			holder.msgTitle = (TextView) convertView
					.findViewById(R.id.msg_title);
			holder.content = (TextView) convertView.findViewById(R.id.msg_one);
			holder.content1 = (TextView) convertView.findViewById(R.id.msg_two);
			holder.content2 = (TextView) convertView.findViewById(R.id.msg_three);
			holder.content3 = (TextView) convertView.findViewById(R.id.msg_four);
			holder.content4 = (TextView) convertView.findViewById(R.id.msg_five);
			holder.content5 = (TextView) convertView.findViewById(R.id.msg_six);
			holder.content6 = (TextView) convertView.findViewById(R.id.msg_seven);
			holder.content7 = (TextView) convertView.findViewById(R.id.msg_eight);
			holder.content8 = (TextView) convertView.findViewById(R.id.msg_nine);
			holder.content9 = (TextView) convertView.findViewById(R.id.msg_ten);
			holder.time = (TextView) convertView
					.findViewById(R.id.msg_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final Holder finalHolder = holder;
		PKshiInfo.RootBean.Pk10Bean info = list.get(position);
		finalHolder.msgTitle.setText(info.getIssue() + "");
		String[] numbers = info.getNums().split(" ");
		int num = Integer.valueOf(numbers[index]);
		switch (num)
		{
			case 1:
				finalHolder.content.setBackgroundResource(R.drawable.ball_one);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 2:
				finalHolder.content1.setBackgroundResource(R.drawable.ball_two);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 3:
				finalHolder.content2.setBackgroundResource(R.drawable.ball_three);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 4:
				finalHolder.content3.setBackgroundResource(R.drawable.ball_four);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 5:
				finalHolder.content4.setBackgroundResource(R.drawable.ball_five);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 6:
				finalHolder.content5.setBackgroundResource(R.drawable.ball_six);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 7:
				finalHolder.content6.setBackgroundResource(R.drawable.ball_seven);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 8:
				finalHolder.content7.setBackgroundResource(R.drawable.ball_eight);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 9:
				finalHolder.content8.setBackgroundResource(R.drawable.ball_nine);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				finalHolder.content9.setBackgroundResource(R.drawable.shape);
				break;
			case 10:
				finalHolder.content9.setBackgroundResource(R.drawable.ball_ten);
				finalHolder.content1.setBackgroundResource(R.drawable.shape);
				finalHolder.content2.setBackgroundResource(R.drawable.shape);
				finalHolder.content3.setBackgroundResource(R.drawable.shape);
				finalHolder.content4.setBackgroundResource(R.drawable.shape);
				finalHolder.content5.setBackgroundResource(R.drawable.shape);
				finalHolder.content6.setBackgroundResource(R.drawable.shape);
				finalHolder.content7.setBackgroundResource(R.drawable.shape);
				finalHolder.content8.setBackgroundResource(R.drawable.shape);
				finalHolder.content.setBackgroundResource(R.drawable.shape);
				break;
		}
		String timeStr = info.getTime();
		timeStr = timeStr.substring(timeStr.indexOf(" "));
		finalHolder.time.setText(timeStr);
		return convertView;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
