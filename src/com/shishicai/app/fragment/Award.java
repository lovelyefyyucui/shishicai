package com.shishicai.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.activity.adapter.PKshiAdapter;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.PKshiInfo;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.DensityUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Award extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private Activity context;
	private List<PKshiInfo.RootBean.Pk10Bean> list;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private PullToRefreshView mRefreshView;
	private View award;
	private PKshiAdapter awardAdapter;
	private String TAG = "Award";
	private String currentDay;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (award == null) {
			award = inflater.inflate(R.layout.award, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) award.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(award);
	    }

		msgLayout = (LinearLayout) award.findViewById(R.id.msg_layout);
		linear = (LinearLayout) award.findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) award.findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) award.findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		currentDay = DateUtil.getCurrentDate();
		list = new ArrayList<>();
		gainData();
		return award;
	}

	private void gainData() {
		String url = Constant.AWARD_URL;
		String simpleString = currentDay.replace("-", "");
		url = url + simpleString + ".xml";
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
				String result = DensityUtil.xml2JSON(str);
				PKshiInfo info = null;
				try {
					info = GsonUtils.parseJSON(result, PKshiInfo.class);
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
				if (info != null){
					if (info.getRoot().getPk10() != null && info.getRoot().getPk10().size() > 0) {
						list = info.getRoot().getPk10();
						Collections.reverse(list); // 倒序排列
						msgLayout.setVisibility(View.VISIBLE);
						linear.setVisibility(View.GONE);
						awardAdapter = new PKshiAdapter(context, list);
						listArticle.setAdapter(awardAdapter);
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
			gainData();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(context);
	}

}
