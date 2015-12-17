package com.android.demo.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_File;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2015年1月3日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
@InLayer(R.layout.activity_source)
public class SourceActivity extends Activity {

	@InView
	private WebView vb_webview;

	@Init
	private void init() {

		Intent intent = getIntent();
		String file = intent.getStringExtra("file");

		try {
			String body = Handler_File.getAsString(getResources().getAssets().open("code/activity/" + file + ".java"));

			String html = Handler_File.getAsString(getResources().getAssets().open("index.html"));

			vb_webview.getSettings().setJavaScriptEnabled(true);
			vb_webview.getSettings().setDefaultTextEncodingName("UTF-8");
			vb_webview.getSettings().setSupportZoom(true);
			vb_webview.getSettings().setBuiltInZoomControls(true);
			vb_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			vb_webview.loadDataWithBaseURL("file:///android_asset/", html.replace("{{code}}", body + "\r\n"), "text/html", "utf-8", null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
