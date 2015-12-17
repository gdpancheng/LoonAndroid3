package com.android.demo.activity;

import java.util.HashMap;

import android.app.Activity;

import com.android.demo.module.AHttpModule;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonCallback;

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
@InLayer(R.layout.activity_http_demo)
public class HttpDemoActivity extends Activity {

	@InModule
	AHttpModule module;

	// --------------------------------------------------------------------------------------------

	@Init
	void init() {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		data.put("item", getIntent().getIntExtra("item", 0));
		data.put("index", getIntent().getIntExtra("index", 0));
		module.setData(data);
		module.setCallBack(new LoonCallback() {
			@Override
			public void callbck(Object... objects) {
				System.out.println("-----------------------------");
			}
		});
	}
}
