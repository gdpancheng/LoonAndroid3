/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.android.demo.activity;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.android.demo.adapter.SamplePagerAdapter;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@InLayer(R.layout.activity_ptr_viewpager)
public class PullViewPagerActivity extends Activity {

	@InView(pull = true, down = true)
	ViewPager vp_viewpager;

	private PullToRefreshBase<?> pullToRefreshBase;

	@Init
	void init() {
		vp_viewpager.setAdapter(new SamplePagerAdapter());
	}

	@InPullRefresh@InBack
	public void refresh(PullToRefreshBase<?> refreshView, Pull type) throws InterruptedException {
		pullToRefreshBase = refreshView;
		Thread.sleep(4000);
		finisRefresh();
	}
	@InUI
	public void finisRefresh(){
		pullToRefreshBase.onRefreshComplete();
	}
}
