package com.android.demo.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InListener;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.tinybus.Subscribe;
import com.loonandroid.pc.tinybus.Subscribe.Mode;
import com.loonandroid.pc.tinybus.TinyBus;

/**
 * 只有activity和fragment中可以不用注册tinybus<br>
 * 其他类中需要自己手动绑定以及解绑<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).register(object)}<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).unregister(object)}<br>
 * 请参考{@link  com.android.demo.module.TimeModule}<br>
 * 发送消息请用{@link  TinyBus.send(object)}<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年1月11日 下午4:32:24
 */
@InLayer(R.layout.activity_com)
public class BusFragment extends Fragment {

	@InView(binder = @InBinder(listener = OnClick.class, method = "click"))
	Button top;
	@InView
	TextView tv_top;

	@Init
	void init() {
		top.setText("点击发送跨进程消息");
		System.out.println("fragment 初始化完毕");
	}

	@InBack
	private void click(View view) {
		TinyBus.send(new Test());
	}

	class Test {
	}

	@Subscribe(mode = Mode.Background)
	public void event(Test event) {
		System.out.println("这里是后台进程");
	}

	@Subscribe
	public void event2(Test event) {
		tv_top.setText("点击之后");
		System.out.println("这里是前台进程");
	}

	/**
	 * 对view的view进行事件绑定 其中ids是需要绑定事件的View的ID，后面为事件类型 TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2015年11月6日 下午6:00:52
	 * @param view
	 * @return void
	 */
	@InListener(ids = { R.id.bottom }, listeners = { OnClick.class })
	private void l(View view) {
		Toast.makeText(view.getContext(), "父类中点击了", Toast.LENGTH_SHORT).show();
	}
}
