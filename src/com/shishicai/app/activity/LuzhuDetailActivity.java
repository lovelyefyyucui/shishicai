package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.adapter.AwardReferAdapter;
import com.shishicai.app.activity.adapter.LuzhuAdapter;
import com.shishicai.app.domain.AwardReferInfo;
import com.shishicai.app.domain.LuzhuInfo;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.MyListView;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class LuzhuDetailActivity extends Base2Activity implements OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener  {
	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private PullToRefreshView mRefreshView;
	private List<LuzhuInfo.ItemsBean> list;
	private MyListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private LuzhuAdapter adapter;
	private String titleString;
	private String  url;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luzhu_detail);
		TAG = "LuzhuDetailActivity";
		title = (TextView) findViewById(R.id.title_top);
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		msgLayout = (LinearLayout) findViewById(R.id.msg_layout);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView1);
		listArticle = (MyListView) findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		Intent intent = getIntent();
		titleString = intent.getStringExtra("title");
		title.setText(titleString);
		url = intent.getStringExtra("url");
		list = new ArrayList<>();
		adapter = new LuzhuAdapter(this, list);
		listArticle.setAdapter(adapter);
		gainData();
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


	private void gainData() {
		LogUtil.e(TAG, "url=" + url);
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				Toast.makeText(LuzhuDetailActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}


			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				LuzhuInfo info = null;
				try {
					info = GsonUtils.parseJSON(str, LuzhuInfo.class);
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
				if (info != null && info.getItems() != null && info.getItems().size() > 0) {
					list.addAll(info.getItems());
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				}else {
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					Toast.makeText(LuzhuDetailActivity.this, "暂无数据!", Toast.LENGTH_SHORT).show();
				}
			}

		});

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		list.clear();
		gainData();
		mRefreshView.onHeaderRefreshComplete();
	}
}
