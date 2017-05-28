package com.shishicai.app.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.shishicai.R;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shishicai.app.activity.mine.MineCollection;
import com.shishicai.app.activity.mine.MineFindPassword;
import com.shishicai.app.activity.mine.MineOrder;
import com.shishicai.app.activity.mine.MineRegister;
import com.shishicai.app.activity.mine.MineSetting;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.LoginEntity;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

public class Mine extends Fragment implements OnClickListener {
	private Context context;
	private View mineView;
	// 登录界面
	private TextView title;
	private Button login, register;
	private EditText et_username, et_password;
	private String username, password;

	private CheckBox ckb_remember;
	private TextView tv_forget;
	private TextView address, collect, order, shopcart, setting, coin,vip;
	private TextView user_name;
	private ImageView headPic;
	private SharedPreferences sharedpreferences;
	private LinearLayout loginLayout;
	private FrameLayout loginedLayout;	
	private DisplayImageOptions options;
	private SharedPreferences.Editor editor;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 105:
				init();
				//status  1：普通登录  	2：第三方登录  微博 QQ
				if (sharedpreferences.getInt("status", 1) == 2 || sharedpreferences.getInt("status", 1)==3) {
					user_name.setText(sharedpreferences.getString("nickName", null));
				}else{
					user_name.setText(sharedpreferences.getString("username", null));
				}
				String userId = sharedpreferences.getString("userId", null);
				if (userId != null) {
					String url = Constant.URLDomain +"/uc_server/avatar.php?uid=" + userId + "&size=small";
					LogUtil.e("img", "url=" + url);
					ImageLoader.getInstance().displayImage(url, headPic, options, Constant.animateFirstListener);
				}
				
				break;
			case 104:
				initLogin();
				break;
			}
			 
			super.handleMessage(msg);
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    if (mineView == null) {
			mineView = inflater.inflate(R.layout.mine_login, container, false);// 登录界面
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) mineView.getParent();  
	    if (parent != null)  
	    {  
	        parent.removeView(mineView);  
	    }  
	    loginLayout = (LinearLayout) mineView.findViewById(R.id.login);
	    loginedLayout = (FrameLayout) mineView.findViewById(R.id.logined);
	    title = (TextView) mineView.findViewById(R.id.title_top);
		 	options = new DisplayImageOptions.Builder()
         .showStubImage(R.drawable.g_car_sample)    //在ImageView加载过程中显示图片
         .showImageForEmptyUri(R.drawable.g_car_sample)  //image连接地址为空时
         .showImageOnFail(R.drawable.g_car_sample)  //image加载失败
         .cacheInMemory(true)  //加载图片时会在内存中加载缓存
         .cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
         .bitmapConfig(Bitmap.Config.RGB_565)  
         .displayer(new RoundedBitmapDisplayer(20))  //设置用户加载图片task(这里是圆角图片显示)
         .build();
		sharedpreferences = context.getSharedPreferences(Constant.IS_OPEN, 0);
		if (Constant.isLogin) {
			init();
		} else {
			initLogin();
		}
		return mineView;
	}

	@Override
	public void onAttach(Activity activity) {
		context = activity;
		super.onAttach(activity);
	}


	private void init() {
		loginLayout.setVisibility(View.GONE);
		loginedLayout.setVisibility(View.VISIBLE);
		title.setText("我的");
		//登陆后，各个信息的点击事件
		address = (TextView) mineView.findViewById(R.id.mine_address);
		collect = (TextView) mineView.findViewById(R.id.mine_collect);
		order = (TextView) mineView.findViewById(R.id.mine_order);
		shopcart = (TextView) mineView.findViewById(R.id.mine_shopcart);
		setting = (TextView) mineView.findViewById(R.id.mine_setting);
		coin = (TextView) mineView.findViewById(R.id.mine_coin);
		vip = (TextView) mineView.findViewById(R.id.mine_vip);
		address.setOnClickListener(this);
		collect.setOnClickListener(this);
		order.setOnClickListener(this);
		shopcart.setOnClickListener(this);
		setting.setOnClickListener(this);
		coin.setOnClickListener(this);
		vip.setOnClickListener(this);
		user_name = (TextView) mineView.findViewById(R.id.mine_username);
		headPic = (ImageView) mineView.findViewById(R.id.mine_head_pic);
	}

	private void initLogin() {
		loginLayout.setVisibility(View.VISIBLE);
		loginedLayout.setVisibility(View.GONE);
		// 设置标题图片文字
		title.setText("登录");
		login = (Button) mineView.findViewById(R.id.mine_btn_login);
		register = (Button) mineView.findViewById(R.id.mine_btn_register);
		ckb_remember = (CheckBox) mineView.findViewById(R.id.login_ckb_remember);
		tv_forget = (TextView) mineView.findViewById(R.id.login_forget);
		et_username = (EditText) mineView.findViewById(R.id.login_username);
		et_password = (EditText) mineView.findViewById(R.id.login_password);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		tv_forget.setOnClickListener(this);
//		boolean isRemmeber = sharedPreferences.getBoolean("rememberPwd", false);
		String username = sharedpreferences.getString("username", "");
		String password = sharedpreferences.getString("password", "");
//		if (isRemmeber) {
			ckb_remember.setChecked(true);
			et_username.setText(username);
			et_password.setText(password);
//		}else{
//			ckb_remember.setChecked(false);
//		}
	}

	@Override
	public void onClick(View v) 
	{
		Intent intent;		
		switch (v.getId()) 
		{
		case R.id.mine_btn_login:
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			if ( TextUtils.isEmpty(username.trim())|| TextUtils.isEmpty(password.trim())) 
			{
				Toast.makeText(context, "用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
			} else 
			{
				if (HttpMethod.isNetworkConnected(context)) 
				{
					normalLogin(username,password);
				}else{
					Toast.makeText(context, "亲，网络连接失败", Toast.LENGTH_SHORT).show();
				}
			}
		break;
		case R.id.mine_btn_register:
			intent = new Intent(context, MineRegister.class);
			startActivity(intent);
		break;
		case R.id.login_forget:
			intent = new Intent(context, MineFindPassword.class);
			startActivity(intent);
		break;
		case R.id.mine_address:
//			intent = new Intent(context, MineAddress.class);
//			intent.putExtra("activity", "Mine");
//			startActivity(intent);
		break;
		case R.id.mine_shopcart:
			intent = new Intent();
//			startActivity(intent);
		break;
		case R.id.mine_collect:
			intent = new Intent(context, MineCollection.class);
			startActivity(intent);
		break;
		case R.id.mine_order:
			intent = new Intent(context, MineOrder.class);
			startActivity(intent);
		break;
		case R.id.mine_setting:
			intent = new Intent(context, MineSetting.class);
			startActivityForResult(intent, 49);
			break;
		case R.id.mine_coin:
			
			break;
		case R.id.mine_vip:
			
			break;
		}
	}
	
	private void normalLogin(final String username,final String password){
		final ProgressDialog dialog = ProgressDialog.show(context, "",
				"正在登录，请稍等 …", true, true);
		dialog.setCanceledOnTouchOutside(false);
		RequestParams params = new RequestParams();
		params.put("name",username);
		params.put("pwd",password);
		LogUtil.e("login", "url=" + Constant.URLUser);
		LogUtil.e("login", "name=" + username + ",pwd=" + password);
		HttpUser.post(Constant.URLUser, params, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e("login-result", "login-result=" + arg3.getMessage());
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e("login-result", "login-result=" + str);
				if (!TextUtils.isEmpty(str)) {
					LoginEntity entity = GsonUtils.parseJSON(str, LoginEntity.class);
					Constant.session_id = entity.getSession_id();
					Constant.isLogin = true;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String time = sdf.format(date);
					editor = sharedpreferences.edit();// 调用SharedPreferences.Editor方法对SharedPreferences进行修改
					editor.putInt("status", 1);		//status  1：普通登录  	2：第三方登录  微博 QQ
					editor.putBoolean("rememberPwd", ckb_remember.isChecked());
					editor.putString("username", username);
					editor.putString("password", password);
					editor.putString("time", time);
					editor.commit();
					Message msg = handler.obtainMessage();
					msg.what = 105;
					handler.sendMessage(msg);
					dialog.dismiss();
				}

			}

		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 49:
			Message msg = new Message();
			msg.what = 104;
			handler.sendMessage(msg);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}
}
