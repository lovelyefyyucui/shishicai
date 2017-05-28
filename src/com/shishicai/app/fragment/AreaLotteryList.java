package com.shishicai.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shishicai.R;
import com.shishicai.app.activity.AwardActivity;
import com.shishicai.app.activity.adapter.CategoryListAdapter;
import com.shishicai.app.domain.CategoryInfo;
import com.shishicai.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class AreaLotteryList extends Fragment  implements AdapterView.OnItemClickListener {
	private Activity context;
	private ListView categoryLv;
	private View areaLottery;
	private CategoryListAdapter adapter;
	private String TAG = "AreaLottery";
	private List<CategoryInfo> list;
	private int[] imgIds = {R.drawable.ic_launcher,
			R.drawable.klblogo, R.drawable.kuaile_10,
			R.drawable.kuaile_12, R.drawable.eleven_5, R.drawable.eleven_five,
			R.drawable.fucai_3d, R.drawable.shengfucai,
			R.drawable.fast_three, R.drawable.seven_lecai,R.drawable.shuangseqiu,
			R.drawable.shishicai
	}; //R.drawable.kuaile_10,
	private String[] names = {"重庆时时彩",
			"北京快乐8", "广东快乐十分",
			"浙江快乐十二", "广西11选5", "辽宁11选5", "福彩3d", "六场半全场",
			"上海快3", "七乐彩", "双色球",
			"黑龙江时时彩"
	};//"北京PK十",

	private String[] urls = {"http://f.apiplus.cn/cqssc-10.json",
			"http://f.apiplus.net/bjkl8.json",
			"http://f.apiplus.net/gdklsf.json",	"http://f.apiplus.net/zjkl12.json",
			"http://f.apiplus.net/gx11x5.json", "http://f.apiplus.net/ln11x5.json",
			"http://f.apiplus.net/fc3d.json", "http://f.apiplus.net/zcbqc.json",
			"http://f.apiplus.net/shk3.json", "http://f.apiplus.net/qlc.json", "http://f.apiplus.net/ssq.json",
			"http://f.apiplus.net/hljssc.json"
	};//"http://f.apiplus.net/bjpk10.json",
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (areaLottery == null) {
			areaLottery = inflater.inflate(R.layout.area_lottery_list, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) areaLottery.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(areaLottery);
	    }
		categoryLv = (ListView) areaLottery.findViewById(R.id.lottery_lv);
		categoryLv.setOnItemClickListener(this);
		initData();
		adapter = new CategoryListAdapter(context, list);
		categoryLv.setAdapter(adapter);
		return areaLottery;
	}


	private void initData(){
		list = new ArrayList<CategoryInfo>();
		for (int i = 0; i < imgIds.length; i ++){
			CategoryInfo info = new CategoryInfo();
			info.setImgID(imgIds[i]);
			info.setName(names[i]);
			info.setUrl(urls[i]);
			list.add(info);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, AwardActivity.class);
		String link =  list.get(position).getUrl();
		intent.putExtra("link", link);
		LogUtil.e(TAG, "link=" + link);
		intent.putExtra("title", list.get(position).getName());
		startActivity(intent);
	}
}
