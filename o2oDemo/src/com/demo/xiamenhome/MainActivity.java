package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.adapter.HomeBannerAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.view.CircleFlowIndicator;
import com.demo.view.PagerScrollView;
import com.demo.view.ViewFlow;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InNewIntent;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.handler.Handler_Ui;
import com.loonandroid.pc.listener.OnClick;

/**
 * 主页 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 上午11:39:44
 */
@InLayer(value = R.layout.home, parent = R.id.center)
public class MainActivity extends BaseActivity {

	@InAll
	Views view;

	@InBean(R.layout.home_banner_item)
	HomeBannerAdapter adapter;

	private ArrayList<HashMap<String, Object>> banner;

	// ----------------------------------------------------------------------------
	// view类
	static class Views {
		EditText input;
		ViewFlow baner;// 广告图
		CircleFlowIndicator circle;
		RelativeLayout frame;
		ImageView image;// 中间的图片
		@InBinder(listener = OnClick.class, method = "click")
		View search_bt,view_woshi, view_chuanglian, view_diban, view_yigui, view_chuang, view_keting, view_dengshi, view_shafa, view_chaji, view_bizhi, view_yushi, view_longtou, view_chizhuan, view_wujing, view_zuobianqi, view_yushigui, view_taipen, view_cufang, view_chudian, view_canzuo, view_chugui, view_yizi, look_keting, look_yushi, look_chufang, look_woshi;
		@InBinder(listener = OnClick.class, method = "click")
		TextView shop;
		PagerScrollView scrollview;
	}

	@InNewIntent
	public void onNew(Intent intent) {
		if (intent.hasExtra("exist")) {
			finish();
		}
	}

	// -------------------------------------------------------------初始化 类似oncreate---------------------------------------------
	@Init
	protected void init() {
		setBottom(R.id.home);
		if (getIntent().hasExtra("exist")) {
			finish();
		}
		hideTop();
		// ----------------------------------------------------------------------------------------------------------
		banner = adapter.getData();
		view.baner.setAdapter(adapter);
		view.baner.setFlowIndicator(view.circle);
		// -----------------------------------------------------以下是对首页进行适配 以保证不同分辨率下都可以点击到按钮---------------------------------------------------------------------------
		// 根据屏幕重新绘制位置
		Handler_Ui.resetRLBack(view.image);
		Handler_Ui.resetLLBack(view.frame);

		Handler_Ui.resetRL(view.view_woshi, view.look_keting, view.look_chufang, view.look_yushi, view.look_woshi, view.view_taipen, view.view_cufang, view.view_chudian, view.view_canzuo, view.view_chugui, view.view_yizi, view.view_chuanglian, view.view_diban, view.view_yigui, view.view_chuang, view.view_keting, view.view_dengshi, view.view_shafa, view.view_chaji, view.view_bizhi, view.view_yushi, view.view_longtou, view.view_chizhuan, view.view_wujing, view.view_zuobianqi, view.view_yushigui);
		Handler_Ui.resetRLBack(view.view_woshi, view.look_keting, view.look_chufang, view.look_yushi, view.look_woshi, view.view_taipen, view.view_cufang, view.view_chudian, view.view_canzuo, view.view_chugui, view.view_yizi, view.view_chuanglian, view.view_diban, view.view_yigui, view.view_chuang, view.view_keting, view.view_dengshi, view.view_shafa, view.view_chaji, view.view_bizhi, view.view_yushi, view.view_longtou, view.view_chizhuan, view.view_wujing, view.view_zuobianqi, view.view_yushigui);
		// 根据屏幕重新绘制大小
		// -----------------------------------------------------异步获取数据---------------------------------------------------------------------------
		// 开始获取数据了
		showProgress();
		App.http.u(this).focus();
	}

	// ---------------------------------------网络请求回调-----------------------------------------------------
	/**
	 * 网络请求回调
	 * 
	 * @author gdpancheng@gmail.com 2015年12月9日 下午4:43:46
	 * @param result
	 * @return void
	 */
	@InHttp
	public void result(Result result) {
		progressDimss();
		// 判断请求是否成功
		if (!result.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		banner.clear();
		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
		if (http_data.containsKey("status") && http_data.get("status").equals("1")) {
			banner.addAll((ArrayList<HashMap<String, Object>>) http_data.get("data"));
			adapter.notifyDataSetChanged();
		}
	}

	// ---------------------------------------点击事件-----------------------------------------------------
	/**
	 * 点击事件
	 * 
	 * @author gdpancheng@gmail.com 2013-7-29 下午5:12:52
	 * @param v
	 * @return void
	 */
	public void click(View v) {
		int cid = 0;
		Object tag = v.getTag();
		if (null != tag) {
			cid = Integer.valueOf(tag.toString());
		}
		if (cid > 0) {
			Intent intent = new Intent(this, GoodsDetailListActivity.class);
			intent.putExtra("cid", cid + "");
			startActivity(intent);
		}
		switch (v.getId()) {
		case R.id.search_bt:
			String keyword = view.input.getText().toString().trim();
			if (keyword.length() > 0) {
				Intent in = new Intent(this, GoodsSearchListActivity.class);
				in.putExtra("keyword", keyword);
				startActivity(in);
			}
			return;
		case R.id.look_chufang:
			view.scrollview.scrollTo(0, (int) (1000 * Handler_System.getWidthRoate()));
			break;
		case R.id.look_keting:
			view.scrollview.scrollTo(0, (int) (300 * Handler_System.getWidthRoate()));
			break;
		case R.id.look_woshi:
			view.scrollview.scrollTo(0, 0);
			break;
		case R.id.look_yushi:
			view.scrollview.scrollTo(0, (int) (600 * Handler_System.getWidthRoate()));
			break;
		case R.id.shop:
			Uri uri = Uri.parse("http://xm.137home.com/index.php?m=index&a=shop");
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(it);
			return;
		}
	}
}
