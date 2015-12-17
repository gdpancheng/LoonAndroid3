package com.demo.xiamenhome;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.demo.adapter.WelcomePagerAdapter;
import com.demo.view.CircleFlowIndicator;
import com.demo.view.ViewFlow;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_SharedPreferences;

/**
 * 欢迎页面
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年12月10日 上午11:39:55
 */
@InLayer(R.layout.welcome)
public class WelcomeActivity extends Activity implements OnClickListener {

	@InBean(R.layout.welcome_item)
	private WelcomePagerAdapter mAdapter;
	// ----------------------------------------------------------------
	// View
	@InAll
	Views v;

	static class Views {
		public ViewFlow flow;
		public CircleFlowIndicator circle;
	}
	// -------------------------------------------------------------初始化 类似oncreate---------------------------------------------
	@Init
	public void init() {
		if (!getIntent().hasExtra("isHelp")) {
			HashMap<String, Object> data = Handler_SharedPreferences.getAllByBasesName("data");
			if (data.containsKey("isFirst") && Boolean.valueOf(data.get("isFirst").toString())) {
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				finish();
				return;
			}
		}
		mAdapter.setL(this);
		v.flow.setAdapter(mAdapter);
		v.flow.setFlowIndicator(v.circle);
		mAdapter.notifyDataSetChanged();
	}
	// -------------------------------------------------------------点击事件---------------------------------------------
	@Override
	public void onClick(View v) {
		if (getIntent().hasExtra("isHelp")) {
			finish();
		}
		Handler_SharedPreferences.WriteSharedPreferences("data", "isFirst", true);
		startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
		finish();
	}
}
