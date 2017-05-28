package com.shishicai.app.utils;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.shishicai.R;

import com.shishicai.app.Constant;

public class TreeViewAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private LayoutInflater inflater1;
	private List<Map<String, String>> group;
	private List<List<Map<String,String>>> child;
	public Context mContext;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	private com.nostra13.universalimageloader.core.ImageLoader uniLoad;

	public TreeViewAdapter(Context c, DisplayImageOptions options, ImageLoadingListener animateFirstListener, com.nostra13.universalimageloader.core.ImageLoader uniLoad, List<Map<String, String>> group, List<List<Map<String,String>>> child) {
		this.inflater = LayoutInflater.from(c);
		this.inflater1 = LayoutInflater.from(c);
		this.options = options;
		this.animateFirstListener = animateFirstListener;
		this.uniLoad = uniLoad;
		this.group = group;
		this.child = child;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub

		return child.get(groupPosition).get(childPosition).get("name");
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View childView = inflater1.inflate(R.layout.commodity_child_item, null);
		TextView textview = (TextView) childView.findViewById(R.id.item_child);
		textview.setText(getChild(groupPosition, childPosition).toString());
		return childView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition).get("imageurl");
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return child.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View parentView = inflater.inflate(R.layout.commodity_item, null);
		ImageView icon = (ImageView) parentView.findViewById(R.id.item_icon);
		TextView title = (TextView) parentView.findViewById(R.id.item_title);
		uniLoad.displayImage(Constant.URLDomain + "/" + getGroup(groupPosition).toString(), icon, options,
				animateFirstListener);
		title.setText(group.get(groupPosition).get("name"));
		return parentView;
	}
	

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}	
}
