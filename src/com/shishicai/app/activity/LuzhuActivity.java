package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.AboutActivity;
import com.shishicai.app.utils.Base2Activity;

public class LuzhuActivity extends Base2Activity implements OnClickListener {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luzhu);
		TAG = "LuzhuActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("路珠");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		findViewById(R.id.longhu_tv).setOnClickListener(this);
		findViewById(R.id.guanyehe_tv).setOnClickListener(this);
		findViewById(R.id.qianhou_tv).setOnClickListener(this);
		findViewById(R.id.daxiao_tv).setOnClickListener(this);
		findViewById(R.id.danshuang_tv).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		String title = "", url = "";
		switch (v.getId())
		{
			case R.id.longhu_tv:
				intent = new Intent(this, LuzhuDetailActivity.class);
				title = "龙虎路珠";
				url = Constant.LONGHU_LUZHU;
				break;

			case R.id.guanyehe_tv:
				intent = new Intent(this, LuzhuDetailActivity.class);
				title = "冠亚和路珠";
				url = Constant.SUM_LUZHU;
				break;

			case R.id.qianhou_tv:
				intent = new Intent(this, LuzhuDetailActivity.class);
				title = "前后路珠";
				url = Constant.CODE_LUZHU;
				break;

			case R.id.daxiao_tv:
				intent = new Intent(this, LuzhuDetailActivity.class);
				title = "大小路珠";
				url = Constant.BIG_SMALL_LUZHU;
				break;
			case R.id.danshuang_tv:
				intent = new Intent(this, LuzhuDetailActivity.class);
				title = "单双路珠";
				url = Constant.ODD_EVEN_LUZHU;
				break;

			case R.id.title_left:
				finish();
				break;
		}
		if (intent != null)
		{
			intent.putExtra("title", title);
			intent.putExtra("url", url);
			startActivity(intent);
		}
	}



}
