package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shishicai.R;
import com.shishicai.app.domain.AwardReferInfo;
import com.shishicai.app.ui.NoScrollListView;

import java.util.List;

public class AwardReferAdapter extends BaseAdapter {
	private Context context;
	private List<AwardReferInfo.ItemArrayBean> list;
	private ReferListAdapter adapter;

	public AwardReferAdapter(Context context, List<AwardReferInfo.ItemArrayBean> list)
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


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context,
					R.layout.refer_listview_item, null);
		TextView timeTv = (TextView) convertView
					.findViewById(R.id.time_tv);
		TextView expertTv = (TextView) convertView
					.findViewById(R.id.expert_tv);
		TextView resultTv = (TextView) convertView
					.findViewById(R.id.result_tv);
		NoScrollListView referLv = (NoScrollListView) convertView.findViewById(R.id.refer_lv);
		AwardReferInfo.ItemArrayBean bean = list.get(position);
		timeTv.setText(bean.getDate());
		expertTv.setText(bean.getPeriod() + "期");
		if (TextUtils.isEmpty(bean.getCode()))
		{
			resultTv.setText("投注参考");
		}else
		{
			resultTv.setText("开奖结果:" + bean.getCode());
		}
		adapter = new ReferListAdapter(context, bean.getData());
		referLv.setAdapter(adapter);
		return convertView;
	}

	
}
