package com.shishicai.app.ui;

import com.shishicai.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class SharePopupWindow extends PopupWindow {

	public SharePopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View mMenuView = inflater.inflate(R.layout.font_item, null);
		mMenuView.findViewById(R.id.font1).setOnClickListener(itemsOnClick);
		mMenuView.findViewById(R.id.font2).setOnClickListener(itemsOnClick);
		mMenuView.findViewById(R.id.font3).setOnClickListener(itemsOnClick);
		setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明，点击back退出pop
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.popup).getTop();
				int right = mMenuView.findViewById(R.id.popup).getRight();
				int y = (int) event.getY();
				int x = (int) event.getX();
				if(event.getAction() == MotionEvent.ACTION_UP){
					if(y > height || x < right){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

}
