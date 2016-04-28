package com.android.demo.activity;

import com.android.demo.fragment.BusFragment;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;

/**
 * 只有activity和fragment中可以不用注册tinybus<br>
 * 其他类中需要自己手动绑定以及解绑<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).register(object)}<br>
 * 先{@link TinyBus.from(Ioc.getIoc().getApplication()).unregister(object)}<br>
 * 请参考{@link  com.android.demo.module.TimeModule}<br>
 * 发送消息请用{@link  TinyBus.send(object)}<br>
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月19日 下午1:15:51
 */
@InLayer(value = R.layout.activity_fragment)
public class BusFragmentActivity extends CommonActivity {

	/**
	 * {@link InBean}创建了一个Fragment 无需方法onCreateView
	 */
	@InBean
	private BusFragment fragment;

	/**
	 * 当onCreateView执行完毕以后 会调用含有{@link Init}注解的方法
	 * @author gdpancheng@gmail.com 2015年1月11日 下午4:43:00
	 * @return void
	 */
	@Init
	void init() {
		System.out.println(fragment);
		startFragmentAdd(fragment);
	}
}
