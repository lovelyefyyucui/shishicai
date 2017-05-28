package com.shishicai.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.WebviewActivity;
import com.shishicai.app.activity.adapter.NewsAdapter;
import com.shishicai.app.activity.adapter.TwoSideLongAdapter;
import com.shishicai.app.db.dao.NewsInfo;
import com.shishicai.app.domain.BaseListModel;
import com.shishicai.app.domain.DayNews;
import com.shishicai.app.domain.TwoSideLongInfo;
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

public class TwoSideLong extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private Activity context;
	private PullToRefreshView mRefreshView;
	private List<List<String>> list;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private View twoSideLong;
	private TwoSideLongAdapter twoSideLongAdapter;
	private String TAG = "TwoSideLong";

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (twoSideLong == null) {
			twoSideLong = inflater.inflate(R.layout.two_side_long, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) twoSideLong.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(twoSideLong);
	    }
		msgLayout = (LinearLayout) twoSideLong.findViewById(R.id.msg_layout);
		linear = (LinearLayout) twoSideLong.findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) twoSideLong.findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) twoSideLong.findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		list = new ArrayList<>();
		twoSideLongAdapter = new TwoSideLongAdapter(context, list);
		listArticle.setAdapter(twoSideLongAdapter);
		gainData();
		return twoSideLong;
	}


	private void gainData() {
		String url = Constant.TWO_SIDE_LONG_URL;
		LogUtil.e(TAG, "url=" + url);
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				msgLayout.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				Toast.makeText(context, "网络异常!", Toast.LENGTH_SHORT).show();
			}


			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				TwoSideLongInfo info = null;
				try {
					info = GsonUtils.parseJSON(str, TwoSideLongInfo.class);
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
				if (info != null && info.getItemArray() != null && info.getItemArray().size() > 0) {
					list.addAll(info.getItemArray());
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					twoSideLongAdapter.notifyDataSetChanged();
				}else {
					msgLayout.setVisibility(View.VISIBLE);
					linear.setVisibility(View.GONE);
					Toast.makeText(context, "暂无数据!", Toast.LENGTH_SHORT).show();
				}
			}

		});

	}



	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
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


	public void refresh(){
		if (list != null && list.size() == 0){
			list.clear();
			gainData();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(context);
	}

}
