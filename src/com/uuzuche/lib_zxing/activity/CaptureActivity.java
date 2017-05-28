package com.uuzuche.lib_zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//import android.support.v7.app.AppCompatActivity;

import com.shishicai.R;
import com.shishicai.app.activity.ScanResultShow;
import com.shishicai.app.utils.BaseActivity;

/**
 * Initial the camera
 *
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends BaseActivity {
    private TextView title;
    private ImageView titleimgLeft; // 标题信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        TAG = "CaptureActivity";
        title = (TextView) findViewById(R.id.title_top);
        title.setText("二维码扫描");
        titleimgLeft = (ImageView) findViewById(R.id.title_left);
        titleimgLeft.setImageResource(R.drawable.top_back);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        titleimgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent intent = new Intent(CaptureActivity.this, ScanResultShow.class);
            Bundle b = new Bundle();
            b.putString("result", result);
            intent.putExtras(b);
            startActivity(intent);
        }

        @Override
        public void onAnalyzeFailed() {
            Intent intent = new Intent(CaptureActivity.this, ScanResultShow.class);
            Bundle b = new Bundle();
            b.putString("result", "扫描失败");
            intent.putExtras(b);
            startActivity(intent);
            CaptureActivity.this.finish();
        }
    };
}