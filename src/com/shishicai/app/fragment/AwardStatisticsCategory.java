package com.shishicai.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.MainActivity;
import com.shishicai.app.activity.adapter.CategoryPagerAdapter;
import com.shishicai.app.activity.adapter.AwardStatisticsAdapter;
import com.shishicai.app.domain.AwardStatisticInfo;
import com.shishicai.app.domain.GuanyaheBean;
import com.shishicai.app.domain.NumberBean;
import com.shishicai.app.domain.WeuBean;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.ui.PullToRefreshView;
import com.shishicai.app.utils.DensityUtil;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class AwardStatisticsCategory extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, OnPageChangeListener, View.OnClickListener {
    private MainActivity context;
    private View awardStatisticsCategory;
    private LinearLayout ll_tab_1,ll_tab_2;
    private LinearLayout tab_1, tab_2, tab_3, tab_4, tab_5, tab_6, tab_7, tab_8, tab_9, tab_10;
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
    private AwardStatisticsAdapter mAdapter1;
    private AwardStatisticsAdapter mAdapter2;
    private String TAG = "AwardStatisticsCategory";
    private int index;
    private List<NumberBean> list;
    private List<NumberBean> list1;
    private WeuBean weu;
    private GuanyaheBean guanyahe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (awardStatisticsCategory == null) {
            awardStatisticsCategory = inflater.inflate(R.layout.award_statistics_category, container, false);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) awardStatisticsCategory.getParent();
        if (parent != null)
        {
            parent.removeView(awardStatisticsCategory);
        }
        ll_tab_1 = (LinearLayout) awardStatisticsCategory.findViewById(R.id.ll_tab_1);
        ll_tab_2 = (LinearLayout) awardStatisticsCategory.findViewById(R.id.ll_tab_2);
        ll_tab_1.setOnClickListener(this);
        ll_tab_2.setOnClickListener(this);
        mViewPager = (ViewPager) awardStatisticsCategory.findViewById(R.id.vPager);
        /***************** 左右滑动不销毁子View **********************/
        mViewPager.setOffscreenPageLimit(2);

        mViews = new ArrayList<View>();
        View view1 = inflater.inflate(R.layout.category_single_list, null);
        View view2 = inflater.inflate(R.layout.category_double_list, null);
        mViews.add(view1);
        mViews.add(view2);
        mListView1 = (ListView) view1.findViewById(R.id.msg_listview);
        mListView2 = (ListView) view2.findViewById(R.id.msg_listview);
        tab_1 = (LinearLayout) view1.findViewById(R.id.tab_1);
        tab_2 = (LinearLayout) view1.findViewById(R.id.tab_2);
        tab_3 = (LinearLayout) view1.findViewById(R.id.tab_3);
        tab_4 = (LinearLayout) view1.findViewById(R.id.tab_4);
        tab_5 = (LinearLayout) view1.findViewById(R.id.tab_5);
        tab_6 = (LinearLayout) view1.findViewById(R.id.tab_6);
        tab_7 = (LinearLayout) view1.findViewById(R.id.tab_7);
        tab_8 = (LinearLayout) view1.findViewById(R.id.tab_8);
        tab_9 = (LinearLayout) view1.findViewById(R.id.tab_9);
        tab_10 = (LinearLayout) view1.findViewById(R.id.tab_10);
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
        msgLayout.setVisibility(View.GONE);
        linear.setVisibility(View.VISIBLE);

        mViewPager.setAdapter(new CategoryPagerAdapter(mViews));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOnPageChangeListener(this);
        ll_tab_1.setSelected(true);
        tab_1.setSelected(true);
        mViewPager.setCurrentItem(index);
        list = new ArrayList<NumberBean>();
        list1 = new ArrayList<NumberBean>();
        mAdapter1 = new AwardStatisticsAdapter(context, list);
        mAdapter2 = new AwardStatisticsAdapter(context, list1);
        mListView1.setAdapter(mAdapter1);
        mListView2.setAdapter(mAdapter2);
        if (index == 0) {
            gainData();
        }

        return awardStatisticsCategory;
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
                gainData();
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
                if (list.size() == 0)
                     gainData();
                break;
            case 1:
                ll_tab_1.setSelected(false);
                ll_tab_2.setSelected(true);
                if (list1.size() == 0)
                    gainData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void gainData() {
        switch (index) {
            case 0:
                if (list.size() > 0){
                    list.clear();
                }
                break;
            case 1:
                if (list1.size() > 0){
                    list1.clear();
                }
                break;
            default:
                break;
        }
        String url = Constant.AWARD_STATISTICS_URL;
        LogUtil.e(TAG, "url=" + url);
        HttpUser.get(url, new AsyncHttpResponseHandler(){

            public void onFailure(int arg0, Header[] arg1,
                                  byte[] arg2, Throwable arg3) {
                LogUtil.e(TAG, "err=" + arg3.getMessage());
                switch (index) {
                    case 0:
                        msgLayout.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.GONE);
                        break;
                    case 1:
                        msgLayout1.setVisibility(View.VISIBLE);
                        linear1.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                Toast.makeText(context, "网络异常!", Toast.LENGTH_SHORT).show();
            }

            public void onSuccess(int arg0, Header[] arg1,
                                  byte[] arg2) {
                String str = new String(arg2);
//                LogUtil.e(TAG, "result=" + str);
                String result = DensityUtil.xml2JSON(str);
                LogUtil.e(TAG, "result=" + result);
                AwardStatisticInfo info;
                try {
                    info = GsonUtils.parseJSON(result, AwardStatisticInfo.class);
                }catch (JsonSyntaxException e){
                    LogUtil.e(TAG, "err=" + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                if (info != null){
                    if (info.getRoot() != null) {
                        switch (index) {
                            case 0:
                                if (info.getRoot().getWeu() != null && info.getRoot().getWeu().getOne() != null)
                                {
                                    weu = info.getRoot().getWeu();
                                    list.addAll(weu.getOne().getHaoma().getNumber());
                                    msgLayout.setVisibility(View.VISIBLE);
                                    linear.setVisibility(View.GONE);
                                    mAdapter1.notifyDataSetChanged();
                                }
                                break;
                            case 1:
                                if (info.getRoot().getGuanyahe() != null && info.getRoot().getGuanyahe().getHaoma() != null)
                                {
                                    guanyahe = info.getRoot().getGuanyahe();
                                    list1.addAll(guanyahe.getHaoma().getNumber());
                                    msgLayout1.setVisibility(View.VISIBLE);
                                    linear1.setVisibility(View.GONE);
                                    mAdapter2.notifyDataSetChanged();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }else {
                    switch (index) {
                        case 0:
                            msgLayout.setVisibility(View.VISIBLE);
                            linear.setVisibility(View.GONE);
                            break;
                        case 1:
                            msgLayout1.setVisibility(View.VISIBLE);
                            linear1.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getOne().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getTwo().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getThree().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getFour().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getFive().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getSix().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getSeven().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getEight().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getNight().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
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
                if (weu != null)
                {
                    list.clear();
                    list.addAll(weu.getTen().getHaoma().getNumber());
                    mAdapter1.notifyDataSetChanged();
                }
                break;
        }
    }

}
