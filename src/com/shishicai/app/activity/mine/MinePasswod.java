package com.shishicai.app.activity.mine;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;

public class MinePasswod extends Activity {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息
	private EditText etOldpwd, etNewpwd,etRenewpwd;
	private String oldpwd, newpwd,renewpwd;
	private Button submit;
	
	public static boolean isChange = false;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 58:
				etOldpwd.setText("");
				etNewpwd.setText("");
				etRenewpwd.setText("");
				Toast.makeText(MinePasswod.this, "用户信息过期，请重新登录！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(MinePasswod.this, MineActivity.class);
				startActivity(intent);
				MinePasswod.this.finish();
				break;
			}
		}
	};
	private  ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_password);

		// 设置标题图标
		title = (TextView) findViewById(R.id.title_top);
		title.setText("密码管理");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);	
		titleimgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		etOldpwd = (EditText) findViewById(R.id.password_old);
		etNewpwd = (EditText) findViewById(R.id.password_new);
		etRenewpwd = (EditText) findViewById(R.id.password_renew);
		submit = (Button) findViewById(R.id.password_submit);
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				oldpwd = etOldpwd.getText().toString();
				newpwd =  etNewpwd.getText().toString();
				renewpwd = etRenewpwd.getText().toString();
				
				if(oldpwd.equals("")){
					Toast.makeText(MinePasswod.this, "原密码不能为空！", Toast.LENGTH_SHORT).show();
				}else if(newpwd.equals("")){
					Toast.makeText(MinePasswod.this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
				}else if(!newpwd.equals(renewpwd)){
					Toast.makeText(MinePasswod.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
				}else{
					dialog = ProgressDialog.show(MinePasswod.this, "",
							"正在提交，请稍等 …", true, true);
					dialog.setCanceledOnTouchOutside(false);
					if (HttpMethod.isNetworkConnected(MinePasswod.this)) {
						RequestParams params = new RequestParams();
						params.put("act", "act_edit_password");
						params.put("authkey", Constant.session_id);
						params.put("old_password", oldpwd);
						params.put("new_password", newpwd);
						HttpUser.post(Constant.URLUser, params, new JsonHttpResponseHandler(){
							public void onSuccess(JSONObject arg0) {
								dialog.dismiss();
								String suc = arg0.optString("success");
								String error = arg0.optString("error");
								if(!suc.equals("")){
									Toast.makeText(MinePasswod.this,  suc, Toast.LENGTH_SHORT).show();
									Message msg = new Message();
									msg.what = 58;
									handler.sendMessage(msg);
								}else{
									Toast.makeText(MinePasswod.this,  error, Toast.LENGTH_SHORT).show();
								}
							};
							
							public void onFailure(Throwable arg0) {
								dialog.dismiss();
							};
						});
					}else{
						HttpMethod.Toast(MinePasswod.this);
					}
				}
			}
		});
	}
}
