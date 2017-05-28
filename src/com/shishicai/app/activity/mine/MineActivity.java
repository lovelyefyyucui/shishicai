package com.shishicai.app.activity.mine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.LoginEntity;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MineActivity extends Activity{
	private TextView title;	// 标题信息
	private Button login, register;
	private EditText et_username, et_password;
	private String username, password;
	private CheckBox ckb_remember;
	private SharedPreferences sharedPreferences;
	private TextView tv_forget;
	private ProgressDialog dialog;
	public static long timeOut;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_activity_login);
		title = (TextView) findViewById(R.id.title_top);
		title.setText("登录");
		login = (Button) findViewById(R.id.mine_btn_login);
		login.setOnClickListener(new MyClickListener());
		register = (Button) findViewById(R.id.mine_btn_register);
		register.setOnClickListener(new MyClickListener());
		ckb_remember = (CheckBox) findViewById(R.id.login_ckb_remember);
		tv_forget = (TextView) findViewById(R.id.login_forget);
		tv_forget.setOnClickListener(new MyClickListener());
		et_username = (EditText) findViewById(R.id.login_username);
		et_password = (EditText) findViewById(R.id.login_password);
		
		sharedPreferences = MineActivity.this.getSharedPreferences(Constant.IS_OPEN, MODE_PRIVATE);
		boolean isRemmeber = sharedPreferences.getBoolean("rememberPwd", false);
		String username = sharedPreferences.getString("username", null);
		String password = sharedPreferences.getString("password", null);
		if (isRemmeber) {
			ckb_remember.setChecked(true);
			et_username.setText(username);
			et_password.setText(password);
		}else{
			ckb_remember.setChecked(false);
		}
		
	}

        
	
	class MyClickListener implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			switch (v.getId()) {
			case R.id.mine_btn_login:
				if (username.equals("")) {
					Toast.makeText(MineActivity.this, "用户名不能为空！",Toast.LENGTH_SHORT).show();
				} else if (password.equals("")) {
					Toast.makeText(MineActivity.this, "密码不能为空！",Toast.LENGTH_SHORT).show();
				} else {
					if (HttpMethod.isNetworkConnected(MineActivity.this)) {
						dialog = ProgressDialog.show(MineActivity.this, "",
								"正在登录，请稍等 …", true, true);
						dialog.setCanceledOnTouchOutside(false);
						normalLogin(username,password);
					}else{
						Toast.makeText(MineActivity.this, "亲，网络连接失败", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.mine_btn_register:
				intent = new Intent(MineActivity.this, MineRegister.class);
				startActivity(intent);
				break;
			case R.id.login_forget:
				intent = new Intent(MineActivity.this, MineFindPassword.class);
				startActivity(intent);
				break;
			}
		}
	}
	
	private void normalLogin(final String username,final String password){
		RequestParams params = new RequestParams();
		params.put("Name",username);
		params.put("Pwd",password);
		LogUtil.e("login", "url=" + Constant.URLUser);
		LogUtil.e("login", "name=" + username + ",password=" + password);
		HttpUser.post(Constant.URLUser, params, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e("login-result", "err=" + arg3.getMessage());
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e("login-result", "login-result=" + str);
				if (!TextUtils.isEmpty(str)) {
					LoginEntity entity = (LoginEntity) GsonUtils.parseJSON(str, LoginEntity.class);
					Constant.session_id = entity.getSession_id();
					Constant.isLogin = true;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String time = sdf.format(date);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt("status", 1);		//status  1：普通登录  	2：第三方登录  微博 QQ
					editor.putBoolean("rememberPwd", ckb_remember.isChecked());
					editor.putString("username", username);
					editor.putString("password", password);
					editor.putString("time", time);
					editor.commit();
					dialog.dismiss();
					MineActivity.this.finish();
				}
			}
		});
	}
	
}
