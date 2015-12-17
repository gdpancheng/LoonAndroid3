package com.android.demo.module;

import java.util.ArrayList;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonCallback;
import com.loonandroid.pc.interfaces.LoonModule;
import com.loonandroid.pc.listener.OnClick;

/**
 * 2.2系统中组件的方法必须为public <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月17日 下午8:06:48
 */
@InModule(R.layout.activity_com)
public abstract class Test implements LoonModule {

	@InAll(@InBinder(listener = OnClick.class, method = "click"))
	Views views;

	class Views {
		Button top, bottom;
	}

	/**
	 * 该方法运行在后台 不能是私有和final
	 * @author gdpancheng@gmail.com 2015年5月22日 下午8:03:05
	 * @return void
	 */
	@Init@InBack
	public void init() {
		try {
	        Thread.sleep(5000);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		views.top.setText("后台进程报错");
	}
	
	
	private void click(View v) {
		ArrayList<String> test = getData();
		LoonCallback callback = getCallBack();
		if (callback != null) {
			callback.callbck(v);
		}
		Toast.makeText(views.top.getContext(), "点击了", Toast.LENGTH_SHORT).show();
	}

}
