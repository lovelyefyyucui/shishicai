package com.shishicai.app.activity.mine;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.shishicai.R;

import com.shishicai.app.Constant;
import com.shishicai.app.domain.Goods;
import com.shishicai.app.domain.MyOrder;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

public class MineOrder extends Activity {
	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private ListView listView;
	private MyAdapter myAdapter;
	private MyOrder myOrder;
	private List<MyOrder> myOrders;
	private List<Goods> myGoods;
	private Goods goods;
	private TabWidget tab;
	private TabHost mTabHost;
	private TabSpec mTabSpec1, mTabSpec2;
	private ProgressDialog dialog;
	private static final String TAB1 = "goodstype";
	private static final String TAB2 = "brandtype";
	private static String TAG = "com.soshow.hiyoga.MineOrder";
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 60:
				dialog.dismiss();
				HttpMethod.ToastTimeOut(MineOrder.this);
				break;
			case 61:
				dialog.dismiss();
				myAdapter.notifyDataSetChanged();
				break;
			case 62:
				dialog.dismiss();
				setContentView(R.layout.activity_tool);
				title = (TextView) findViewById(R.id.title_top);
				title.setText("我的订单");
				titleimgLeft = (ImageView) findViewById(R.id.title_left);
				titleimgLeft.setImageResource(R.drawable.title_left);
				titleimgLeft.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
				TextView text = (TextView) findViewById(R.id.empty);
				text.setText("暂无订单");
				break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_order);
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("我的订单");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		titleimgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tab = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost = (TabHost) findViewById(R.id.tabHostId);
		mTabHost.setup();

		mTabSpec1 = mTabHost.newTabSpec(TAB1);
		mTabSpec1.setIndicator(createView(R.layout.activity_main_tab, "未完成"));

		mTabSpec1.setContent(R.id.mine_order_tab1);

		mTabSpec2 = mTabHost.newTabSpec(TAB2);
		mTabSpec2.setIndicator(createView(R.layout.activity_main_tab, "已完成"));
		mTabSpec2.setContent(R.id.mine_order_tab2);

		mTabHost.addTab(mTabSpec1);
		mTabHost.addTab(mTabSpec2);

		mTabHost.setCurrentTab(0);
		((TextView) mTabHost.getCurrentTabView().findViewById(R.id.textViewId))
		.setTextColor(Color.WHITE);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
//				Log.e("TAG", "getCurrentTab" + mTabHost.getCurrentTab() + ",tabid =" + tabId);
				((TextView) mTabHost.getCurrentTabView().findViewById(R.id.textViewId))
				.setTextColor(Color.WHITE);
				switch (mTabHost.getCurrentTab()) {
				case 0:
					tab.setBackgroundResource(R.drawable.shape_register);
					((TextView) tab.getChildTabViewAt(1).findViewById(
							R.id.textViewId)).setTextColor(Color.rgb(64, 224, 208));
					break;
				case 1:
					tab.setBackgroundResource(R.drawable.shape);
					((TextView) tab.getChildTabViewAt(0).findViewById(
							R.id.textViewId)).setTextColor(Color.rgb(64, 224, 208));
					break;

				default:
					break;
				}
			}
		});
		myOrders = new ArrayList<MyOrder>();
		myGoods = new ArrayList<Goods>();
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);
		gainOrder();
		listView = (ListView) findViewById(R.id.mine_order_listview);
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (HttpMethod.isNetworkConnected(MineOrder.this)) {
					myOrder = myOrders.get(arg2);
					Intent intent = new Intent(MineOrder.this, MineOrderDetail.class);
					intent.putExtra("orderId", myOrder.getOrder_id());
					startActivity(intent);
				}else{
					HttpMethod.Toast(MineOrder.this);
				}
			}
		});
	}
	
	private View createView(int resId, String tabName) {
		View view = LayoutInflater.from(this)
				.inflate(resId, null);
		TextView textView = (TextView) view.findViewById(R.id.textViewId);
		// textView.setTag(tabName);
		textView.setText(tabName);
		return view;
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myOrders.size();
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
			private ImageView goods_logo;
			private TextView goods_name, goods_price,goods_attr,goods_sn,fee,status,orderSn,goods_num;
		}
		
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = View.inflate(MineOrder.this, R.layout.mine_order_item, null);
				holder.goods_logo = (ImageView) arg1.findViewById(R.id.order_goods_logo);
				holder.goods_name = (TextView) arg1.findViewById(R.id.goods_name);
				holder.goods_price = (TextView) arg1.findViewById(R.id.goods_price);
				holder.goods_attr = (TextView) arg1.findViewById(R.id.goods_attr);
				holder.goods_sn = (TextView) arg1.findViewById(R.id.goods_sn);
				holder.goods_num = (TextView) arg1.findViewById(R.id.goods_num);
				holder.fee = (TextView) arg1.findViewById(R.id.myorder_total_fee);
				holder.status = (TextView) arg1.findViewById(R.id.myorder_order_status);
				holder.orderSn = (TextView) arg1.findViewById(R.id.myorder_order_sn);
				arg1.setTag(holder);
			}else{
				holder = (Holder) arg1.getTag();
			}
			myOrder = myOrders.get(arg0);
			goods = myGoods.get(arg0);
			holder.fee.setText(myOrder.getTotal_fee());
			holder.status.setText(myOrder.getOrder_status_txt());
			ImageLoader.getInstance().displayImage(Constant.URLDomain + "/" + goods.getGoods_thumb(), holder.goods_logo, Constant.options, Constant.animateFirstListener);
			holder.goods_name.setText(goods.getGoods_name());
			holder.goods_price.setText(goods.getMarket_price());
			holder.goods_attr.setText(goods.getSize());
			holder.goods_sn.setText(goods.getGoods_sn());
			holder.goods_num.setText("×" + goods.getGoods_num());
			holder.orderSn.setText("订单号：" + myOrder.getOrder_sn());
			return arg1;
		}
	}
	
	/*
	 * 获取我的订单,"goods_list":[]有异常
	 */
	public void gainOrder(){
			String url = Constant.URLADV + "user.php";
			RequestParams params = new RequestParams();
			params.put("act","order_list");
			params.put("authkey", Constant.session_id);
			params.put("page", "1");
			HttpUser.get(url, params, new AsyncHttpResponseHandler(){

				public void onFailure(int arg0, Header[] arg1,
									  byte[] arg2, Throwable arg3) {
					LogUtil.e(TAG, "err=" + arg3.getMessage());
				}

				public void onSuccess(int arg0, Header[] arg1,
									  byte[] arg2) {
					String str = new String(arg2);
					LogUtil.e(TAG + Constant.URLADV, str);
					Message msg = new Message();
					try{
						JSONObject jsonObject = new JSONObject(str);
						String count = jsonObject.getString("count");
						if (!count.equals("0")) {
							JSONArray jsonArray = jsonObject.getJSONArray("list");
							for (int i = 0; i < jsonArray.length() - 1; i ++) {
								JSONObject item = jsonArray.getJSONObject(i);
								myOrder = new MyOrder();
								myOrder.setOrder_id(item.getString("order_id"));
								myOrder.setLog_id(item.getString("log_id"));
								myOrder.setOrder_sn(item.getString("order_sn"));
								myOrder.setOrder_time(item.getString("order_time"));
								myOrder.setOrder_status(item.getString("order_status"));
								myOrder.setOrder_status_txt(item.getString("order_status_txt"));
								myOrder.setTotal_fee(item.getString("total_fee"));
								myOrder.setHandler(item.getString("handler"));
								myOrders.add(myOrder);
								JSONArray jsonArray1 = item.getJSONArray("goods_list");
								for (int j = 0; j < jsonArray1.length(); j ++) {
									JSONObject item1 = jsonArray1.getJSONObject(j);
									goods = new Goods();
									goods.setGoods_thumb(item1.getString("goods_thumb"));
									goods.setGoods_name(item1.getString("goods_name"));
									goods.setSize(item1.getString("goods_attr"));
									goods.setGoods_id(item1.getString("goods_attr_id"));
									goods.setGoods_num(item1.getString("goods_number"));
									goods.setMarket_price(item1.getString("goods_price"));
									goods.setGoods_sn(item1.getString("goods_sn"));
									myGoods.add(goods);
								}
							}
							msg.what = 61;
						}else{
							msg.what = 62;
						}
						handler.sendMessage(msg);
					} catch (JSONException e) {
						LogUtil.e(TAG + ",json", e.toString());
					}

				}
			});
	}
}
