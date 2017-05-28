package com.shishicai.app.activity;

import com.shishicai.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.shishicai.app.ui.DragImageView;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.LogUtil;

public class ShowImgActivity extends Base2Activity {
	private int window_width, window_height;// 控件宽度
	private DragImageView dragImageView;// 自定义控件
	private int state_height;// 状态栏的高度

	private ViewTreeObserver viewTreeObserver;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_img);
		TAG = "ShowImgActivity";
		/** 获取可見区域高度 **/
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();

		dragImageView = (DragImageView) findViewById(R.id.div_main);
		String path = getIntent().getStringExtra("path");
		LogUtil.e(TAG, "path=" + path);
		Bitmap bmp =BitmapFactory.decodeFile(path);  //ImageUtil.getBitmap(path);
		// 设置图片
		dragImageView.setImageBitmap(bmp);
		dragImageView.setmActivity(this);//注入Activity.
		/** 测量状态栏高度 **/
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							// 获取状况栏高度
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H((window_height-state_height));
							dragImageView.setScreen_W(window_width);
						}

					}
				});
	}

}
