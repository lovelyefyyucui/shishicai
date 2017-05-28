package com.shishicai.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shishicai.R;
import com.shishicai.app.activity.adapter.MsgAdapter;
import com.shishicai.app.activity.adapter.ReferListAdapter;
import com.shishicai.app.db.dao.MsgInfo;
import com.shishicai.app.domain.AwardReferInfo;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.DateUtil;
import com.shishicai.app.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateNumberActivity extends Base2Activity implements OnClickListener{

	private TextView title;
	private ImageView titleimgLeft;
	private ListView listMsg;
	private List<AwardReferInfo.ItemArrayBean.DataBeanX> mInfos;
	private ReferListAdapter adapter;
	private int num;
	private String[] singleDouble = {"单", "双"};
	private String[] bigSmall = {"大", "小"};
	private String[] name = {"冠军", "亚军", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名", "冠亚和"};
	private Random r;
	private int[] lottery;
	private static final int MY_PERMISSIONS_REQUEST = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_num);
        TAG = "GenerateNumberActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("自助选号");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		listMsg = (ListView) findViewById(R.id.msg_listview);
		findViewById(R.id.tv_generate).setOnClickListener(this);
		findViewById(R.id.tv_save).setOnClickListener(this);
		findViewById(R.id.tv_clean).setOnClickListener(this);
		r = new Random();
		mInfos = new ArrayList<>();
		getLottery();
		adapter = new ReferListAdapter(this, mInfos);
		listMsg.setAdapter(adapter);


	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.tv_generate:
				getLottery();
				adapter.notifyDataSetChanged();
				break;
			case R.id.tv_clean:
				mInfos.clear();
				adapter.notifyDataSetChanged();
				break;
			case R.id.tv_save:
				if (Build.VERSION.SDK_INT >= 23)
				{
					LogUtil.e(TAG, "version >= 23");
					if (ContextCompat.checkSelfPermission(this,
							Manifest.permission.WRITE_EXTERNAL_STORAGE)
							!= PackageManager.PERMISSION_GRANTED) {
						ActivityCompat.requestPermissions(this,
								new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
								MY_PERMISSIONS_REQUEST);
					}else{
						saveCurrentImage();
					}
				}else{
					saveCurrentImage();
				}
				break;
			case R.id.title_left:
				finish();
				break;
		}
	}


	private List<AwardReferInfo.ItemArrayBean.DataBeanX.DataBean> data;
	private AwardReferInfo.ItemArrayBean.DataBeanX info;
	private AwardReferInfo.ItemArrayBean.DataBeanX.DataBean bean;

	private void getLottery()
	{
		if (mInfos.size() > 0)
		{
			mInfos.clear();
		}
		for (int j = 0; j < 11; j++)
		{
			lottery = new int[5];
			for (int i = 0; i < lottery.length; i++)
			{
				num = r.nextInt(10) + 1;
				lottery[i] = num;
				// if the new one exists in the array,generate again  生成非重复号码
				for (int k = 0; k < i; k++) {
					if (lottery[k] == lottery[i])
					{
						i--;
						continue;
					}
				}
			}
			info = new AwardReferInfo.ItemArrayBean.DataBeanX();
			info.setName(name[j]);
			data = new ArrayList<>();
			bean = new AwardReferInfo.ItemArrayBean.DataBeanX.DataBean();
			String code = Arrays.toString(lottery);
			code = code.substring(1, code.length() - 1);
			bean.setCode(code);
			bean.setColor("");
			bean.setResult("0");//r.nextInt(2) + ""
			data.add(bean);
			bean = new AwardReferInfo.ItemArrayBean.DataBeanX.DataBean();
			bean.setCode(singleDouble[r.nextInt(2)]);
			bean.setColor("");
			bean.setResult("0");
			data.add(bean);
			bean = new AwardReferInfo.ItemArrayBean.DataBeanX.DataBean();
			bean.setCode(bigSmall[r.nextInt(2)]);
			bean.setColor("");
			bean.setResult("0");
			data.add(bean);
			info.setData(data);
			mInfos.add(info);
		}

	}

	private Bitmap temBitmap;

	//这种方法状态栏是空白，显示不了状态栏的信息
	private void saveCurrentImage()
	{
		// 获取内置SD卡路径
		String sdCardPath;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
		if (sdCardExist)
		{
			sdCardPath = Environment.getExternalStorageDirectory().getPath();//获取根目录
		}else {
			sdCardPath = Environment.getDownloadCacheDirectory().getPath();
		}
		//获取当前屏幕的大小
		int width = getWindow().getDecorView().getRootView().getWidth();
		int height = getWindow().getDecorView().getRootView().getHeight();
		//生成相同大小的图片
		temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		//找到当前页面的跟布局
		View view =  getWindow().getDecorView().getRootView();
		//设置缓存
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		//从缓存中获取当前屏幕的图片
		temBitmap = view.getDrawingCache();

		//输出到sd卡
		// 图片文件路径
		String filePath = sdCardPath + File.separator + "screenshot" + DateUtil.getFormatCurrentTime("yyyy-MM-dd_HH:mm:ss") + ".png";
		File file = new File(filePath);
		try {
			if (!file.exists()) {
					file.createNewFile();
			}
			FileOutputStream foStream = new FileOutputStream(file);
			temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
			foStream.flush();
			foStream.close();
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage());
			e.printStackTrace();
			return;
		}
		Toast.makeText(this, "图片已保存至：" + filePath, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (temBitmap != null)
		{
			temBitmap = null;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MY_PERMISSIONS_REQUEST)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				saveCurrentImage();
			} else
			{
				// Permission Denied
				Toast.makeText(this, "读写权限被禁止，无法保存截图", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
