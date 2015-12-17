package com.android.demo.activity;

import android.app.Activity;
import android.content.Intent;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;

/**
 * 欢迎页面 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月14日 下午11:26:47
 */
@InLayer(R.layout.activity_first)
public class WelecomeActivity extends Activity {

	/**
	 * Init注解表示View初始化完毕后执行的方法<br>
	 * InBack注解该方法运行在后台进程(该方法不能是private 不能是final)<br>
	 * 
	 * @author gdpancheng@gmail.com 2015年1月5日 下午5:46:34
	 * @return void
	 * @throws InterruptedException
	 */
	@Init@InBack
	protected void init() throws InterruptedException {
		Thread.sleep(3000);
		startActivity(new Intent(WelecomeActivity.this, MenuActivity.class));
		finish();
	}
}
