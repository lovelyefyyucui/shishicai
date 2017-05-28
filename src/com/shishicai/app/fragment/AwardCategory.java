package com.shishicai.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.MainActivity;
import com.shishicai.app.activity.adapter.CategoryPagerAdapter;
import com.shishicai.app.activity.adapter.PKshiAdapter;
import com.shishicai.app.domain.PKshiInfo;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.DensityUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.Header;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class AwardCategory extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, OnPageChangeListener, View.OnClickListener {
    private MainActivity context;
    private View awardCategory;
    private LinearLayout ll_tab_1,ll_tab_2;
    private ViewPager mViewPager;
    private PullToRefreshView mPullToRefreshView1;
    private PullToRefreshView mPullToRefreshView2;
    private List<View> mViews;
    private ListView mListView1;
    private ListView mListView2;
    private LinearLayout msgLayout;
    private LinearLayout msgLayout1;
    private LinearLayout linear;
    private LinearLayout linear1;
    private PKshiAdapter mAdapter1;
    private PKshiAdapter mAdapter2;
    private String TAG = "AwardCategory";
    private int index;
    private String currentDay;
    private String yesterday;
    private List<PKshiInfo.RootBean.Pk10Bean> list;
    private List<PKshiInfo.RootBean.Pk10Bean> list1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (awardCategory == null) {
            awardCategory = inflater.inflate(R.layout.award_category, container, false);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) awardCategory.getParent();
        if (parent != null)
        {
            parent.removeView(awardCategory);
        }
        ll_tab_1 = (LinearLayout) awardCategory.findViewById(R.id.ll_tab_1);
        ll_tab_2 = (LinearLayout) awardCategory.findViewById(R.id.ll_tab_2);
        ll_tab_1.setOnClickListener(this);
        ll_tab_2.setOnClickListener(this);
        mViewPager = (ViewPager) awardCategory.findViewById(R.id.vPager);
        /***************** 左右滑动不销毁子View **********************/
        mViewPager.setOffscreenPageLimit(2);

        mViews = new ArrayList<View>();
        View view1 = inflater.inflate(R.layout.category_list, null);
        View view2 = inflater.inflate(R.layout.category_list, null);
        mViews.add(view1);
        mViews.add(view2);
        mListView1 = (ListView) view1.findViewById(R.id.msg_listview);
        mListView2 = (ListView) view2.findViewById(R.id.msg_listview);
        mPullToRefreshView1 = (PullToRefreshView) view1.findViewById(R.id.pullToRefreshView1);
        mPullToRefreshView2 = (PullToRefreshView) view2.findViewById(R.id.pullToRefreshView1);
        msgLayout = (LinearLayout) view1.findViewById(R.id.msg_layout);
        msgLayout1 = (LinearLayout) view2.findViewById(R.id.msg_layout);
        linear = (LinearLayout) view1.findViewById(R.id.msg_load);
        linear1 = (LinearLayout) view2.findViewById(R.id.msg_load);
        mPullToRefreshView1.setOnHeaderRefreshListener(this);
        mPullToRefreshView1.setOnFooterRefreshListener(this);
        mPullToRefreshView2.setOnHeaderRefreshListener(this);
        mPullToRefreshView2.setOnFooterRefreshListener(this);
        currentDay = DateUtil.getCurrentDate();
        yesterday = DateUtil.getYestoday(currentDay, "yyyy-MM-dd");
        msgLayout.setVisibility(View.GONE);
        linear.setVisibility(View.VISIBLE);

        mViewPager.setAdapter(new CategoryPagerAdapter(mViews));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOnPageChangeListener(this);
        ll_tab_1.setSelected(true);
        mViewPager.setCurrentItem(index);
        list = new ArrayList<PKshiInfo.RootBean.Pk10Bean>();
        list1 = new ArrayList<PKshiInfo.RootBean.Pk10Bean>();
        mAdapter1 = new PKshiAdapter(context, list);
        mListView1.setAdapter(mAdapter1);
        mAdapter2 = new PKshiAdapter(context, list1);
        mListView2.setAdapter(mAdapter2);
        if (index == 0) {
            gainData();
        }

        return awardCategory;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context = (MainActivity) activity;
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        switch (index) {
            case 0:
                gainData();
                mPullToRefreshView1.onHeaderRefreshComplete();
                break;

            case 1:
                gainData(yesterday);
                mPullToRefreshView2.onHeaderRefreshComplete();
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        switch (index) {
            case 0:
                mPullToRefreshView1.onFooterRefreshComplete();
                break;
            case 1:
                mPullToRefreshView2.onFooterRefreshComplete();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        switch (position) {
            case 0:
                ll_tab_1.setSelected(true);
                ll_tab_2.setSelected(false);
                context.setRightIvGone();
                if (list.size() == 0)
                     gainData();
                break;
            case 1:
                ll_tab_1.setSelected(false);
                ll_tab_2.setSelected(true);
                context.setRightIvVisable();
                if (list1.size() == 0)
                    gainData(yesterday);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void gainData() {
        if (list.size() > 0){
            list.clear();
        }
        String url = Constant.AWARD_URL;
        String currentDay = DateUtil.getCurrentDate();
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
                PKshiInfo info;
                try {
                    info = GsonUtils.parseJSON(result, PKshiInfo.class);
                }catch (JsonSyntaxException e){
                    LogUtil.e(TAG, "err=" + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                if (info != null && info.getRoot() != null){
                    if (info.getRoot().getPk10() != null && info.getRoot().getPk10().size() > 0) {
                        list.addAll(info.getRoot().getPk10());
                        Collections.reverse(list); // 倒序排列
                        msgLayout.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.GONE);
                        mAdapter1.notifyDataSetChanged();
                    }
                }else {
                    msgLayout.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.GONE);
                    Toast.makeText(context, "暂无数据!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }



    public void gainData(String day) {
        if (list1.size() > 0)
        {
            list1.clear();
        }
        String url = Constant.AWARD_URL;
        String simpleString = day.replace("-", "");
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
                        list1.addAll(info.getRoot().getPk10());
                        Collections.reverse(list1); // 倒序排列
                        msgLayout1.setVisibility(View.VISIBLE);
                        linear1.setVisibility(View.GONE);
                        mAdapter2.notifyDataSetChanged();
                    }
                }else {
                    msgLayout1.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.GONE);
                    Toast.makeText(context, "暂无数据!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_tab_1:
                ll_tab_1.setSelected(true);
                ll_tab_2.setSelected(false);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_tab_2:
                ll_tab_1.setSelected(false);
                ll_tab_2.setSelected(true);
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    public int getIndex() {
        return index;
    }
}
