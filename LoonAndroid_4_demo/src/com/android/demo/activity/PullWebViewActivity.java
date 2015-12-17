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
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@InLayer(R.layout.activity_ptr_webview)
public final class PullWebViewActivity extends Activity {

	@InView(pull = true, down = true)
	WebView vb_webview;
	
	@Init
	void init() {
		vb_webview.getSettings().setJavaScriptEnabled(true);
		vb_webview.loadUrl("http://www.baidu.com");
		vb_webview.setWebViewClient(new WebViewClient(){

		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        // TODO Auto-generated method stub
		        view.loadUrl(url);
		        return true;
		      }
		    });
	}

	@InPullRefresh
	private void refresh(final PullToRefreshBase<?> refreshView, Pull type) {
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.onRefreshComplete();
			}
		}, 2 * 1000);
	}
}
