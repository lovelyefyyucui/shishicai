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
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Activitydetailpopup extends PopupWindow {
	private View mMenuView;
	/** 退出分享按钮 */
	private Button share_cancel;
	private TextView title,begin,end,addr,fee,point;
	
	public Activitydetailpopup(Activity context,String titlestr,String beginstr,String endstr,String addrstr,String feestr,String pointstr) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.detail_pop, null);
		share_cancel = (Button) mMenuView.findViewById(R.id.popcancel);
		title = (TextView) mMenuView.findViewById(R.id.acttitle);
		begin = (TextView) mMenuView.findViewById(R.id.actbegin);
		end = (TextView) mMenuView.findViewById(R.id.actend);
		addr = (TextView) mMenuView.findViewById(R.id.actaddr);
		fee = (TextView) mMenuView.findViewById(R.id.actfee);
		point = (TextView) mMenuView.findViewById(R.id.actpoint);
		//设置按钮监听
		share_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		title.setText(titlestr);
		begin.setText(beginstr);
		end.setText(endstr);
		addr.setText(addrstr);
		fee.setText(feestr);
		point.setText(pointstr);
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		ColorDrawable colorDrawable = new ColorDrawable(80000000);
		this.setBackgroundDrawable(colorDrawable);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.actpopup).getTop();
				int y=(int) event.getY();
				if(event.getAction() == MotionEvent.ACTION_UP){
					if(y < height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

}
