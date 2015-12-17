package com.android.demo.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InSource;
import com.loonandroid.pc.annotation.Init;

/**
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
@InLayer(R.layout.activity_inlayer)
public class InSourceActivity extends Activity {

	// --------------------------------------------------------------------------------------------
	// InSource可以自动取出xml中的资源进行填充
	// 1 资源名称要和变量名一样
	// 2 支持Drawable，String，String[]
	@InSource
	Drawable ic_launcher;
	@InSource
	String hello_world;

	@Init
	void init() {
		System.out.println(ic_launcher);
		System.out.println(hello_world);
	}
}
