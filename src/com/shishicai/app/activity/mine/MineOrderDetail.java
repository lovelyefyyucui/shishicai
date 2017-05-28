package com.shishicai.app.activity.mine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.shishicai.R;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.app.domain.Addresses;
import com.shishicai.app.domain.Cart;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.MyOrder;
import com.shishicai.app.image.CustomImageLoader;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

public class MineOrderDetail extends Activity {
	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private MyOrder myOrder;
	private Addresses address;
	private List<Cart> carts;
	private Cart cart;
	private static final int SDK_PAY_FLAG = 1;
	private String goodSubject ="";
	private TextView orderId,orderStatus,payStatus,shipStatus,goodAmount,shipFee,orderAmount,
	consignee,email,tv_address,mobile,tel,signBuilding,bestTime,payName,orderAmount2,shipName,payName2,oosName,userBonus;
	private ListView listView;
	private MyAdapter myAdapter;
	private CustomImageLoader imageLoader;
	private LinearLayout linear;
	private Button btnPayfor;
	
	private static String TAG = "MineOrderDetail";
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) 
			{
			case 500:
				linear.setVisibility(View.INVISIBLE);
				HttpMethod.ToastTimeOut(MineOrderDetail.this);
				break;
			case 516:
				linear.setVisibility(View.INVISIBLE);
				
				orderId.setText(myOrder.getOrder_sn());
				orderStatus.setText(myOrder.getOrder_status());
				payStatus.setText(myOrder.getPay_status());
				shipStatus.setText(myOrder.getShipping_status());
				goodAmount.setText(myOrder.getFormated_goods_amount());
				shipFee.setText(myOrder.getFormated_shipping_fee());
				orderAmount.setText(myOrder.getFormated_order_amount());
				consignee.setText(address.getConsignee());
				email.setText(address.getEmail());
				tv_address.setText(address.getAddress());
				mobile.setText(address.getMobile());
				tel.setText(address.getTel());		
				signBuilding.setText(myOrder.getSign_building());
				bestTime.setText(myOrder.getBest_time());
				payName.setText(myOrder.getPay_name());
				orderAmount2.setText(myOrder.getFormated_order_amount());
				userBonus.setText("-"+myOrder.getFormated_bonus());
				shipName.setText(myOrder.getShipping_name());
				payName2.setText(myOrder.getPay_name());
				oosName.setText(myOrder.getHow_oos());
	
				myAdapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(listView);
				break;
			case SDK_PAY_FLAG: 
				
				break;
				}
		}
	};
	private DecimalFormat format;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_order_detail);	
		format = new DecimalFormat("#.00");
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("订单详情");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		titleimgLeft.setOnClickListener(new MyClickListener());
		
		orderId = (TextView) findViewById(R.id.orderDetail_orderId);
		orderStatus = (TextView) findViewById(R.id.orderDetail_orderStatus);
		payStatus = (TextView) findViewById(R.id.orderDetail_payStatus);
		shipStatus = (TextView) findViewById(R.id.orderDetail_shipStatus);
		goodAmount = (TextView) findViewById(R.id.orderDetail_goodAmount);
		shipFee = (TextView) findViewById(R.id.orderDetail_shipFee);
		orderAmount = (TextView) findViewById(R.id.orderDetail_orderAmount);
		consignee = (TextView) findViewById(R.id.orderDetail_consignee);
		email = (TextView) findViewById(R.id.orderDetail_email);
		tv_address = (TextView) findViewById(R.id.orderDetail_address);
		mobile = (TextView) findViewById(R.id.orderDetail_mobile);
		tel = (TextView) findViewById(R.id.orderDetail_tel);
		signBuilding = (TextView) findViewById(R.id.orderDetail_signBuilding);
		bestTime = (TextView) findViewById(R.id.orderDetail_bestTime);
		payName = (TextView) findViewById(R.id.orderDetail_payName);
		orderAmount2 = (TextView) findViewById(R.id.orderDetail_orderAmount2);
		shipName = (TextView) findViewById(R.id.orderDetail_shipName);
		payName2 = (TextView) findViewById(R.id.orderDetail_payName2);
		oosName = (TextView) findViewById(R.id.orderDetail_oosName);
		userBonus = (TextView) findViewById(R.id.orderDetail_userbonus);
		btnPayfor = (Button) findViewById(R.id.orderDetail_payfor);
		linear = (LinearLayout) findViewById(R.id.orderDetail_load);
		linear.setVisibility(View.VISIBLE);
		
		Bundle bl = getIntent().getExtras();
		String orderId = bl.getString("orderId");
		carts = new ArrayList<Cart>();
		gainOrderDetail(orderId); 	
		listView = (ListView) findViewById(R.id.orderDetail_listview);
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);	
		btnPayfor.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(orderAmount2.getText().toString().equals("￥0.00元"))
				{
					Toast.makeText(MineOrderDetail.this, "订单已取消，不能付款！", Toast.LENGTH_SHORT).show();
					return;
				}
				pay();			
			}
		});
		listView.setEnabled(false);
	}
	
	 /**
	 * call alipay sdk pay. 调用SDK支付
	 * seller_id 卖家支付宝账号 邮箱或手机号或唯一id ？？
		 *  out_trade_no 商户网站唯一订单号
		 * subject 商品名称 
		 * total_fee 总金额 
		 * notify_url 服务器异步通知页面路径
		 * show_url 商品展示的超链接。预留参数。 可空 
		 * sign：签名 ？？
		 *  it_b_pay 
		 *  未付款交易的超时时间: 30m 
		 *  body 商品详情 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
	 */
	private void pay() {
		String orderInfo = getOrderInfor();
		LogUtil.e(TAG + "orderInfo" ,"orderInfo:" + orderInfo);
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG ,e.toString());
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
//				PayTask alipay = new PayTask(MineOrderDetail.this);
				// 调用支付接口
//				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	private String sign(String orderInfo) {
		return orderInfo;
//		return SignUtils.sign(orderInfo, Keys.PRIVATE);
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	public String getOrderInfor() {	
		String str = orderAmount2.getText().toString();	
		String substring = str.substring(1, str.length()-1) ;//去掉价格前的人民币符号
		Double i = Double.parseDouble(substring);			
		String submitSum = format.format(i);
		// 合作者身份ID
		String orderInfo = "";
//		orderInfo = "partner=" + "\"" + Keys.DEFAULT_PARTNER + "\"";
//		// 卖家支付宝账号
//		orderInfo += "&seller_id=" + "\"" + Keys.DEFAULT_SELLER + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + goodSubject + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + "hiyoga" + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + submitSum + "\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://hiyoga.xianshan.cn/paysdk/notify_url.php" + "\"";
		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}

	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
		}


	
	private class MyClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_left:
				finish();
				break;
			}
		}
	}
	
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }   
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return carts.size();
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
		
		private class Holder {
			public TextView goodsName, goodsPrice, goodsSn, goodsAttr,
					goodsQuality;
			public ImageView imageCart;
		}
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = View.inflate(MineOrderDetail.this,R.layout.order_detail_item, null);
				holder.goodsQuality = (TextView) arg1.findViewById(R.id.pay_goods_quality);
				holder.goodsName = (TextView) arg1.findViewById(R.id.pay_goods_nameinfor);
				holder.goodsSn = (TextView) arg1.findViewById(R.id.pay_goods_sn);
				holder.goodsAttr = (TextView) arg1.findViewById(R.id.pay_goods_attr);
				holder.goodsPrice = (TextView) arg1.findViewById(R.id.pay_goods_price);
				holder.imageCart = (ImageView) arg1.findViewById(R.id.pay_goods_img);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}
			cart = carts.get(arg0);
			holder.goodsName.setText(cart.getGoods_name());
			holder.goodsSn.setText(cart.getGoods_sn());
			holder.goodsAttr.setText(cart.getGoods_attr());
			holder.goodsPrice.setText(cart.getGoods_price());
			holder.goodsQuality.setText(cart.getGoods_number());
			imageLoader = new CustomImageLoader(
					MineOrderDetail.this.getApplicationContext());
			String url = Constant.URLDomain + "/"
					+ cart.getGoods_thumb();
			imageLoader.DisplayImage(url, holder.imageCart);
			
			for (int i = 0; i < carts.size(); i++) {
				cart = carts.get(i);
				goodSubject = goodSubject + cart.getGoods_name();
			}
			
			return arg1;
		}
		
	}
	
	/*
	 * 获取订单详情
	 */
	 public void gainOrderDetail(final String orderId){
		 if (HttpMethod.isNetworkConnected(MineOrderDetail.this)) {
			 String url = Constant.URLUser + "?act=order_detail&authkey=" + Constant.session_id + "&order_id=" + orderId;
			 HttpUser.get(url, new AsyncHttpResponseHandler(){

				 public void onFailure(int arg0, Header[] arg1,
									   byte[] arg2, Throwable arg3) {
					 LogUtil.e(TAG, "err=" + arg3.getMessage());
				 }

				 public void onSuccess(int arg0, Header[] arg1,
									   byte[] arg2) {
					 String str = new String(arg2);
					 LogUtil.e(TAG + Constant.URLUser + "?act=order_detail&authkey=" + Constant.session_id + "&order_id=" + orderId, str);
					 try {
						 Message msg = new Message();
						 JSONObject jsonObject = new JSONObject(str);
						 JSONObject jsonObject2 = jsonObject.getJSONObject("order");
						 myOrder = new MyOrder();
						 address = new Addresses();
						 myOrder.setOrder_sn(jsonObject2.getString("order_sn"));
						 myOrder.setOrder_status(jsonObject2.getString("order_status"));
						 myOrder.setShipping_status(jsonObject2.getString("shipping_status"));
						 myOrder.setPay_status(jsonObject2.getString("pay_status"));

						 address.setConsignee(jsonObject2.getString("consignee"));
						 address.setCountry(jsonObject2.getString("country"));
						 address.setProvince(jsonObject2.getString("province"));
						 address.setCity(jsonObject2.getString("city"));
						 address.setDistrict(jsonObject2.getString("district"));
						 address.setAddress(jsonObject2.getString("address"));
						 address.setTel(jsonObject2.getString("tel"));
						 address.setMobile(jsonObject2.getString("mobile"));
						 address.setEmail(jsonObject2.getString("email"));

						 myOrder.setShipping_name(jsonObject2.getString("shipping_name"));
						 myOrder.setPay_name(jsonObject2.getString("pay_name"));
						 myOrder.setFormated_total_fee(jsonObject2.getString("formated_total_fee"));
						 myOrder.setFormated_goods_amount(jsonObject2.getString("formated_goods_amount"));
						 myOrder.setFormated_shipping_fee(jsonObject2.getString("formated_shipping_fee"));
						 myOrder.setFormated_add_time(jsonObject2.getString("formated_add_time"));
						 myOrder.setHow_oos(jsonObject2.getString("how_oos"));
						 myOrder.setFormated_bonus(jsonObject2.getString("formated_bonus"));
						 myOrder.setFormated_order_amount(jsonObject2.getString("formated_order_amount"));

						 JSONArray jsonArray = jsonObject.getJSONArray("goods_list");
						 for (int i = 0; i < jsonArray.length(); i++) {
							 JSONObject item = jsonArray.getJSONObject(i);
							 cart = new Cart();
							 cart.setGoods_id(item.getString("goods_id"));
							 cart.setGoods_name(item.getString("goods_name"));
							 cart.setGoods_sn(item.getString("goods_sn"));
							 cart.setMarket_price(item.getString("market_price"));
							 cart.setGoods_number(item.getString("goods_number"));
							 cart.setGoods_price(item.getString("goods_price"));
							 cart.setGoods_attr(item.getString("goods_attr"));
							 cart.setSubtotal(item.getString("subtotal"));
							 cart.setGoods_thumb(item.getString("goods_image"));
							 carts.add(cart);
						 }
						 msg.what = 516;
						 handler.sendMessage(msg);
					 } catch (Exception e) {
						 Log.e(TAG, e.toString());
					 }
				 }

			 });
		 }else{
			 HttpMethod.Toast(MineOrderDetail.this);
		 }
	 }
}
