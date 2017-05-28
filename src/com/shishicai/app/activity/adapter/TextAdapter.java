package com.shishicai.app.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shishicai.app.domain.ListData;
import com.shishicai.R;



public class TextAdapter  extends BaseAdapter{
	private List<ListData> lists;
	private Context  mContext;
	private RelativeLayout layout;
	
	
	public TextAdapter(List<ListData> lists, Context mContext) {
	
		this.lists = lists;
		this.mContext = mContext;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater  inflater=LayoutInflater.from(mContext);
		if(lists.get(position).getFlag()==ListData.receiver){
			layout=(RelativeLayout) inflater.inflate(R.layout.leftitem, null);
		}
		if(lists.get(position).getFlag()==ListData.send){
			layout=(RelativeLayout) inflater.inflate(R.layout.rightem, null);
		}
		TextView tv = (TextView) layout.findViewById(R.id.tv);
//		TextView time= (TextView) layout.findViewById(R.id.time);
//		time.setText(lists.get(position).getTime());
		tv.setText(lists.get(position).getContent());
		return layout;
	}

}
