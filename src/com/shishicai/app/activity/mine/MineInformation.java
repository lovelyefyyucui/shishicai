package com.shishicai.app.activity.mine;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.shishicai.R;

import com.shishicai.app.MyApplication;
import com.shishicai.app.Constant;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.service.HttpUser;

public class MineInformation extends Activity {

	private TextView title,finish,cancel;
	private ImageView titleimgLeft,rightImg; // 标题信息
	private TextView gender, birthday,email,nickname;
	private static final int DATE_ID = 1;
	private RelativeLayout rNickname,rGender, rBirthday, rEmail;
	private ImageView img,img1,img2,img3;  // 请选择的右侧箭头
	private String emailStr,nicknamestr;
	private String birthdayYear,birthdayMonth,birthdayDay,sex;
	private final String TAG = "MineInformation";
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				 rightImg.setVisibility(View.INVISIBLE);
				 titleimgLeft.setVisibility(View.INVISIBLE);
				 finish.setText("完成");
				 finish.setVisibility(View.VISIBLE);
				 cancel.setText("取消");
				 cancel.setVisibility(View.VISIBLE);
				 img.setVisibility(View.VISIBLE);
				 img1.setVisibility(View.VISIBLE);
				 img2.setVisibility(View.VISIBLE);
				 img3.setVisibility(View.VISIBLE);
				 rNickname.setOnClickListener(new MyClickListener());
				 rGender.setOnClickListener(new MyClickListener());
				 rBirthday.setOnClickListener(new MyClickListener());
				 rEmail.setOnClickListener(new MyClickListener());
				 break;
			case 101:
				 finish.setVisibility(View.INVISIBLE);
				 cancel.setVisibility(View.INVISIBLE);
				 rightImg.setVisibility(View.VISIBLE);
				 titleimgLeft.setVisibility(View.VISIBLE);
				 img.setVisibility(View.INVISIBLE);
				 img1.setVisibility(View.INVISIBLE);
				 img2.setVisibility(View.INVISIBLE);
			 	 img3.setVisibility(View.INVISIBLE);
				 rGender.setOnClickListener(new MyNotClickListener());
				 rBirthday.setOnClickListener(new MyNotClickListener());
				 rEmail.setOnClickListener(new MyNotClickListener());
				 break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_information);
		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("个人资料");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		//modify = (CheckBox) findViewById(R.id.title_ckb);
		titleimgLeft.setImageResource(R.drawable.title_left);
		finish = (TextView) findViewById(R.id.title_finish);
		cancel = (TextView) findViewById(R.id.title_cancel);
		img = (ImageView) findViewById(R.id.address_img);
		img1 = (ImageView) findViewById(R.id.address_img1);
		img2 = (ImageView) findViewById(R.id.address_img2);
		img3 = (ImageView) findViewById(R.id.address_img3);
		rightImg = (ImageView) findViewById(R.id.title_right);
		rightImg.setImageResource(R.drawable.mine_address_right);
		rightImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 100;
				handler.sendMessage(msg);
				
			}
		});
		 
		//取消点击事件
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 101;
				handler.sendMessage(msg);
				MyApplication app = (MyApplication)getApplication();

			}
		});
		
		// 完成的点击事件，还得将修改内容保存
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 101;
				handler.sendMessage(msg);	
				final String birthdayStr = birthday.getText().toString();
				final String genderStr = gender.getText().toString();
				emailStr = email.getText().toString();	
				nicknamestr = nickname.getText().toString();
				if(!birthdayStr.equals("请选择")){
					String[] birthArr = birthdayStr.split("-");
					birthdayYear = birthArr[0];
					birthdayMonth = birthArr[1];
					birthdayDay = birthArr[2];
				}else{
					birthdayYear = "1990";
					birthdayMonth = "1";
					birthdayDay = "1";
				}
				
				if(genderStr.equals("保密")){
					sex = "0";
				}else if(genderStr.equals("男")){
					sex = "1";
				}else if(genderStr.equals("女")){
					sex = "2";
				}else{
					sex = "0";
				}
				if (HttpMethod.isNetworkConnected(MineInformation.this)) {
					RequestParams params = new RequestParams();
					params.put("act", "act_edit_profile");
					params.put("authkey", Constant.session_id);
					params.put("birthdayYear", birthdayYear);
					params.put("birthdayMonth", birthdayMonth);
					params.put("birthdayDay", birthdayDay);
					params.put("birthdayDay", birthdayDay);
					params.put("email", emailStr);
					params.put("nickname", nicknamestr);
					params.put("sex", sex);
					HttpUser.post(Constant.URLUser, params, new AsyncHttpResponseHandler(){

						public void onFailure(int arg0, Header[] arg1,
											  byte[] arg2, Throwable arg3) {
							Log.e(TAG + "jsonerr",arg3.toString());
						}

						public void onSuccess(int arg0, Header[] arg1,
											  byte[] arg2) {
							String str = new String(arg2);
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(str);
								Toast.makeText(MineInformation.this, jsonObject.optString("success"), Toast.LENGTH_SHORT).show();
							} catch (JSONException e) {
								Log.e(TAG + "jsonerr",e.toString());
							}
						}
					});
				}else{
					HttpMethod.Toast(MineInformation.this);
				}
			}
		});
		
		titleimgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		rNickname = (RelativeLayout) findViewById(R.id.mine_relative_nickname);
		rGender = (RelativeLayout) findViewById(R.id.mine_relative_gender);
		rBirthday = (RelativeLayout) findViewById(R.id.mine_relative_birthday);
		rEmail = (RelativeLayout) findViewById(R.id.mine_relative_email);	
		nickname = (TextView) findViewById(R.id.mine_nickname);
		birthday = (TextView) findViewById(R.id.mine_birthday);
		gender = (TextView) findViewById(R.id.mine_gender);
		email = (TextView) findViewById(R.id.mine_email);
		img.setVisibility(View.INVISIBLE);
		img1.setVisibility(View.INVISIBLE);
		img2.setVisibility(View.INVISIBLE);
		img3.setVisibility(View.INVISIBLE);
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mine_relative_nickname:
				Intent intent = new Intent(MineInformation.this, MineNickName.class);
				startActivityForResult(intent, 50);
				break;
			case R.id.mine_relative_birthday:
				// 用于显示日期对话框,他会调用onCreateDialog()
				showDialog(DATE_ID);
				break;
			case R.id.mine_relative_gender:
				AlertDialog.Builder builder = new Builder(MineInformation.this);
				builder.setTitle("请选择");
				final String[] items = new String[]{"男","女","保密"};
				builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Toast.makeText(OrderActivity.this, items[which], 0).show();
						gender.setText(items[which]);
						dialog.dismiss();
					}
				});
				builder.show();
				break;
			case R.id.mine_relative_email:
				Intent intent1 = new Intent(MineInformation.this, MineEmail.class);
				startActivityForResult(intent1, 51);
				break;
			}
		}
	}

	class MyNotClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_ID:
			return new DatePickerDialog(MineInformation.this,
					onDateSetListener, 1990, 0, 1);
		}
		return null;
	}

	
	// 设置时间之后点击SET就会将时间改为你刚刚设置的时间
	// 注意，在DatePicker中，月份是从0开始编号的，但是日是从1开始编号的。
	DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
			// Toast.makeText(MineInformation.this,
			// birthday.getText().toString(), 0).show(); 
		}
	};
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 50:
			Bundle bl = data.getExtras();
			nickname.setText(bl.getString("nickname"));
			break;
		case 51:
			Bundle bl1 = data.getExtras();
			email.setText(bl1.getString("email"));
			break;
		default:
			break;
		}
	}
}
