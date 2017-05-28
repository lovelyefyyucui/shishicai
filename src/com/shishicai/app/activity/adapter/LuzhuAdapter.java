package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.LuzhuInfo;
import com.shishicai.app.ui.HorizontialListView;

import java.util.List;

public class LuzhuAdapter extends BaseAdapter {
	private Context context;
	private List<LuzhuInfo.ItemsBean> list;
	private LuzhuListAdapter adapter;

	public LuzhuAdapter(Context context, List<LuzhuInfo.ItemsBean> list)
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
					R.layout.luzhu_listview_item, null);
		TextView nameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
		TextView resultTv = (TextView) convertView
					.findViewById(R.id.result_tv);
		HorizontialListView referLv = (HorizontialListView) convertView.findViewById(R.id.luzhu_lv);
		LuzhuInfo.ItemsBean bean = list.get(position);
		nameTv.setText(bean.getName());
		List<LuzhuInfo.ItemsBean.ExtDataBean> extData = bean.getExtData();
		String name1 = extData.get(0).getName();
		String name2 = extData.get(1).getName();
		resultTv.setText(name1 + "(" + bean.getFCount() + ")" + name2 + "(" + bean.getSCount() + ")");
		adapter = new LuzhuListAdapter(context, bean.getData());
		referLv.setAdapter(adapter);
		return convertView;
	}

	
}
