package com.shishicai.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.activity.adapter.MsgAdapter;
import com.shishicai.app.db.dao.MsgInfo;
import com.shishicai.app.utils.Base2Activity;
import com.shishicai.app.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MsgActivity extends Base2Activity implements OnClickListener{

	private TextView title;
	private ImageView titleimgLeft;
	private ListView listMsg;
	private List<MsgInfo> mInfos;
	private MsgAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_activity);
        TAG = "MsgActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("消息");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		listMsg = (ListView) findViewById(R.id.msg_listview);

		mInfos = DataSupport.order("time desc").find(MsgInfo.class);
		if (mInfos != null && mInfos.size() > 0)
		{
			for (MsgInfo info : mInfos) {
				LogUtil.e(TAG, info.getMsgId() + " " + info.getContent() + "\n" + info.getTime());
			}
			adapter = new MsgAdapter(this, mInfos);
			listMsg.setAdapter(adapter);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.title_left:
				finish();
				break;
		}
	}
}
