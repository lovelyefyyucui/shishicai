package com.shishicai.app.activity;

import com.shishicai.app.utils.Base2Activity;

import com.shishicai.R;
import com.shishicai.app.utils.LogUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToolActivity extends Base2Activity implements OnClickListener {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	/**
	 * 扫描跳转Activity RequestCode
	 */

	private static final int MY_PERMISSIONS_REQUEST = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool);
		TAG = "ToolActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("更多");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		findViewById(R.id.cold_warm_ll).setOnClickListener(this);
		findViewById(R.id.generate_num_ll).setOnClickListener(this);
		findViewById(R.id.news_ll).setOnClickListener(this);
		findViewById(R.id.kefu_ll).setOnClickListener(this);
		findViewById(R.id.scan_ll).setOnClickListener(this);
		findViewById(R.id.about_us_ll).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.cold_warm_ll:
				startActivity(new Intent(this, ColdHotActivity.class));
				break;

			case R.id.generate_num_ll:
				startActivity(new Intent(this, GenerateNumberActivity.class));
				break;
			case R.id.news_ll:
				startActivity(new Intent(this, DayNewsActivity.class));
				break;

			case R.id.kefu_ll:
				startActivity(new Intent(this, ChatActivity.class));
				break;

			case R.id.scan_ll:
				if (Build.VERSION.SDK_INT >= 23)
				{
					LogUtil.e(TAG, "version >= 23");
					if (ContextCompat.checkSelfPermission(this,
							Manifest.permission.CAMERA)
							!= PackageManager.PERMISSION_GRANTED) {
						ActivityCompat.requestPermissions(this,
								new String[]{Manifest.permission.CAMERA},
								MY_PERMISSIONS_REQUEST);
					}else{
						Intent intent = new Intent(this, CaptureActivity.class);
						startActivity(intent);
					}
				}else{
					Intent intent = new Intent(this, CaptureActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.about_us_ll:
				startActivity(new Intent(this, AboutAsActivity.class));
				break;
			case R.id.title_left:
				finish();
				break;
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MY_PERMISSIONS_REQUEST)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				Intent intent = new Intent(this, CaptureActivity.class);
				startActivity(intent);
			} else
			{
				// Permission Denied
				Toast.makeText(this, "权限被禁止，无法打开相机", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
