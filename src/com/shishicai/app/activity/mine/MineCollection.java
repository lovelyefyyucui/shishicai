package com.shishicai.app.activity.mine;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import com.shishicai.R;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.Goods;
import com.shishicai.app.domain.News;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

public class MineCollection extends Activity {
	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private ListView lvgoods,lvarticle,lvactivity;
	public int cursorWidth;
	private List<Goods> list;
	private Goods goods;
	private List<News> listarticle,listactivity;
	private News article;
	private MyAdapter myAdapter;
	private MyArticleAdapter myArticleAdapter,myActivityAdapter;
	private TabWidget tab;
	private TabHost mTabHost;
	private TabSpec mTabSpec1, mTabSpec2,mTabSpec3;
	private ProgressDialog dialog;
	private static final String TAG = "com.soshow.hiyoga.MineCollection";
	private static final String TAB1 = "read";
	private static final String TAB2 = "activity";
	private static final String TAB3 = "goods";
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				dialog.dismiss();
				HttpMethod.ToastTimeOut(MineCollection.this);
				break;
			case 101:
				dialog.dismiss();
				myArticleAdapter.notifyDataSetChanged();
				break;
			case 102:
				dialog.dismiss();
				myAdapter.notifyDataSetChanged();
				break;
			case 103:
				dialog.dismiss();
				myActivityAdapter.notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_collection);
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("关注收藏");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		tab = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost = (TabHost) findViewById(R.id.tabHostId);
		mTabHost.setup();
		mTabSpec1 = mTabHost.newTabSpec(TAB1);
		mTabSpec1.setIndicator(createView(R.layout.collection_tab, "阅读"));
		mTabSpec1.setContent(R.id.mine_tab1);
		mTabSpec2 = mTabHost.newTabSpec(TAB2);
		mTabSpec2.setIndicator(createView(R.layout.collection_tab, "参与"));
		mTabSpec2.setContent(R.id.mine_tab2);
		mTabSpec3 = mTabHost.newTabSpec(TAB3);
		mTabSpec3.setIndicator(createView(R.layout.collection_tab, "商品"));
		mTabSpec3.setContent(R.id.mine_tab3);
		mTabHost.addTab(mTabSpec1);
		mTabHost.addTab(mTabSpec2);
		mTabHost.addTab(mTabSpec3);
		mTabHost.setCurrentTab(0);
		((TextView) mTabHost.getCurrentTabView().findViewById(
				R.id.tvId)).setTextColor(Color.rgb(64, 224, 208));
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
//				Log.e("TAG", "getCurrentTab" + mTabHost.getCurrentTab() + ",tabid =" + tabId);
				((TextView) mTabHost.getCurrentTabView().findViewById(R.id.tvId)).setTextColor(Color.rgb(64, 224, 208));
				switch (mTabHost.getCurrentTab()) {
				case 0:
					((TextView) tab.getChildTabViewAt(1).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					((TextView) tab.getChildTabViewAt(2).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					break;
				case 1:
					((TextView) tab.getChildTabViewAt(0).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					((TextView) tab.getChildTabViewAt(2).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					break;
				case 2:
					((TextView) tab.getChildTabViewAt(0).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					((TextView) tab.getChildTabViewAt(1).findViewById(R.id.tvId)).setTextColor(Color.BLACK);
					break;
				default:
					break;
				}
			}
		});
		titleimgLeft.setOnClickListener(new MyClickListener());
		lvgoods = (ListView) findViewById(R.id.mine_collectgoods_listview);
		lvarticle = (ListView) findViewById(R.id.mine_collectarticle_listview);
		lvactivity = (ListView) findViewById(R.id.mine_collectactivity_listview);
		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);
		list = new ArrayList<Goods>();
		listarticle = new ArrayList<News>();
		listactivity = new ArrayList<News>();
		myAdapter = new MyAdapter();
		myArticleAdapter = new MyArticleAdapter(listarticle);
		myActivityAdapter = new MyArticleAdapter(listactivity);
		lvgoods.setAdapter(myAdapter);
		lvarticle.setAdapter(myArticleAdapter);
		lvactivity.setAdapter(myActivityAdapter);
		lvgoods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (HttpMethod.isNetworkConnected(MineCollection.this)) {
					goods = list.get(arg2);
					String goodsId = goods.getGoods_id();
					Intent intent = new Intent();
					intent.putExtra("urlPart", "goods.php?id=" + goodsId);
					intent.putExtra("goodsId", goodsId);
//					startActivity(intent);
				}else{
					HttpMethod.Toast(MineCollection.this);
				}
			}
		});
		lvarticle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intent = new Intent(MineCollection.this, NewsActivity.class);
//				intent.putExtra("id", listarticle.get(arg2).getId());
//				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		// 获得收藏信息
		gainCollection();
		gainArticleCollection();
		gainActivityCollection();
		super.onResume();
	}
	
	private void gainArticleCollection() {
		final Message msg = handler.obtainMessage();
		if (HttpMethod.isNetworkConnected(MineCollection.this)) 
		{
			RequestParams params = new RequestParams();
			params.put("act", "collection_article_list");
			params.put("authkey", Constant.session_id);
			params.put("is_act", "0");
			LogUtil.e(TAG , Constant.URLUser + "?act=collection_article_list&authkey=" + Constant.session_id);
			HttpUser.get(Constant.URLUser,  params, new JsonHttpResponseHandler(){

//				public void onFailure(int arg0, Header[] arg1,
//									  Throwable arg2, JSONObject arg3) {
//
//				}
//
//				public void onSuccess(int arg0, Header[] arg1,
//									  JSONObject arg2) {
//
//
//				}


				public void onFailure(int arg0, Header[] arg1,
									  Throwable arg2, JSONObject arg3) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());
				}

				public void onSuccess(int arg0, Header[] arg1,
									  JSONObject arg2) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());
					try{
						listarticle.clear();
						JSONArray jsonArray = arg2.getJSONArray("goods_list");
						for (int i = 0; i < jsonArray.length(); i ++) {
							JSONObject item = jsonArray.getJSONObject(i);
							article = new News();
							article.setId(item.getString("rec_id"));
//							article.setIs_attention(item.getString("is_attention"));
//							article.setArticle_id(item.getString("goods_id"));
							article.setTitle(item.getString("goods_name"));
//							article.setAdd_time(item.getString("add_time"));
//							article.setUrl(item.getString("url"));
							listarticle.add(article);
						}
						msg.what = 101;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						LogUtil.e(TAG ,e.toString());
					}
				}
			});
		}else{
			msg.what = 100;
			handler.sendMessage(msg);
		}
	}
	
	private void gainActivityCollection() {
		final Message msg = handler.obtainMessage();
		if (HttpMethod.isNetworkConnected(MineCollection.this)) 
		{
			RequestParams params = new RequestParams();
			params.put("act", "collection_article_list");
			params.put("authkey", Constant.session_id);
			params.put("is_act", "1");
			LogUtil.e(TAG , Constant.URLUser + "?act=collection_article_list&authkey=" + Constant.session_id);
			HttpUser.get(Constant.URLUser,  params, new JsonHttpResponseHandler(){

				public void onFailure(int arg0, Header[] arg1,
									  Throwable arg2, JSONObject arg3) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());
				}

				public void onSuccess(int arg0, Header[] arg1,
									  JSONObject arg2) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());
					try{
						listactivity.clear();
						JSONArray jsonArray = arg2.getJSONArray("goods_list");
						for (int i = 0; i < jsonArray.length(); i ++) {
							JSONObject item = jsonArray.getJSONObject(i);
							article = new News();
							article.setId(item.getString("rec_id"));
//							article.setIs_attention(item.getString("is_attention"));
//							article.setArticle_id(item.getString("goods_id"));
							article.setTitle(item.getString("goods_name"));
//							article.setAdd_time(item.getString("add_time"));
//							article.setUrl(item.getString("url"));
							listactivity.add(article);
						}
						msg.what = 103;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						LogUtil.e(TAG ,e.toString());
					}
				}

			});
		}else{
			msg.what = 100;
			handler.sendMessage(msg);
		}
	}
	

	private View createView(int resId, String tabName) {
		View view = LayoutInflater.from(this)
				.inflate(resId, null);
		TextView textView = (TextView) view.findViewById(R.id.tvId);
		// textView.setTag(tabName);
		textView.setText(tabName);
		return view;
	}
	
	/**
	 * 产品适配器
	 */
	private class MyArticleAdapter extends BaseAdapter {
		private List<News> list;
		public MyArticleAdapter(List<News> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		private class Holder {
			private	ImageView article_logo;
			private TextView article_name, article_addtime;
			private Button delbtn;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = View.inflate(MineCollection.this,
						R.layout.mine_collection_article, null);
				holder.article_logo = (ImageView) arg1.findViewById(R.id.collect_article_logo);
				holder.article_name = (TextView) arg1.findViewById(R.id.collect_article_name);
				holder.article_addtime = (TextView) arg1.findViewById(R.id.collect_article_addtime);
				holder.delbtn = (Button) arg1.findViewById(R.id.cllect_article_del);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}
			article = list.get(position);
//			ImageLoader.getInstance().displayImage(Constant.URLDomain + "/" + article.getUrl(), holder.article_logo, Constant.options, Constant.animateFirstListener);
			holder.article_name.setText(article.getTitle());
			holder.article_addtime.setText(article.getTime());
			if (HttpMethod.isNetworkConnected(MineCollection.this)) {
				holder.delbtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LogUtil.e(TAG, position + "...");
						AlertDialog.Builder builder = new Builder(
								MineCollection.this);
						builder.setTitle("删除关注");
						builder.setMessage("确定删除？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										RequestParams params = new RequestParams();
										params.put("act", "delete_article_collection");
										params.put("authkey", Constant.session_id);
										params.put("collection_id", article.getId());
										HttpUser.get(Constant.URLUser, params, new JsonHttpResponseHandler(){

											public void onFailure(int arg0, Header[] arg1,
									  			Throwable arg2, JSONObject arg3) {
												LogUtil.e(TAG, arg2.toString());
												Message msg = new Message();
												msg.what = 100;
												handler.sendMessage(msg);
											}

											public void onSuccess(int arg0, Header[] arg1,
									  					JSONObject arg2) {
												Log.e(TAG + Constant.URLUser + "?act=delete_article_collection&id=" + article.getId(), arg2.toString());
												try {
													if (TextUtils.equals(arg2.getString("success"),"1")) {
														Toast.makeText(MineCollection.this, "删除收藏成功", Toast.LENGTH_SHORT).show();
													}else {
														Toast.makeText(MineCollection.this, "删除收藏失败", Toast.LENGTH_SHORT).show();
													}
												} catch (JSONException e) {
													LogUtil.e(TAG, e.toString());
												}

											}
										});
										listarticle.remove(article);
										Message msg = new Message();
										msg.what = 101;
										handler.sendMessage(msg);	
									}
								});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
	
									}
								});
						AlertDialog dialog = builder.create();
						dialog.show();
					}
				});
			}else{
				HttpMethod.Toast(MineCollection.this);
			}
			return arg1;
		}
	}
    
	/**
	 * 产品适配器
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		private class Holder {
			private ImageView goods_logo;
			private TextView goods_name, market_price,shop_price,promote_price;
			private Button delbtn;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = View.inflate(MineCollection.this,
						R.layout.mine_collection_product, null);
				holder.goods_logo = (ImageView) arg1.findViewById(R.id.collect_goods_logo);
				holder.goods_name = (TextView) arg1
						.findViewById(R.id.collect_goods_name);
				holder.market_price = (TextView) arg1.findViewById(R.id.collect_market_price);
				holder.shop_price = (TextView) arg1
						.findViewById(R.id.collect_shop_price);
				holder.promote_price = (TextView) arg1.findViewById(R.id.collect_promote_price);
				holder.delbtn = (Button) arg1.findViewById(R.id.cllect_del);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}
			goods = list.get(position);
			ImageLoader.getInstance().displayImage(Constant.URLDomain + "/" + goods.getGoods_img(), holder.goods_logo, Constant.options, Constant.animateFirstListener);
			holder.goods_name.setText(goods.getGoods_name());
			holder.shop_price.setText(goods.getShop_price());
			holder.market_price.setText(goods.getMarket_price());
			holder.promote_price.setText(goods.getPromote_price());
			if (HttpMethod.isNetworkConnected(MineCollection.this)) {
				holder.delbtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LogUtil.e(TAG, position + "...");
						AlertDialog.Builder builder = new Builder(
								MineCollection.this);
						builder.setTitle("删除关注");
						builder.setMessage("确定删除？");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
//										goods = list.get(position);
//										RequestParams params = new RequestParams();
//										params.put("act", "delete_goods_collection");
//										params.put("authkey", Constant.session_id);
//										params.put("collection_id", goods.getRec_id());
//										HttpUser.get(Constant.URLUser, params, new AsyncHttpResponseHandler());
//										list.remove(goods);
//										Message msg = new Message();
//										msg.what = 102;
//										handler.sendMessage(msg);
									}
								});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
	
									}
								});
						AlertDialog dialog = builder.create();
						dialog.show();
					}
				});
			}else{
				HttpMethod.Toast(MineCollection.this);
			}
			return arg1;
		}
	}


	class MyClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_left: // 关闭当前页
				MineCollection.this.finish();
				break;
			}
		}
	}

	/*
	 *  获得收藏信息 （获取产品关注）
	 */
	private void gainCollection() {
		final Message msg = handler.obtainMessage();
		if (HttpMethod.isNetworkConnected(MineCollection.this)) 
		{
			RequestParams params = new RequestParams();
			params.put("act", "collection_goods_list");
			params.put("authkey", Constant.session_id);
			LogUtil.e(TAG , Constant.URLUser + "?act=collection_goodslist&authkey=" + Constant.session_id);
			HttpUser.get(Constant.URLUser,  params, new JsonHttpResponseHandler(){

				public void onFailure(int arg0, Header[] arg1,
									  Throwable arg2, JSONObject arg3) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());

				}

				public void onSuccess(int arg0, Header[] arg1,
									  JSONObject arg2) {
					LogUtil.e(TAG + "result","result=" + arg2.toString());
					try{
						list.clear();
						JSONArray jsonArray = arg2.getJSONArray("goods_list");
						for (int i = 0; i < jsonArray.length(); i ++) {
							JSONObject item = jsonArray.getJSONObject(i);
							goods = new Goods();
							goods.setRec_id(item.getString("rec_id"));
							goods.setIs_attention(item.getString("is_attention"));
							goods.setGoods_id(item.getString("goods_id"));
							goods.setGoods_name(item.getString("goods_name"));
							goods.setShop_price(item.getString("shop_price"));
							goods.setMarket_price(item.getString("market_price"));
							goods.setPromote_price(item.getString("promote_price"));
							goods.setGoods_img(item.getString("goods_img"));
							list.add(goods);
						}
						msg.what = 102;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						LogUtil.e(TAG ,e.toString());
					}
				}

			});
		}else{
			msg.what = 100;
			handler.sendMessage(msg);
		}
	}
}
