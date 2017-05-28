package com.shishicai.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishicai.R;
import com.shishicai.app.utils.Base2Activity;

public class AboutAsActivity extends Base2Activity implements OnClickListener {

	private TextView title;
	private ImageView titleimgLeft; // 标题信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		TAG = "AboutAsActivity";
		title = (TextView) findViewById(R.id.title_top);
		title.setText("关于");
		titleimgLeft = (ImageView) findViewById(R.id.title_left);
		titleimgLeft.setImageResource(R.drawable.top_back);
		titleimgLeft.setOnClickListener(this);
		findViewById(R.id.introduction_ll).setOnClickListener(this);
		findViewById(R.id.help_center_ll).setOnClickListener(this);
		findViewById(R.id.declare_ll).setOnClickListener(this);
		findViewById(R.id.about_us_ll).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		String title = "", content = "";
		switch (v.getId())
		{
			case R.id.introduction_ll:
				intent = new Intent(this, AboutActivity.class);
				title = "应用简介";
				content = "北京赛车pk10s是一款集历史开奖、统计信息、推荐数据于一体的赛车信息应用。\n北京赛车pk10通过大量的数据，以及运用自主研发的分析开奖结果的方法，实现了北京赛车各玩法的推荐。\n北京赛车pk10s是具有资源占有低、操作简捷、资料齐全等特点，是目前国内受欢迎的全能北京赛车信息应用。\n临沧信安富创投科技有限公司是专业研发各种棋牌游戏及彩票统计相关的科技公司，如果阁下对北京赛车有独特的预测经验或计算方法，我们很乐意与您洽谈，并按您的想法开发出自动统计及模拟程序供您使用，同时本公司的专业团队也提供各方面的建议及更多的经验分享。";
				break;

			case R.id.help_center_ll:
				intent = new Intent(this, AboutActivity.class);
				title = "帮助中心";
				content = "1、这是什么软件？\n北京赛车pk10s应用是一款集历史开奖、统计信息、推荐数据及随机做号等数据功能于一体的应用软件，通过软件可以查看即时的开奖结果与查询分析数据作为投注参考。\n2、这软件怎样用？\n北京赛车pk10提供多种数据统计，每类型数据统计都有各自的参考价值，能更好的辅助选号。\n3，什么是历史开奖?\n历史开奖是统计所选择日期里面的开奖结果，包括今日开奖，往日开奖。\n4，数据统计\n数据统计实时统计单号码的名次出现占比等数据，以及冠亚和的各个号码和的统计数据。\n5，走势图\n走势图可以按名次筛选显示近期期数对应的开奖结果的单号码。";
				break;

			case R.id.declare_ll:
				intent = new Intent(this, AboutActivity.class);
				title = "免责声明";
				content = "\t北京赛车pk10s是临沧信安富创投科技有限公司研发的一款统计资讯推荐数据的应用，此应用仅供中国大陆投注北京赛车进行相关的参考，对于使用本应用出现的任何问题，临沧信安富创投科技有限公司不承担责任。\n用户必须留意本身所处之地区及相关法律，不得利用本软件进行任何非法活动，任何情况下导致触犯所属地区之法律，用户须自行承担责任，一切后果临沧信安富创投科技有限公司概不负责。";
				break;

			case R.id.about_us_ll:
				intent = new Intent(this, AboutActivity.class);
				title = "关于我们";
				content = "临沧信安富创投科技有限公司的愿景：最受欢迎的娱乐互联网企业\n临沧信安富创投科技有限公司的使命：通过互联网服务提升娱乐生活新品质\n临沧信安富创投科技有限公司肩负着重要的使命，美好的愿景，不断的努力，提供科技化的人性服务，开拓市场新领土。公司一直持续的进行市场资讯的收集和研究，持续拓展业务和开拓全新的服务领域，加强发展技术，至力于新产品的开发、合作。我们每一项产品和软件设计思念，都要求最简单最实用最方便，所以大大的满足用户和家户的娱乐要求，我们不断的为目标市场创造机会和话题，将新产品推向我们的合作伙伴、用户，创造双赢、多赢的局面。";
				break;

			case R.id.title_left:
				finish();
				break;
		}
		if (intent != null)
		{
			intent.putExtra("title", title);
			intent.putExtra("content", content);
			startActivity(intent);
		}
	}



}
