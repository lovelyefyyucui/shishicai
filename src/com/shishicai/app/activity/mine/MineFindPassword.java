package com.shishicai.app.activity.mine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.shishicai.R;

import com.shishicai.app.Constant;
import com.shishicai.app.service.HttpUser;

public class MineFindPassword extends Activity {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息

	private EditText et_username,et_email;
	private Button submit;
	private String username,email;
	
//	private static String TAG = "MineFindPassword";
	private  ProgressDialog dialog;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 56:
				et_username.setText("");
				et_email.setText("");
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_find_password);

		// 设置标题图标
		title = (TextView) findViewById(R.id.title_top);
		title.setText("找回密码");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		titleimgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		et_username = (EditText) findViewById(R.id.find_username);
		et_email = (EditText) findViewById(R.id.find_email);
		submit = (Button) findViewById(R.id.find_submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				username = et_username.getText().toString();
				email = et_email.getText().toString();
				if(username.equals("") | email.equals("")){
					Toast.makeText(MineFindPassword.this, "用户名或邮箱不能为空。", Toast.LENGTH_SHORT).show();
				}else{
					dialog = ProgressDialog.show(MineFindPassword.this, "",
							"正在提交，请稍等 …", true, true);
					dialog.setCanceledOnTouchOutside(false);
					RequestParams params = new RequestParams();
					params.put("act", "send_pwd_email");
					params.put("user_name", username);
					params.put("email", email);
					HttpUser.post(Constant.URLUser, params, new JsonHttpResponseHandler(){
						public void onSuccess(JSONObject arg0) {
							dialog.dismiss();
							String success = arg0.optString("success");
							String error = arg0.optString("error");
							if(!success.equals("")){
								try {
									Toast.makeText(MineFindPassword.this,URLDecoder.decode(success, "UTF-8"),Toast.LENGTH_SHORT).show();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									Log.e("findpsw", e.toString());
								}
								Message msg = new Message();
								msg.what = 56;
								handler.sendMessage(msg);
							}else if(!error.equals("")){
								try {
									Toast.makeText(MineFindPassword.this,URLDecoder.decode(error, "UTF-8"),Toast.LENGTH_SHORT).show();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									Log.e("findpsw", e.toString());
								}
							}
						};
						
						public void onFailure(Throwable arg0) {
							dialog.dismiss();
						};
					});
				}
			}
		});
	}
}
