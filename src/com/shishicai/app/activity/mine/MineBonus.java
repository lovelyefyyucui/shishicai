package com.shishicai.app.activity.mine;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.shishicai.R;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.app.domain.Bonus;
import com.shishicai.app.Constant;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

public class MineBonus extends Activity {

	private TextView title;// 标题信息
	private ImageView titleimgLeft; 
	private ListView lvCoupon;
	private List<Bonus> list = new ArrayList<Bonus>();
	private Bonus bonus;
	private MyAdapter myAdapter;
	private LinearLayout linear;

	private static String TAG = "MineCoupon";
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				linear.setVisibility(View.INVISIBLE);
				HttpMethod.ToastTimeOut(MineBonus.this);
				break;
			case 109:
				linear.setVisibility(View.INVISIBLE);
				myAdapter.notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_bonus);

		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("优惠券");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		titleimgLeft.setOnClickListener(new MyClickListener());

		gainData();
		linear = (LinearLayout) findViewById(R.id.bonus_load);
		linear.setVisibility(View.VISIBLE);
		lvCoupon = (ListView) findViewById(R.id.mine_listview_coupon);
		myAdapter = new MyAdapter();
		lvCoupon.setAdapter(myAdapter);
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_left:
				finish();
				break;
			}
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		private class Holder{
			public TextView type_name, startdate, enddate;
			public ImageView ivStatus;
		}
		
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = View.inflate(MineBonus.this,
						R.layout.bonus_listview_item, null);
				holder.type_name = (TextView) arg1.findViewById(R.id.bonus_type_name);
				holder.startdate = (TextView) arg1.findViewById(R.id.bonus_startdate);
				holder.enddate = (TextView) arg1.findViewById(R.id.bonus_enddate);
				holder.ivStatus = (ImageView) arg1.findViewById(R.id.bonus_status);
				arg1.setTag(holder);
			}else{
				holder = (Holder) arg1.getTag();
			}
			String status = list.get(arg0).getStatus().trim();
			if (status.contains("已过期")) {
				holder.ivStatus.setBackgroundResource(R.drawable.bonus_outdate);
			} else if (status.contains("已使用")) {
				holder.ivStatus.setBackgroundResource(R.drawable.bonus_over);
			}
			holder.type_name.setText(list.get(arg0).getType_name());
			holder.startdate.setText(list.get(arg0).getUse_startdate());
			holder.enddate.setText(list.get(arg0).getUse_enddate());
			return arg1;
		}
	}

	// 获取优惠价列表
	public void gainData() {
		if (HttpMethod.isNetworkConnected(MineBonus.this)) {
			HttpUser.get(Constant.URLUser+ "?act=bonus&page=" + 1, new AsyncHttpResponseHandler(){

				public void onFailure(int arg0, Header[] arg1,
									  byte[] arg2, Throwable arg3) {
					LogUtil.e("bonus", "err=" + arg3.getMessage());
				}


				public void onSuccess(int arg0, Header[] arg1,
									  byte[] arg2) {
					String str = new String(arg2);
					System.out.println("str:"+str);
					try{
						Message msg = new Message();
						JSONArray jsonArray = new JSONArray(str);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							bonus = new Bonus();
							bonus.setType_name(item.getString("type_name"));
							bonus.setType_money(item.getString("type_money"));
							bonus.setMin_goods_amount(item
									.getString("min_goods_amount"));
							bonus.setStatus(item.getString("status"));
							bonus.setUse_startdate(item.getString("use_startdate"));
							bonus.setUse_enddate(item.getString("use_enddate"));
							list.add(bonus);
						}
						msg.what = 109;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
		}else{
			HttpMethod.Toast(MineBonus.this);
		}
	}
}
