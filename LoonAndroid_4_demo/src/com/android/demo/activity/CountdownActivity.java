package com.android.demo.activity;

import android.app.Activity;
import android.widget.TextView;

import com.android.demo.module.TimeModule;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.countdown.IocCountDownTimer.TimeEntity;
import com.loonandroid.pc.handler.Handler_Time;
import com.loonandroid.pc.tinybus.Subscribe;

/**
 * 倒计时 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月9日 下午5:29:09
 */
@InLayer(R.layout.activity_countdown)
public class CountdownActivity extends Activity {

	@InModule
	TimeModule test;

	@InView
	TextView my_time;

	long startTime = 0, limitTime = 0;

	@Init
	private void init() {
		Handler_Time time = Handler_Time.getInstance();
		startTime = time.getTimeInMillis();
		limitTime = (long) (60 * 1000);
		//----------------------------------------------------
		//倒计时模块
		test.setLimtTime(startTime, limitTime);
	}

	/**
	 * 自己实现倒计时
	 * @author gdpancheng@gmail.com 2015年12月4日 下午5:46:25
	 * @param event
	 * @return void
	 */
	@Subscribe
	public void event(TimeEntity event) {
		if (startTime == 0 || limitTime == 0) {
			return;
		}
		long s = (startTime + limitTime) - System.currentTimeMillis();
		if (s < 0) {
			my_time.setText("时间已到");
			return;
		}
		my_time.setText(Handler_Time.formatDuring(s));
	}
}
