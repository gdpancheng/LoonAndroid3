package com.android.demo.activity;

import android.app.Activity;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InDestroy;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InNewIntent;
import com.loonandroid.pc.annotation.InPause;
import com.loonandroid.pc.annotation.InRestart;
import com.loonandroid.pc.annotation.InResume;
import com.loonandroid.pc.annotation.InStart;
import com.loonandroid.pc.annotation.InStop;
import com.loonandroid.pc.annotation.Init;

/**
 * activity生命周期注解<br>
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
@InLayer(R.layout.activity_life)
public class LifeCyCleActivity extends Activity {

	// --------------------------------------------------------------------------------------------
	// 生命周期注解
	// ---------------------------------------------------------
	// 1 init代替了OnCreate
	// 2 InNewIntent代替了OnNewIntent
	// 3 InPause代替了OnPause
	// 4 InResume代替了OnResume
	// 5 InRestart代替了OnRestart
	// 6 InStart代替了OnStart
	// 7 InStop代替了OnStop
	// 7 InDestroy代替了OnDestroy
	// ---------------------------------------------------------
	// 方法名随意，只需要在方法上加上以上注解即可

	@Init
	void creat() {
		System.out.println("onCreate");
	}

	@InNewIntent
	void newIntent() {
		System.out.println("InPause代替了OnNewIntent");
	}

	@InPause
	void pause() {
		System.out.println("InPause代替了OnPause");
	}

	@InRestart
	void restart() {
		System.out.println("InPause代替了OnRestart");
	}

	@InStart
	void start() {
		System.out.println("InStart代替了OnStart");
	}

	@InResume
	void resume() {
		System.out.println("InStart代替了OnStop");
	}
	
	@InStop
	void stop() {
		System.out.println("InStart代替了OnStop");
	}

	@InDestroy
	void destroy() {
		System.out.println("InStart代替了OnDestroy");
	}
}
