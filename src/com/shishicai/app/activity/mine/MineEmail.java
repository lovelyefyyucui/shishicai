package com.shishicai.app.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shishicai.R;

public class MineEmail extends Activity {

	private TextView title,tv;
	private ImageView titleimgLeft; // 标题信息

	private EditText eEmail;
	private Button submit;
	private String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_email);

		// 设置标题图片文字
		title = (TextView) findViewById(R.id.title_top);
		title.setText("编辑邮箱");
		tv = (TextView) findViewById(R.id.mine_et_tv);
		tv.setText("邮箱");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.title_left);
		titleimgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		eEmail = (EditText) findViewById(R.id.mine_et_email);
		submit = (Button) findViewById(R.id.email_submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				email = eEmail.getText().toString().trim();
				if (email.equals("")) {
					Toast.makeText(MineEmail.this, "输入不能为空", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(MineEmail.this,
							MineInformation.class);
					intent.putExtra("email", email);
					MineEmail.this.setResult(51,intent);
					MineEmail.this.finish();
				}
			}
		});
	}
}
