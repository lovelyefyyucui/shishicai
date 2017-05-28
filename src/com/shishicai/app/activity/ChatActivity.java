package com.shishicai.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.fragment.Chat;
import com.shishicai.app.utils.BaseActivity;

/**
 * 客服
 */
public class ChatActivity extends BaseActivity {
    private TextView title;
    private ImageView titleimgLeft; // 标题信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TAG = "CaptureActivity";
        title = (TextView) findViewById(R.id.title_top);
        title.setText("客服");
        titleimgLeft = (ImageView) findViewById(R.id.title_left);
        titleimgLeft.setImageResource(R.drawable.top_back);
        Chat chat = new Chat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_chat, chat).commit();
        titleimgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}