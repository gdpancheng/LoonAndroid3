package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.adapter.HomeBannerAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.view.CircleFlowIndicator;
import com.demo.view.ViewFlow;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.handler.Handler_Ui;
import com.loonandroid.pc.listener.OnClick;

/**
 * 品牌介绍 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 下午3:36:59
 */
@InLayer(value = R.layout.introduction_info, parent = R.id.center)
public class IntroductionInfoActivity extends BaseActivity {

	@InAll
	Views views;
	String supplierid;
	private ArrayList<HashMap<String, Object>> gallery;
	@InBean(R.layout.home_banner_item)
	HomeBannerAdapter adapter;

	static class Views {
		ViewFlow good_image;
		CircleFlowIndicator circle;
		@InBinder(listener = OnClick.class, method = "click")
		TextView message, server;
		TextView title, content, name;
		View frame;
	}

	@Init
	private void init() {
		setBottom(R.id.list);
		setTitle("品牌详情");

		supplierid = getIntent().getStringExtra("supplierid").trim();
		gallery = adapter.getData();
		views.good_image.setAdapter(adapter);
		views.good_image.setFlowIndicator(views.circle);
		Handler_Ui.resetLLBack(views.frame);

		supplier();
	}

	@InHttp
	public void result(Result result) {
		progressDimss();
		// 判断请求是否成功
		if (!result.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		
		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
		if (http_data.containsKey("status") && http_data.get("status").equals("1")) {
			HashMap<String, Object> data = (HashMap<String, Object>) http_data.get("data");
			if (data.containsKey("name")) {
				views.title.setText(data.get("name").toString());
				views.name.setText(data.get("name").toString());
			}
			if (data.containsKey("intro")) {
				views.content.setText(Html.fromHtml(data.get("intro").toString()));
			}
			if (data.containsKey("gallery")) {
				Object object = data.get("gallery");
				if (object instanceof ArrayList) {
					gallery.addAll((ArrayList<HashMap<String, Object>>) object);
				}
				if (gallery.size() == 0) {
					gallery.add(new HashMap<String, Object>());
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 收藏
	 * 
	 * @author gdpancheng@gmail.com 2015年12月10日 下午12:09:33
	 * @return void
	 */
	private void supplier() {
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", supplierid);
		App.http.u(this).supplier(params);
	}
	
	public void click(View v) {
		switch (v.getId()) {
		case R.id.message:
//			Intent intent = new Intent(this, ShopMessageActivity.class);
//			intent.putExtra("id", supplierid);
//			startActivity(intent);
			break;
		case R.id.server:
//			startActivity(new Intent(this, ServerActivity.class));
			break;
		default:
			break;
		}
	}
}
