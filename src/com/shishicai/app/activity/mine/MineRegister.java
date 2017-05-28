package com.shishicai.app.activity.mine;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.shishicai.R;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.LoginEntity;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;


public class MineRegister extends Activity implements OnClickListener {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private EditText et_username, et_nickname, et_pwd, et_repwd, et_email;
	private Button submit;
	private String username, nickname, pwd, repwd, email;
	private EditText phoneET;
	private String phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_register);
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("注册用户");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		et_username = (EditText) findViewById(R.id.register_username);
		et_nickname = (EditText) findViewById(R.id.register_nick_name);
		et_pwd = (EditText) findViewById(R.id.register_password);
		et_repwd = (EditText) findViewById(R.id.register_repassword);
		et_email = (EditText) findViewById(R.id.register_email);
		phoneET = (EditText) findViewById(R.id.forgetPsw1ET);
		submit = (Button) findViewById(R.id.register_submit);
		titleimgLeft.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left:
			MineRegister.this.finish();
			break;

		case R.id.register_submit:
			username = et_username.getText().toString();
			nickname = et_nickname.getText().toString();
			pwd = et_pwd.getText().toString();
			repwd = et_repwd.getText().toString();
			email = et_email.getText().toString();
			phone = phoneET.getText().toString().trim();
			if (HttpMethod.isNetworkConnected(MineRegister.this)) {
				normalRegist(username, pwd, nickname, email, phone);
			}else
			{
				HttpMethod.Toast(MineRegister.this);
			}
			break;
		}
		
	}
	
	private void normalRegist(final String username, final String password, final String nickname,final String email, final String phone){

		if (TextUtils.isEmpty(username)) {
			Toast.makeText(MineRegister.this, "请输入用户名！",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(MineRegister.this, "请输入密码！",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(repwd)) {
			Toast.makeText(MineRegister.this, "请确认密码！",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (pwd.length() < 6){
			Toast.makeText(MineRegister.this, "密码不能少于6个字符！", Toast.LENGTH_SHORT).show();
			return;
		}else if (TextUtils.isEmpty(email)) {
			Toast.makeText(MineRegister.this, "请输入邮箱！",
					Toast.LENGTH_SHORT).show();
			return;
		}else if (TextUtils.isEmpty(phone)) {
			Toast.makeText(MineRegister.this, "请输入手机号码！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		else if (!pwd.equals(repwd)) {
				Toast.makeText(MineRegister.this, "两次密码输入不一致！",
						Toast.LENGTH_SHORT).show();
				return;
			}
		final ProgressDialog dialog = ProgressDialog.show(MineRegister.this, "",
				"正在注册，请稍等 …", true, true);
		dialog.setCanceledOnTouchOutside(false);
		RequestParams params = new RequestParams();
		params.put("name",username);
		params.put("pwd",password);
		params.put("nick_name", nickname);
		params.put("email", email);
		params.put("phone", phone);
		LogUtil.e("regist", "url=" + Constant.URLRegist);
		LogUtil.e("regist", "Name=" + username + ",Pwd=" + password + ",nick_name=" + nickname + ",email=" + email + ",phone=" + phone);
		HttpUser.post(Constant.URLUser, params, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e("regist-result", "regist-result=" + arg3.getMessage());
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e("regist-result", "regist-result=" + str);
				if (!TextUtils.isEmpty(str)) {
					LoginEntity entity = GsonUtils.parseJSON(str, LoginEntity.class);
					Constant.session_id = entity.getSession_id();
					Constant.isLogin = true;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String time = sdf.format(date);
					SharedPreferences sharedPreferences = MineRegister.this.getSharedPreferences(Constant.IS_OPEN, MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt("status", 1);		//status  1：普通登录  	2：第三方登录  微博 QQ
					editor.putString("username", username);
					editor.putString("password", password);
					editor.putString("time", time);
					editor.commit();
					dialog.dismiss();
					MineRegister.this.finish();
				}

			}

		});
	}
}
