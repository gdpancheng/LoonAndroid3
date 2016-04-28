package com.android.demo.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.tinybus.Subscribe;
import com.loonandroid.pc.tinybus.Subscribe.Mode;
import com.loonandroid.pc.tinybus.TinyBus;

/**
 * 这里是跨进程通讯实例，框架集成了tinybus <br>
 * 只有activity和fragment中可以不用注册tinybus<br>
 * 其他类中需要自己手动绑定以及解绑<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).register(object)}<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).unregister(object)}<br>
 * 请参考{@link  com.android.demo.module.TimeModule}<br>
 * 发送消息请用{@link  TinyBus.send(object)}<br>
 * <br>----------------------------------------------- <br>
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
public class InBusActivity extends Activity {

	@InView(binder = @InBinder(method = "click", listener = OnClick.class))
	Button bt_test;
	@InView
	TextView tv_test;

	TinyBus bus;

	@Init
	public void init() {
		bt_test.setText("点击之前");
	}

	@InBack
	public void click(View v) {
		TinyBus.send(new User());
	}

	class User {
	}

	@Subscribe(mode = Mode.Background)
	public void event(User event) {
		System.out.println("这里是后台进程");
	}

	@Subscribe
	public void event2(User event) {
		bt_test.setText("点击之后");
		System.out.println("这里是前台进程");
	}
}
