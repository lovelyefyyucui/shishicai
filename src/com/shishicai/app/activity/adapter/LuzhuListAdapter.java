package com.shishicai.app.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.domain.LuzhuInfo;

import java.util.List;

public class LuzhuListAdapter extends BaseAdapter {
	private Context context;
	private List<LuzhuInfo.ItemsBean.DataBeanX> list;
	public LuzhuListAdapter(Context context, List<LuzhuInfo.ItemsBean.DataBeanX> list)
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
		TextView nameOddTv, nameTv;
		LinearLayout nameOddLl, nameLl;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.tv_item, null);
			holder.nameOddLl = (LinearLayout) convertView.findViewById(R.id.name_ll_odd);
			holder.nameLl = (LinearLayout) convertView.findViewById(R.id.name_ll);
			holder.nameOddTv = (TextView) convertView.findViewById(R.id.name_tv_odd);
			holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		LuzhuInfo.ItemsBean.DataBeanX info = list.get(position);
		List<LuzhuInfo.ItemsBean.DataBeanX.DataBean> data = info.getData();
		StringBuilder sb = new StringBuilder();
		for (LuzhuInfo.ItemsBean.DataBeanX.DataBean bean : data)
		{
			sb.append(bean.getResult());
		}
		holder.nameTv.setText(sb.toString());
		holder.nameOddTv.setText(sb.toString());
		if (position % 2 != 0)
		{
			holder.nameOddLl.setVisibility(View.VISIBLE);
			holder.nameLl.setVisibility(View.GONE);
		}else {
			holder.nameOddLl.setVisibility(View.GONE);
			holder.nameLl.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
}
