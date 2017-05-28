package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.db.dao.MsgInfo;
import com.shishicai.app.domain.DataBean;
import com.shishicai.app.utils.DateUtil;

import java.util.Date;
import java.util.List;

public class AwardAdapter extends BaseAdapter {
	private Context context;
	private List<DataBean> list;
	public AwardAdapter(Context context, List<DataBean> list)
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
		TextView msgTitle, time, content;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.award_item, null);
			holder.msgTitle = (TextView) convertView
					.findViewById(R.id.msg_title);
			holder.content = (TextView) convertView
					.findViewById(R.id.msg_author);
			holder.time = (TextView) convertView
					.findViewById(R.id.msg_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		DataBean info = list.get(position);
		holder.msgTitle.setText("第" + info.getExpect() + "期");
		String code = info.getOpencode();
		holder.content.setText(code.replace(",", " "));
		holder.time.setText(info.getOpentime());
		return convertView;
	}
	

	
}
