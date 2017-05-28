package com.shishicai.app.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.RotateAnimation;

import com.shishicai.R;

public class MyProgressDialog extends Dialog {
	static RotateAnimation anim;
	public boolean isCommiting = false;

	// public boolean searchEnable = false;

	public MyProgressDialog(Context context) {
		super(context);
		getContext().setTheme(R.style.dialogProgress);
		// TODO Auto-generated constructor stub
		View v = LayoutInflater.from(context).inflate(R.layout.progress_dialog,
				null);
		this.addContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// mContextText.setText(R.string.loading_msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (isCommiting) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
