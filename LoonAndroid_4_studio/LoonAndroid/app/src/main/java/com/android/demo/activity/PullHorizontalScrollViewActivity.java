package com.android.demo.activity;

import android.app.Activity;
import android.widget.HorizontalScrollView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@InLayer(R.layout.activity_horizontalscrollview)
public class PullHorizontalScrollViewActivity extends Activity {

	@InView(pull = true, down = true)
	HorizontalScrollView pull_refresh_horizontalscrollview;

	private PullToRefreshBase<?> pullToRefreshBase;

	@Init
	void init() {
		System.out.println(pull_refresh_horizontalscrollview.getChildCount());
	}

	@InPullRefresh
	@InBack
	public void refresh(PullToRefreshBase<?> refreshView, Pull type) throws InterruptedException {
		pullToRefreshBase = refreshView;
		Thread.sleep(4000);
		getData();
	}

	@InUI
	public void getData() {
		pullToRefreshBase.onRefreshComplete();
	}
}
