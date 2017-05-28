package com.shishicai.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shishicai.R;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.adapter.ColdHotAdapter;
import com.shishicai.app.domain.ColdHotBean;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class ColdHotActivity extends Base2Activity implements OnClickListener {
	private TextView title;// 标题信息
	private LinearLayout tab_1, tab_2, tab_3, tab_4, tab_5, tab_6, tab_7, tab_8, tab_9, tab_10;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private GridView hotGv, warmGv, coldGv;
	private int index;
	private ColdHotBean info;
	private List<Integer> hotList, warmList, coldList;
	private ColdHotAdapter hotAdapter, warmAdapter, coldAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cold_hot);
		TAG = "ColdHotActivity";
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		ImageView titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		title.setText("冷热分析");
		tab_1 = (LinearLayout) findViewById(R.id.tab_1);
		tab_2 = (LinearLayout) findViewById(R.id.tab_2);
		tab_3 = (LinearLayout) findViewById(R.id.tab_3);
		tab_4 = (LinearLayout) findViewById(R.id.tab_4);
		tab_5 = (LinearLayout) findViewById(R.id.tab_5);
		tab_6 = (LinearLayout) findViewById(R.id.tab_6);
		tab_7 = (LinearLayout) findViewById(R.id.tab_7);
		tab_8 = (LinearLayout) findViewById(R.id.tab_8);
		tab_9 = (LinearLayout) findViewById(R.id.tab_9);
		tab_10 = (LinearLayout) findViewById(R.id.tab_10);
		tab_1.setOnClickListener(this);
		tab_2.setOnClickListener(this);
		tab_3.setOnClickListener(this);
		tab_4.setOnClickListener(this);
		tab_5.setOnClickListener(this);
		tab_6.setOnClickListener(this);
		tab_7.setOnClickListener(this);
		tab_8.setOnClickListener(this);
		tab_9.setOnClickListener(this);
		tab_10.setOnClickListener(this);
		msgLayout = (LinearLayout) findViewById(R.id.msg_layout);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		hotGv = (GridView) findViewById(R.id.hot_gv);
		warmGv = (GridView) findViewById(R.id.warm_gv);
		coldGv = (GridView) findViewById(R.id.cold_gv);
		hotList = new ArrayList<>();
		warmList = new ArrayList<>();
		coldList = new ArrayList<>();
		hotAdapter = new ColdHotAdapter(this, hotList);
		warmAdapter = new ColdHotAdapter(this, warmList);
		coldAdapter = new ColdHotAdapter(this, coldList);
		hotGv.setAdapter(hotAdapter);
		warmGv.setAdapter(warmAdapter);
		coldGv.setAdapter(coldAdapter);
		tab_1.setSelected(true);
		gainData();
	}
	
	private void gainData() {
		String url = Constant.COLD_HOT_ANALYSIS + "?ball="
				+ index;
		LogUtil.e(TAG, "url=" + url);
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				Toast.makeText(ColdHotActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				info = GsonUtils.parseJSON(str, ColdHotBean.class);
				hotList.addAll(info.getItemArray().get(0));
				warmList.addAll(info.getItemArray().get(1));
				coldList.addAll(info.getItemArray().get(2));
				hotAdapter.notifyDataSetChanged();
				warmAdapter.notifyDataSetChanged();
				coldAdapter.notifyDataSetChanged();
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.tab_1:
				tab_1.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 0;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_2:
				tab_2.setSelected(true);
				tab_1.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 1;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_3:
				tab_3.setSelected(true);
				tab_2.setSelected(false);
				tab_1.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 2;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_4:
				tab_4.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_1.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 3;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_5:
				tab_5.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_1.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 4;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_6:
				tab_6.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_1.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 5;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_7:
				tab_7.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_1.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 6;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_8:
				tab_8.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_1.setSelected(false);
				tab_9.setSelected(false);
				tab_10.setSelected(false);
				index = 7;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_9:
				tab_9.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_1.setSelected(false);
				tab_10.setSelected(false);
				index = 8;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
			case R.id.tab_10:
				tab_10.setSelected(true);
				tab_2.setSelected(false);
				tab_3.setSelected(false);
				tab_4.setSelected(false);
				tab_5.setSelected(false);
				tab_6.setSelected(false);
				tab_7.setSelected(false);
				tab_8.setSelected(false);
				tab_9.setSelected(false);
				tab_1.setSelected(false);
				index = 9;
				if (hotList.size() > 0)
				{
					hotList.clear();
				}
				if (warmList.size() > 0)
				{
					warmList.clear();
				}
				if (coldList.size() > 0)
				{
					coldList.clear();
				}
				gainData();
				break;
		case R.id.title_left:
			ColdHotActivity.this.finish();
			break;
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(this);
	}
}
