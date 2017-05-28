package com.shishicai.app.activity.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CategoryPagerAdapter extends PagerAdapter {
	private List<View> mViews;

	public CategoryPagerAdapter(List<View> mViews) {
		this.mViews = mViews;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViews.get(position));
		return mViews.get(position);
	}

}
