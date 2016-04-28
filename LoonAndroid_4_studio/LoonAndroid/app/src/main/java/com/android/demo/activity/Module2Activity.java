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
@InLayer(value=R.layout.activity_module2,parent=R.id.common)
public class Module2Activity extends CommonActivity {

	@InModule(parent = R.id.test)
	Test test0;
	@InModule(parent = R.id.test1)
	Test test1;
	@InModule(parent = R.id.test2)
	Test test2;

	// --------------------------------------------------------------------------------------------

	@Init
	void init() {
		ArrayList<String> data = new ArrayList<String>();
		data.add("test");
		data.add("test1");
		test0.setData(data);
		test0.setCallBack(callback);
		test1.setData(data);
		test1.setCallBack(callback);
		test2.setData(data);
		test2.setCallBack(callback);
	}

	LoonCallback callback = new LoonCallback() {
		@Override
		public void callbck(Object... objects) {

		}
	};
}
