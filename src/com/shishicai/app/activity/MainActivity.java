package com.shishicai.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.fragment.AwardCategory;
import com.shishicai.app.fragment.AwardStatisticsCategory;
import com.shishicai.app.fragment.AwardTrend;
import com.shishicai.app.fragment.Prediction;
import com.shishicai.app.fragment.TwoSideLong;
import com.shishicai.app.service.HttpMethod;
import com.shishicai.app.utils.ActManager;
import com.shishicai.app.utils.BaseActivity;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.LogUtil;
import com.shishicai.app.utils.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements OnCheckedChangeListener, View.OnClickListener {
	private TextView titleTv, titleTimeTv;
	private ImageView rightIv, leftIv;
	private RadioGroup rgBar;
	private long firstBack;
	private Prediction prediction;
	private AwardCategory awardCategory;
	private AwardStatisticsCategory awardStatisticsCategory;
	private TwoSideLong twoSideLong;
	private AwardTrend awardTrend;
	final int DATE_DIALOG = 1;
	private int mYear, mMonth, mDay;


	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		TAG = "MainActivity";
		findViewById(R.id.title_left_ll).setOnClickListener(this);
		titleTv = (TextView) findViewById(R.id.title_top);
		titleTimeTv = (TextView) findViewById(R.id.title_time);
		rightIv = (ImageView) findViewById(R.id.title_right);
		leftIv = (ImageView) findViewById(R.id.title_left);
		rgBar = (RadioGroup) findViewById(R.id.rg_main_bar);
		leftIv.setImageResource(R.drawable.h_logo);

		fragmentManager = getSupportFragmentManager();
		rgBar.setOnCheckedChangeListener(this);
		rgBar.check(R.id.main_tab_refer);
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		String registrationId = PreferencesUtils.getString(this, Constant.DEVICE_ID);
		if (TextUtils.isEmpty(registrationId)){
			registrationId = JPushInterface.getRegistrationID(this);
			if (!TextUtils.isEmpty(registrationId)){
				LogUtil.e(TAG, "Registration Id : " + registrationId);
				PreferencesUtils.putString(this, Constant.DEVICE_ID, registrationId);
			}
		}
		rightIv.setOnClickListener(this);
		rightIv.setImageResource(R.drawable.title_right);
//		rightIv.setImageResource(R.drawable.mainnav_icon07);
		rightIv.setVisibility(View.GONE);
		titleTimeTv.setText("正在开奖");
		final Calendar ca = Calendar.getInstance();
		mYear = ca.get(Calendar.YEAR);
		mMonth = ca.get(Calendar.MONTH);
		mDay = ca.get(Calendar.DAY_OF_MONTH);
	}


	private FragmentTransaction  transaction;
	private void setTabSelection(int index) {
				// 开启一个Fragment事务
			    transaction = fragmentManager.beginTransaction();
				// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
//				hideFragments(transaction);
				switch (index) {
				case 0:
						setRightIvGone();
						if (prediction == null) {
							prediction = new Prediction();
							transaction.add(R.id.container, prediction);
						}else {
							transaction.show(prediction);
						}

						if (awardCategory != null) {
							transaction.hide(awardCategory);
						}

						if (awardStatisticsCategory != null) {
							transaction.hide(awardStatisticsCategory);
						}

					if (twoSideLong != null) {
						transaction.hide(twoSideLong);
					}

						if (awardTrend != null) {
							transaction.hide(awardTrend);
						}
				  break;
				case 1:
					if (awardCategory == null) {
						awardCategory = new AwardCategory();
						transaction.add(R.id.container, awardCategory);
					}else {
						transaction.show(awardCategory);
					}
					if (awardCategory.getIndex() == 1)
					{
						setRightIvVisable();
					}

					if (prediction != null) {
						transaction.hide(prediction);
					}

					if (awardStatisticsCategory != null) {
						transaction.hide(awardStatisticsCategory);
					}

					if (twoSideLong != null) {
						transaction.hide(twoSideLong);
					}

					if (awardTrend != null) {
						transaction.hide(awardTrend);
					}
				break;
				
				case 2:
					setRightIvGone();
					if (awardStatisticsCategory == null) {
						awardStatisticsCategory = new AwardStatisticsCategory();
						transaction.add(R.id.container, awardStatisticsCategory);
					}else {
						transaction.show(awardStatisticsCategory);
					}

					if (prediction != null) {
						transaction.hide(prediction);
					}

					if (awardCategory != null) {
						transaction.hide(awardCategory);
					}

					if (twoSideLong != null) {
						transaction.hide(twoSideLong);
					}


					if (awardTrend != null) {
						transaction.hide(awardTrend);
					}
				break;

				case 3:
					setRightIvGone();
					if (awardTrend == null) {
						awardTrend = new AwardTrend();
						transaction.add(R.id.container, awardTrend);
					}else {
						transaction.show(awardTrend);
					}
					awardTrend.refresh();

					if (prediction != null) {
						transaction.hide(prediction);
					}

					if (twoSideLong != null) {
						transaction.hide(twoSideLong);
					}


					if (awardCategory != null) {
						transaction.hide(awardCategory);
					}

					if (awardStatisticsCategory != null) {
						transaction.hide(awardStatisticsCategory);
					}

				break;

				case 4:
					setRightIvGone();

					if (twoSideLong == null) {
						twoSideLong = new TwoSideLong();
						transaction.add(R.id.container, twoSideLong);
					}else {
						transaction.show(twoSideLong);
					}

					if (prediction != null) {
						transaction.hide(prediction);
					}

					if (awardTrend != null) {
						transaction.hide(awardTrend);
					}


					if (awardCategory != null) {
						transaction.hide(awardCategory);
					}

					if (awardStatisticsCategory != null) {
						transaction.hide(awardStatisticsCategory);
					}

					break;
				default:
					break;
				}
				transaction.commit();
	}



	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_tab_refer:
			setTabSelection(0);
			break;
		case R.id.main_tab_works:
			setTabSelection(1);
			break;
		case R.id.main_tab_more:
			setTabSelection(2);
			break;
		case R.id.main_tab_chat:
			setTabSelection(3);
			break;
		case R.id.main_tab_kefu:
			setTabSelection(4);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) { 
	            long secondBack = System.currentTimeMillis(); 
	            if (secondBack - firstBack > 2000) {
	                Toast.makeText(this, "再按一次退出程序", 
	                        Toast.LENGTH_SHORT).show();
	                firstBack = secondBack;
	                return true; 
	            } else {
					PreferencesUtils.putLong(MainActivity.this, Constant.EXIT_TIME, System.currentTimeMillis());
	            	MobclickAgent.onKillProcess(MainActivity.this);
	    			ActManager.getAppManager().AppExit(MainActivity.this);
	            } 
	        } 
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.title_right:
				showDialog(DATE_DIALOG);
				break;
			case R.id.title_left_ll:
				startActivity(new Intent(this, ToolActivity.class));
				break;
		}
	}




	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DATE_DIALOG:
				return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
		}
		return null;
	}

	public void setRightIvVisable(){
		rightIv.setVisibility(View.VISIBLE);
	}

	public void setRightIvGone(){
		rightIv.setVisibility(View.GONE);
	}

	private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			String date = new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1) + "")
					.append("-").append((mDay < 10) ? "0" + mDay : mDay + "").toString();
			String currentDay = DateUtil.getCurrentDate();
			int diff = DateUtil.getDiffDays(currentDay, date);
			if (diff <= 0)
			{
				date = date.replace("-", "");
				awardCategory.gainData(date);
			}else {
				HttpMethod.Toast(MainActivity.this, "还未到开奖时间，请重新选择!");
			}
		}
	};

	public TextView getTitleTv() {
		return titleTv;
	}

	public TextView getTitleTimeTv() {
		return titleTimeTv;
	}

	public RadioGroup getRgBar() {
		return rgBar;
	}
}
