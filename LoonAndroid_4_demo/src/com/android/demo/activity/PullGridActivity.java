package com.android.demo.activity;

import android.app.Activity;
import android.widget.LinearLayout;

import com.android.demo.module.ViewManager;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonModule;

@InLayer(value = R.layout.activity_pull_grid)
public class PullGridActivity extends Activity {

	@InView
	LinearLayout test;
	
	/**这里是一个组件**/
	@InModule
	ViewManager viewManager;
	
	@Init
	void init() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		test.addView(((LoonModule) viewManager).getView(),layoutParams);
	}
}
