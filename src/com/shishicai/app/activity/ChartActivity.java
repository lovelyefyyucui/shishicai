package com.shishicai.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.TrendChartInfo;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class ChartActivity extends Base2Activity implements View.OnClickListener {
    private TextView title;
    private ImageView titleimgLeft; // 标题信息
    private String titleString;
    private Intent intent;
    private CombinedChart mChart;
    private List<String> months;
    private List<Map<String, String>> month;
    private List<Map<String, String>> data;
    //数据
    private String[] mTimes;
    private String[] array;
    private LinearLayout classLl;
    private LinearLayout tab_1, tab_2, tab_3, tab_4, tab_5, tab_6, tab_7, tab_8, tab_9, tab_10;
    private int index;
    private List<TrendChartInfo.ItemsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        TAG = "ChartActivity";
        title = (TextView) findViewById(R.id.title_top);
        titleimgLeft = (ImageView) findViewById(R.id.title_left);
        titleimgLeft.setImageResource(R.drawable.top_back);
        titleimgLeft.setOnClickListener(this);
        classLl = (LinearLayout) findViewById(R.id.class_ll);
        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.setDescription("");
        //设置背景颜色
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(true);
        mChart.setDrawBarShadow(true);
        mChart.setMaxVisibleValueCount(100);
        mChart.getAxisRight().setEnabled(false);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[] { CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE,
                CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        months = new ArrayList<String>();
        month = new ArrayList<Map<String, String>>();
        data = new ArrayList<Map<String, String>>();

        intent = getIntent();
        if (intent.hasExtra("title"))
        {
            classLl.setVisibility(View.GONE);
            titleString = intent.getStringExtra("title");
            Bundle b = intent.getExtras();
            array = b.getStringArray("array");
            mTimes = b.getStringArray("time");
            title.setText(titleString);
            initData();
        }else {
            title.setText("走势图");
            classLl.setVisibility(View.VISIBLE);
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
            list = new ArrayList<>();
            tab_1.setSelected(true);
            gainData();
        }
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
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
                if (list.size() > 0)
                {
                    list.clear();
                }
                gainData();
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }

    private void initData(){
        if (month.size() > 0)
        {
            month.clear();
        }
        if (months.size() > 0)
        {
            months.clear();
        }
        if (data.size() > 0)
        {
            data.clear();
        }
        Map<String, String> map = null;
        for (int i = 0; i < mTimes.length; i++) {
            map = new HashMap<String, String>();
            map.put("addDay",mTimes[i]);
            month.add(map);
        }

        Map<String, String> dataMap = null;
        for (int i = 0; i < array.length; i ++) {
            dataMap = new HashMap<String, String>();
            dataMap.put("learnCount", array[i]);
            data.add(dataMap);
        }

        // 横向显示的时间 12/3 12/4
        for (int index = 0; index < month.size(); index++) {
            Map<String, String> cdata = month.get(index);

            months.add(cdata.get("addDay"));
        }

        CombinedData data = new CombinedData(months);

        data.setData(generateLineData());

        mChart.setData(data);
        mChart.invalidate();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        // 纵向显示的数据
        for (int index = 0; index < data.size(); index++) {
            Map<String, String> cdata = data.get(index);

            entries.add(new Entry(Integer.parseInt(cdata.get("learnCount")), index));
        }

        LineDataSet set = new LineDataSet(entries,
                titleString);
        set.setColor(Color.rgb(40, 40, 40));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(40, 40, 40));
        set.setCircleSize(5f);
        set.setFillColor(Color.rgb(40, 40, 40));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(40, 40, 40));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }


    private void gainData() {
        String url = Constant.TREND_CHART + "?version=3000&ball="
                + index;
        LogUtil.e(TAG, "url=" + url);
        HttpUser.get(url, new AsyncHttpResponseHandler(){

            public void onFailure(int arg0, Header[] arg1,
                                  byte[] arg2, Throwable arg3) {
                LogUtil.e(TAG, "err=" + arg3.getMessage());
                Toast.makeText(ChartActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
            }

            public void onSuccess(int arg0, Header[] arg1,
                                  byte[] arg2) {
                String str = new String(arg2);
                LogUtil.e(TAG, "result=" + str);
                TrendChartInfo info = null;
                try {
                    info = GsonUtils.parseJSON(str, TrendChartInfo.class);
                }catch (JsonSyntaxException e){
                    LogUtil.e(TAG, "err=" + e.getMessage());
                    e.printStackTrace();
                }
                if (info.getItems() != null && info.getItems().size() > 0)
                {
                    list.addAll(info.getItems());
                    mTimes = new String[list.size()];
                    array = new String[list.size()];
                    for (int i = 0; i < list.size(); i ++)
                    {
                        mTimes[i] = list.get(i).getPeriod();
                        array[i] = list.get(i).getBall() + "";
                    }
                    initData();
                }
            }
        });
    }

}
