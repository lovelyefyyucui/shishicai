package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.utils.Base2Activity;

public class AboutActivity extends Base2Activity implements OnClickListener {

	private TextView title, contentTv;
	private ImageView titleimgLeft; // 标题信息
	private String titleString, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		TAG = "AboutActivity";
		title = (TextView) findViewById(R.id.title_top);
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		contentTv = (TextView) findViewById(R.id.content_tv);
		Intent intent = getIntent();
		titleString = intent.getStringExtra("title");
		content = intent.getStringExtra("content");
		title.setText(titleString);
		contentTv.setText(content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.title_left:
				finish();
				break;
		}
	}



}
