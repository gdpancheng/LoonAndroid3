package com.android.demo.activity;

import android.app.Activity;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;

/**
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月9日 下午5:29:09
 */
@InLayer(R.layout.activity_inlayer)
public class InLayerActivity extends Activity {

	// --------------------------------------------------------------------------------------------
	// InLayer代替了setContentView
	// 1 InLayer必须带有参数，且参数必须为布局layout
	// 2 无需setContentView和onCreate
}
