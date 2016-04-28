package com.android.demo.activity;

import com.android.demo.fragment.ParamFragment;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.BeanFactory;

/**
 * 有参的Fragment <br>
 * 如果无参可以直接使用 BeanFactory.instanceFragment(ParamFragment.class)代替new ParamFragment()
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月19日 下午1:17:32
 */
@InLayer(value = R.layout.activity_fragment)
public class ParamFragmentActivity extends CommonActivity {

	private ParamFragment fragment;

	@Init
	void init() {
		fragment = BeanFactory.instanceFragment(ParamFragment.class, "sdfadsfasfd");
		startFragmentAdd(fragment);
	}
}
