package com.android.demo.activity;

import com.android.demo.fragment.AutoFragment;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;

/**
 * 自动创建AutoFragment 尽量创建无参构造器的AutoFragment<br>
 * 如果需要传递参数进去，尽量用set和get方法代替<br>
 * 如果真的需要有参构造器，请参考ParamFragmentActivity
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
public class AutoFragmentActivity extends CommonActivity {

	/**
	 * {@link InBean}创建了一个Fragment 无需方法onCreateView
	 */
	@InBean
	private AutoFragment fragment;

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
