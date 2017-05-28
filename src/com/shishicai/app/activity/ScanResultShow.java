package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.LogUtil;

public class ScanResultShow extends Base2Activity {

	private TextView title, tvScanInfo;
	private ImageView titleimgLeft;
	private String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_result);
		TAG = "ScanResultShow";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("扫描结果");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		tvScanInfo = (TextView) findViewById(R.id.scanInforTvResult);
		Intent intent = getIntent();
		info = intent.getStringExtra("result");
		LogUtil.e(TAG, "info=" + info);
		tvScanInfo.setText(info);
	}
}
