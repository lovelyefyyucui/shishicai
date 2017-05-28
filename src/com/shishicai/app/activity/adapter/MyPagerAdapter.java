package com.shishicai.app.activity.adapter;

import java.util.List;

import com.shishicai.app.activity.WebviewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
/**
 * 填充ViewPager页面的适配器
 * 
 * @author Administrator
 * 
 */

public class MyPagerAdapter extends PagerAdapter
{
	private Activity activity;
	private List<View> imgList;
	private List<String> adLink, adText;
	
	public MyPagerAdapter(Activity activity, List<View> imgList, List<String> adLink, List<String> adText){
		this.activity = activity;
		this.imgList = imgList;
		this.adLink = adLink;
		this.adText = adText;
	}
	
	@Override
	public int getCount() {
		return imgList.size();
	}

	@Override
	public Object instantiateItem(View arg0, final int arg1) {			
		if (imgList.get(arg1).getParent() == null) {
			((ViewPager) arg0).addView(imgList.get(arg1));
		} else {
			((ViewGroup) imgList.get(arg1).getParent()).removeView(imgList.get(arg1));
			((ViewPager) arg0).addView(imgList.get(arg1));
		}
		
		//viewpager的图片的点击事件
		View view  = imgList.get(arg1 % imgList.size());
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adLink.size() > 0) {
					Intent intent = new Intent(activity, WebviewActivity.class);
					String link = adLink.get(arg1);
					intent.putExtra("link", link);
					String title = adText.get(arg1);
					intent.putExtra("title", title);
					activity.startActivity(intent);
				}
			}
		});
		return imgList.get(arg1 % imgList.size());
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(imgList.get(arg1%imgList.size()));
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	@Override
	public void finishUpdate(View arg0) {

	}

}
