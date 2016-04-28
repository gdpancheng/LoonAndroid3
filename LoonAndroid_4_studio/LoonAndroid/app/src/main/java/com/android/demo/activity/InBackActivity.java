package com.android.demo.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;

/**
 * 后台进行 加了@inback 会自动创建一个后台进程 该方法会在新的线程中运行<br>
 * 目前只支持activity<br>
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
@InLayer(R.layout.simple_list_item_1)
public class InBackActivity extends Activity {

	@InView(binder = @InBinder(method = "click", listener = OnClick.class))
	Button bt_test;
	@InView
	TextView tv_test;
	
	@Init
	public void init(){
		bt_test.setText("点击弹不出toast，因为是后台进程");
	}
	
	@InBack
	public void click(View v) {
		Toast.makeText(InBackActivity.this, "弹出Toast", Toast.LENGTH_LONG).show();
		System.out.println("这里是后台进程");
	}
}
