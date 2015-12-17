package com.android.demo.activity;

import android.app.Activity;
import android.widget.ScrollView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@InLayer(R.layout.activity_scrollview)
public class PullScrollViewActivity extends Activity {

	@InView(pull = true, down = true)
	ScrollView pull_refresh_scrollview;

	private PullToRefreshBase<?> pullToRefreshBase;
	
	@InPullRefresh@InBack
	public void refresh(PullToRefreshBase<?> refreshView, Pull type) throws InterruptedException {
		pullToRefreshBase = refreshView;
		Thread.sleep(4000);
		finishRefresh();
	}

	@InUI
	public void finishRefresh(){
		pullToRefreshBase.onRefreshComplete();
	}
}
