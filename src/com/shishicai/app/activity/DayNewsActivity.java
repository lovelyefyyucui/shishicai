package com.shishicai.app.activity;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.app.activity.adapter.NewsAdapter;
import com.shishicai.app.db.dao.NewsInfo;
import com.shishicai.app.domain.BaseListModel;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.DayNews;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.Base2Activity;

import com.shishicai.R;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DayNewsActivity extends Base2Activity implements AdapterView.OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

	private TextView title;
	private PullToRefreshView mRefreshView;
	private List<DayNews> list;
	private List<DayNews> listPage;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private NewsAdapter newsAdapter;
	private int mCurrentPage = 1;
	private int mTotalPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_activity);
		TAG = "DayNewsActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("资讯");
		ImageView titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		msgLayout = (LinearLayout) findViewById(R.id.msg_layout);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) findViewById(R.id.msg_listview);
		linear = (LinearLayout) findViewById(R.id.msg_load);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		listArticle.setOnItemClickListener(this);
		list = new ArrayList<DayNews>();
		listPage = new ArrayList<DayNews>();
		newsAdapter = new NewsAdapter(this, listPage);
		listArticle.setAdapter(newsAdapter);
		gainData();
	}


	private void gainData() {
		String url = Constant.URLNEWS;
		LogUtil.e(TAG, "url=" + url);
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				List<NewsInfo> list1 = DataSupport.findAll(NewsInfo.class);
				if (list1 != null && list1.size() > 0)
				{
					for (int i = 0; i < list1.size(); i++) {
						NewsInfo news = list1.get(i);
						DayNews newsInfo = new  DayNews();
						newsInfo.setTitle(news.getTitle());
						newsInfo.setSummary(news.getSummary());
						newsInfo.setLogofile(news.getLogofile());
						newsInfo.setPublishdate(news.getPublishdate());
						newsInfo.setUrl(news.getUrl());
						list.add(newsInfo);
					}
					mTotalPage = list.size() / 10;
					LogUtil.e(TAG, "total page=" + mTotalPage);
					for (int i = 0; i < 10; i++){
						listPage.add(list.get(i));
					}
					newsAdapter.notifyDataSetChanged();
				}
				Toast.makeText(DayNewsActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
			}


			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				BaseListModel<DayNews> info = null;
				try {
					info = GsonUtils.parseJSON(str, new TypeToken<BaseListModel<DayNews>>() {
					}.getType());
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
				if (info != null && info.getMsg() != null && info.getMsg().size() > 0) {
					list = info.getMsg();
					for (int i = 0; i < 10; i++){
						listPage.add(list.get(i));
					}
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					newsAdapter.notifyDataSetChanged();
					mTotalPage = list.size() / 10;
					LogUtil.e(TAG, "total page=" + mTotalPage);

					//判断是否保存到数据库
					long lastExitTime = PreferencesUtils.getLong(DayNewsActivity.this, Constant.EXIT_TIME);
					if (DateUtil.getStampDiffDays(lastExitTime, System.currentTimeMillis()) > 1)
					{
						LogUtil.e(TAG, "delete");
						List<NewsInfo> list1 = DataSupport.findAll(NewsInfo.class);
						if (list1 != null && list1.size() > 0)
						{

							DataSupport.deleteAll(NewsInfo.class);
						}
						for (int i = 0; i < 10; i++) {
							DayNews news = list.get(i);
							NewsInfo newsInfo = new  NewsInfo();
							newsInfo.setTitle(news.getTitle());
							newsInfo.setSummary(news.getSummary());
							newsInfo.setLogofile(news.getLogofile());
							newsInfo.setPublishdate(news.getPublishdate());
							newsInfo.setUrl(news.getUrl());
							newsInfo.save();
						}
					}
				}else {
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					Toast.makeText(DayNewsActivity.this, "暂无数据!", Toast.LENGTH_SHORT).show();
				}
			}

		});

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, WebviewActivity.class);
		intent.putExtra("link", list.get(arg2).getUrl());
		intent.putExtra("title", list.get(arg2).getTitle());//"hide"
		startActivity(intent);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		if (mCurrentPage < mTotalPage) {
			if (list != null && list.size() > 0)
			{
				for (int i = mCurrentPage * 10; i < (mCurrentPage + 1) * 10; i++) {

					listPage.add(list.get(i));
				}
				newsAdapter.notifyDataSetChanged();
			}
			mCurrentPage ++;
		}else{
			HttpMethod.Toast(this, "没有数据可加载了！");
		}
		mRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		list.clear();
		listPage.clear();
		mCurrentPage = 1;
		gainData();
		mRefreshView.onHeaderRefreshComplete();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(this);
	}

}
