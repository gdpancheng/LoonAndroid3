package com.android.demo.activity;

import java.util.ArrayList;


import com.android.demo.module.Test;
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
@InLayer(value = R.layout.activity_module, parent = R.id.common)
public class ModuleActivity extends CommonActivity {

	@InModule
	Test test;

	// --------------------------------------------------------------------------------------------

	@Init
	void init() {
		ArrayList<String> data = new ArrayList<String>();
		data.add("test");
		data.add("test1");
		test.setData(data);
		test.setCallBack(new LoonCallback() {
			@Override
			public void callbck(Object... objects) {
				System.out.println("-----------------------------");
			}
		});
	}
}
