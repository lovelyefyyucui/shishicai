package com.shishicai.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.ChartActivity;
import com.shishicai.app.activity.adapter.PKshiTrendAdapter;
import com.shishicai.app.domain.PKshiInfo;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.AwardPopupWindow;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.DensityUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AwardTrend extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {
	private Activity context;
	private List<PKshiInfo.RootBean.Pk10Bean> list;
	private TextView titleTv;
	private ListView listArticle;
	private LinearLayout msgLayout;
	private LinearLayout linear;
	private PullToRefreshView mRefreshView;
	private View awardTrend;
	private PKshiTrendAdapter awardAdapter;
	private String TAG = "AwardTrend";
	private String currentDay;
	private AwardPopupWindow popupWindow;
	private int index;
	private String title;
	private String[] mTimes;
	private String[] array;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (awardTrend == null) {
			awardTrend = inflater.inflate(R.layout.award_trend, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) awardTrend.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(awardTrend);
	    }
		titleTv = (TextView) awardTrend.findViewById(R.id.title_tv);
		msgLayout = (LinearLayout) awardTrend.findViewById(R.id.msg_layout);
		linear = (LinearLayout) awardTrend.findViewById(R.id.msg_load);
		mRefreshView = (PullToRefreshView) awardTrend.findViewById(R.id.pullToRefreshView1);
		listArticle = (ListView) awardTrend.findViewById(R.id.msg_listview);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		msgLayout.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		awardTrend.findViewById(R.id.title_right_ll).setOnClickListener(this);
		awardTrend.findViewById(R.id.title_left_ll).setOnClickListener(this);
		title = "冠军走势图";
		currentDay = DateUtil.getCurrentDate();
		list = new ArrayList<PKshiInfo.RootBean.Pk10Bean>();
		awardAdapter = new PKshiTrendAdapter(context, list);
		listArticle.setAdapter(awardAdapter);
		listArticle.setOnItemClickListener(this);
		gainData();
		return awardTrend;
	}

	private void gainData() {
		if (list.size() > 0){
			list.clear();
		}
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
				if (info != null && info.getRoot() != null){
					if (info.getRoot().getPk10() != null && info.getRoot().getPk10().size() > 0) {
						list.addAll(info.getRoot().getPk10());
						Collections.reverse(list); // 倒序排列
						msgLayout.setVisibility(View.VISIBLE);
						linear.setVisibility(View.GONE);
						awardAdapter.notifyDataSetChanged();
						mTimes = new String[list.size()];
						array = new String[list.size()];
						for (int i = 0; i < list.size(); i ++)
						{
							String timeStr = list.get(i).getTime();
							timeStr = timeStr.substring(timeStr.indexOf(" "));
							mTimes[i] = timeStr;
							String[] numbers = list.get(i).getNums().split(" ");
							array[i] = numbers[index];
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

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.title_right_ll:
				if (popupWindow == null)
				{
					popupWindow = new AwardPopupWindow(context, this);
				}
				popupWindow.showAsDropDown(v, 0, 5);
				break;
			case R.id.title_left_ll:
				if (mTimes != null && mTimes.length > 0)
				{
					Intent intent = new Intent(context, ChartActivity.class);
					intent.putExtra("title", title);
					Bundle b=new Bundle();
					b.putStringArray("time", mTimes);
					b.putStringArray("array", array);
					intent.putExtras(b);
					startActivity(intent);
				}else {
					HttpMethod.Toast(context, "请等待数据加载完成!");
				}

				break;
			case R.id.font1:
				popupWindow.dismiss();
				title = "冠军走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 0;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font2:
				popupWindow.dismiss();
				title = "亚军走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 1;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font3:
				popupWindow.dismiss();
				title = "第三名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 2;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font4:
				popupWindow.dismiss();
				title = "第四名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 3;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font5:
				popupWindow.dismiss();
				title = "第五名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 4;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font6:
				popupWindow.dismiss();
				title = "第六名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 5;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font7:
				popupWindow.dismiss();
				title = "第七名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 6;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font8:
				popupWindow.dismiss();
				title = "第八名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 7;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font9:
				popupWindow.dismiss();
				title = "第九名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 8;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
			case R.id.font10:
				popupWindow.dismiss();
				title = "第十名走势图";
				mTimes = new String[list.size()];
				array = new String[list.size()];
				titleTv.setText(title);
				index = 9;
				awardAdapter.setIndex(index);
				awardAdapter.notifyDataSetChanged();
				for (int i = 0; i < list.size(); i ++)
				{
					String timeStr = list.get(i).getTime();
					timeStr = timeStr.substring(timeStr.indexOf(" "));
					mTimes[i] = timeStr;
					String[] numbers = list.get(i).getNums().split(" ");
					array[i] = numbers[index];
				}
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, ChartActivity.class);
		intent.putExtra("title", title);
		Bundle b=new Bundle();
		b.putStringArray("time", mTimes);
		b.putStringArray("array", array);
		intent.putExtras(b);
		startActivity(intent);
	}
}
