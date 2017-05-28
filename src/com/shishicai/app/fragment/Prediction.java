package com.shishicai.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.Constant;
import com.shishicai.app.activity.ChartActivity;
import com.shishicai.app.activity.ColdHotActivity;
import com.shishicai.app.activity.LuzhuActivity;
import com.shishicai.app.activity.MainActivity;
import com.shishicai.app.activity.PredictActivity;
import com.shishicai.app.activity.adapter.CateAdapter;
import com.shishicai.app.activity.adapter.CategoryAdapter;
import com.shishicai.app.domain.AwardNewInfo;
import com.shishicai.app.domain.CategoryInfo;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.GsonUtils;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class Prediction extends Fragment implements AdapterView.OnItemClickListener {
	private MainActivity context;
	private TextView title, time, content, content1, content2, content3, content4, content5, content6, content7, content8, content9;
	private View prediction;
	private String TAG = "Prediction";
	private MyCountDownTimer mc;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private static final long VIBRATE_DURATION = 200L;
	private Handler handler;
	private LoadTask task;
	private GridView categoryGv;
	private CateAdapter adapter;
	private List<CategoryInfo> list;
	private int[] imgIds = {R.drawable.pk10_award,R.drawable.pk10pre,
			R.drawable.pk_10, R.drawable.kuaile_12, R.drawable.eleven_5,
			R.drawable.eleven_five,
			R.drawable.fucai_3d, R.drawable.shengfucai};//, R.drawable.fast_three
	private String[] titles = {"PK10开奖", "PK10预测", "数据统计",
			"走势（数表）", "两面长龙",
			"冷热分析",
			"走势（横屏）", "路珠", "历史"};//
	private String[] subTitle = {"今日及往昔开奖结果",
			"今日预测", "各排名欲漏，欲出概率",
			"纯数字走势图", "两面长龙看遗漏",
			"pk10冷热分析",
			"图表走势图", "龙虎路珠、冠亚和路珠等", "龙虎、单双、大小历史"};//

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (prediction == null) {
			prediction = inflater.inflate(R.layout.prediction, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) prediction.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(prediction);
	    }
		time = (TextView) prediction.findViewById(R.id.msg_time);
		title = (TextView) prediction.findViewById(R.id.msg_title);
		content = (TextView) prediction.findViewById(R.id.msg_one);
		content1 = (TextView) prediction.findViewById(R.id.msg_two);
		content2 = (TextView) prediction.findViewById(R.id.msg_three);
		content3 = (TextView) prediction.findViewById(R.id.msg_four);
		content4 = (TextView) prediction.findViewById(R.id.msg_five);
		content5 = (TextView) prediction.findViewById(R.id.msg_six);
		content6 = (TextView) prediction.findViewById(R.id.msg_seven);
		content7 = (TextView) prediction.findViewById(R.id.msg_eight);
		content8 = (TextView) prediction.findViewById(R.id.msg_nine);
		content9 = (TextView) prediction.findViewById(R.id.msg_ten);
		playBeep = true;
		categoryGv = (GridView) prediction.findViewById(R.id.tab_gv);
		categoryGv.setOnItemClickListener(this);
		initData();
		adapter = new CateAdapter(context, list);
		categoryGv.setAdapter(adapter);
		AudioManager audioService = (AudioManager) context.getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		vibrate = true;
		handler = new Handler();
		task = new LoadTask(this);
		initBeepSound();
		gainNewData();
		return prediction;
	}


	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	public void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}


	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	private void initData(){
		list = new ArrayList<CategoryInfo>();
		for (int i = 0; i < imgIds.length; i ++){
			CategoryInfo info = new CategoryInfo();
			info.setImgID(imgIds[i]);
			info.setName(titles[i]);
			info.setUrl(subTitle[i]);
			list.add(info);
		}

	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (MainActivity) activity;
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
		if (mc != null)
		{
			mc.cancel();
			mc = null;
		}
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		HttpUser.cancel(context);
	}

	private int delay;

	public void gainNewData() {
		String url = Constant.AWARD_NEW_URL;
		LogUtil.e(TAG, "url=" + url);
		HttpUser.get(url, new AsyncHttpResponseHandler(){

			public void onFailure(int arg0, Header[] arg1,
								  byte[] arg2, Throwable arg3) {
				LogUtil.e(TAG, "err=" + arg3.getMessage());
				Toast.makeText(context, "网络异常!", Toast.LENGTH_SHORT).show();
			}

			public void onSuccess(int arg0, Header[] arg1,
								  byte[] arg2) {
				String str = new String(arg2);
				LogUtil.e(TAG, "result=" + str);
				AwardNewInfo info;
				try {
					info = GsonUtils.parseJSON(str, AwardNewInfo.class);
				}catch (JsonSyntaxException e){
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
					return;
				}
				int milliseconds = 0;
				if (info.getNext() != null)
				{
					context.getTitleTv().setText("距离" + info.getNext().getPeriodNumber() + "期开奖时间剩余");
					milliseconds = info.getNext().getAwardTimeInterval();
					LogUtil.e(TAG, "get milliseconds=" + milliseconds);
					delay = info.getNext().getDelayTimeInterval();
					LogUtil.e(TAG, "get delay=" + delay);
					if (milliseconds > 0)
					{
						mc = new MyCountDownTimer(milliseconds, 1000);
						mc.start();
					} else {
						handler.postDelayed(task, delay * 1000);
//						new Handler().postDelayed(new Runnable() {
//
//							@Override
//							public void run() {
//								gainNewData();
//							}
//						}, delay * 1000);
					}
				}

				if (milliseconds > 0 && info.getCurrent() != null)
				{
					time.setText(info.getCurrent().getAwardTime());
					title.setText(info.getCurrent().getPeriodNumber() + "期开奖结果");
					String[] numbers = info.getCurrent().getAwardNumbers().split(",");
					content.setText(numbers[0]);
					content.setBackgroundResource(getBackGround(numbers[0]));
					content1.setText(numbers[1]);
					content1.setBackgroundResource(getBackGround(numbers[1]));
					content2.setText(numbers[2]);
					content2.setBackgroundResource(getBackGround(numbers[2]));
					content3.setText(numbers[3]);
					content3.setBackgroundResource(getBackGround(numbers[3]));
					content4.setText(numbers[4]);
					content4.setBackgroundResource(getBackGround(numbers[4]));
					content5.setText(numbers[5]);
					content5.setBackgroundResource(getBackGround(numbers[5]));
					content6.setText(numbers[6]);
					content6.setBackgroundResource(getBackGround(numbers[6]));
					content7.setText(numbers[7]);
					content7.setBackgroundResource(getBackGround(numbers[7]));
					content8.setText(numbers[8]);
					content8.setBackgroundResource(getBackGround(numbers[8]));
					content9.setText(numbers[9]);
					content9.setBackgroundResource(getBackGround(numbers[9]));
				}

			}

		});
	}


	private int getBackGround(String number)
	{
		int num = Integer.valueOf(number);
		int res = 0;
		switch (num)
		{
			case 1:
				res = R.drawable.ball_one;
				break;
			case 2:
				res = R.drawable.ball_two;
				break;
			case 3:
				res = R.drawable.ball_three;
				break;
			case 4:
				res = R.drawable.ball_four;
				break;
			case 5:
				res = R.drawable.ball_five;
				break;
			case 6:
				res = R.drawable.ball_six;
				break;
			case 7:
				res = R.drawable.ball_seven;
				break;
			case 8:
				res = R.drawable.ball_eight;
				break;
			case 9:
				res = R.drawable.ball_nine;
				break;
			case 10:
				res = R.drawable.ball_ten;
				break;
		}
		return res;
	}


	class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			context.getTitleTimeTv().setText("正在开奖");
			handler.postDelayed(task, 5 * 1000);
//			new Handler().postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//				}
//			},  5 * 1000);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			int second = (int) (millisUntilFinished / 1000) % 60;
		    int	minute = (int) (millisUntilFinished / 1000) / 60;
			if (millisUntilFinished / 1000 < 30)
			{
				playBeepSoundAndVibrate();
			}
			String min = minute + "";
			if (minute < 10){
				min = "0" + minute;
			}
			String sec = second + "";
			if (second < 10)
			{
				sec = "0" + second;
			}
			context.getTitleTimeTv().setText(min + ":" + sec);
		}
	}


	/**
	 * 延迟2秒进入主界面
	 * @author Administrator
	 */
	private static class LoadTask implements Runnable {
		WeakReference<Prediction> prediction;

		LoadTask(Prediction prediction){
			this.prediction = new WeakReference<Prediction>(prediction);
		}

		public void run() {
			if (prediction.get() != null){
				prediction.get().gainNewData();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = null;
		switch (position)
		{
			case 0:
				context.getRgBar().check(R.id.main_tab_works);
				break;
			case 1:
				intent = new Intent(context, PredictActivity.class);
				break;
			case 2:
				context.getRgBar().check(R.id.main_tab_more);
				break;
			case 3:
				context.getRgBar().check(R.id.main_tab_chat);
				break;
			case 4:
				context.getRgBar().check(R.id.main_tab_kefu);
				break;
			case 5:
				intent = new Intent(context, ColdHotActivity.class);
				break;
			case 6:
				intent = new Intent(context, ChartActivity.class);
				break;
			case 7:
				intent = new Intent(context, LuzhuActivity.class);
				break;
			case 8:

				break;
		}
		if (intent != null)
		{
			context.startActivity(intent);
		}
	}
}
