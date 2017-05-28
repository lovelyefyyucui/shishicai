package com.shishicai.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shishicai.R;
import com.shishicai.app.activity.adapter.TextAdapter;
import com.shishicai.app.Constant;
import com.shishicai.app.domain.ListData;
import com.shishicai.app.service.HttpUser;
import com.shishicai.app.utils.LogUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment implements View.OnClickListener{
	private Activity context;
	private List<ListData> list;
	private ListView lv;
	private Button send_btn;
	private EditText sendtext;
	private String content_str;
	private TextAdapter adapter;
	private String [] welcomeArray;
	private View chat;
	private String TAG = "Chat";

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (chat == null) {
			chat = inflater.inflate(R.layout.chat_robot, container, false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	    ViewGroup parent = (ViewGroup) chat.getParent();
	    if (parent != null)  
	    {  
	        parent.removeView(chat);
	    }
		list=new ArrayList<ListData>();
		lv = (ListView) chat.findViewById(R.id.lv);
		send_btn = (Button) chat.findViewById(R.id.send_btn);
		sendtext = (EditText) chat.findViewById(R.id.senText);
		send_btn.setOnClickListener(this);
		adapter = new TextAdapter(list, context);
		lv.setAdapter(adapter);
		ListData listData = null;
		listData = new ListData(getRandomWelcomeTips(), listData.receiver);
		list.add(listData);
		return chat;
	}

	private void gainData(String content) {
		String url = Constant.TULING_ROBOT + "?key=" + Constant.TULING_KEY + "&info=" + content;
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
				try {
					JSONObject jb=new JSONObject(str);
					ListData listData = null;
					listData=new ListData(jb.getString("text"),listData.receiver);
					list.add(listData);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					LogUtil.e(TAG, "err=" + e.getMessage());
					e.printStackTrace();
				}
			}

		});

	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		HttpUser.cancel(context);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.send_btn:
				content_str = sendtext.getText().toString();
				sendtext.setText("");
				String droph;
				if (!TextUtils.isEmpty(content_str)){
					String dropk=content_str.replace(" ", "");
					droph=dropk.replace("\n", "");
				}else {
					droph = "";
					content_str = "";
				}
				ListData listdata = null;
				listdata = new ListData(content_str, listdata.send);
				list.add(listdata);
				if(list.size()>30){
					for(int i=0;i<list.size();i++){
						list.remove(i);
					}
				}
				adapter.notifyDataSetChanged();
				gainData(droph);
				break;
		}
	}


	private String  getRandomWelcomeTips(){
		String welcome_tipe;
		welcomeArray = getResources().getStringArray(R.array.welcome_tips);
		int index = (int) (Math.random()*(welcomeArray.length-1));
		welcome_tipe = welcomeArray[index];
		return welcome_tipe;
	}


}
