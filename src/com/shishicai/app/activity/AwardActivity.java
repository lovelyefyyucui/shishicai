package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.activity.adapter.AwardAdapter;
import com.shishicai.app.domain.AwardInfo;
import com.shishicai.app.domain.DataBean;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class AwardActivity extends Base2Activity implements View.OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private ImageView titleimgLeft;
	private TextView title;
	private List<DataBean> list;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private PullToRefreshView mRefreshView;
	private AwardAdapter awardAdapter;
	private String link;
	private String titleString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.award_activity);
		TAG = "AwardActivity";
		title = (TextView) findViewById(R.id.title_top);
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		msgLayout = (LinearLayout) findViewById(R.id.msg_layout);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		final Intent intent = getIntent();
		titleString = intent.getStringExtra("title");
		link = intent.getStringExtra("link");
		LogUtil.e(TAG, "link=" + link);
		title.setText(titleString + "历史开奖");
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		list = new ArrayList<DataBean>();
		gainData();
	}


	private void gainData() {
		HttpUser.get(link, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				Toast.makeText(AwardActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				AwardInfo info = null;
				try {
					info = GsonUtils.parseJSON(str, AwardInfo.class);
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
				if (info != null){
					if (info.getData() != null && info.getData().size() > 0) {
						list =info.getData();
						msgLayout.setVisibility(View.VISIBLE);
						linear.setVisibility(View.GONE);
						awardAdapter = new AwardAdapter(AwardActivity.this, list);
						listArticle.setAdapter(awardAdapter);
					}
				}else {
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					Toast.makeText(AwardActivity.this, "暂无数据!", Toast.LENGTH_SHORT).show();
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
		list.clear();
		gainData();
		mRefreshView.onHeaderRefreshComplete();
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
