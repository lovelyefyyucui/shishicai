package com.shishicai.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.activity.WebviewActivity;
import com.shishicai.app.activity.adapter.NewsAdapter;
import com.shishicai.app.db.dao.NewsInfo;
import com.shishicai.app.domain.BaseListModel;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.DayNews;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;

import org.apache.http.Header;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;

public class Read extends Fragment implements OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private Activity context;
	private PullToRefreshView mRefreshView;
	private List<DayNews> list;
	private List<DayNews> listPage;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private View read;
	private NewsAdapter newsAdapter;
	private String TAG = "Read";
	private int mCurrentPage = 1;
	private int mTotalPage = 0;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (read == null) {
			read = inflater.inflate(R.layout.read, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) read.getParent();  
	    if (parent != null)  
	    {  
	        parent.removeView(read);  
	    }
		msgLayout = (LinearLayout) read.findViewById(R.id.msg_layout);
		linear = (LinearLayout) read.findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) read.findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) read.findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		listArticle.setOnItemClickListener(this);
		list = new ArrayList<DayNews>();
		listPage = new ArrayList<DayNews>();
		newsAdapter = new NewsAdapter(context, listPage);
		listArticle.setAdapter(newsAdapter);
		gainData();
		return read;
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
				Toast.makeText(context, "网络异常!", Toast.LENGTH_SHORT).show();
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
					long lastExitTime = PreferencesUtils.getLong(context, Constant.EXIT_TIME);
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
					Toast.makeText(context, "暂无数据!", Toast.LENGTH_SHORT).show();
				}
			}

		});

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(context, WebviewActivity.class);
		intent.putExtra("link", list.get(arg2).getUrl());
		intent.putExtra("title", list.get(arg2).getTitle());//"hide"
		startActivity(intent);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
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
			HttpMethod.Toast(context, "没有数据可加载了！");
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


	public void refresh(){
		if (list != null && list.size() == 0){
			list.clear();
			listPage.clear();
			gainData();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(context);
	}

}
