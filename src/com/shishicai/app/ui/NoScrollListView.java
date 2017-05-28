package com.shishicai.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 全部展示,不需要滑动
 * 
 * @author Lee
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int spec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, spec);
	}

}
