package com.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.demo.adapter.HttpAdapter;
import com.android.demo.util.Constant;
import com.android.demo.util.Constant.HttpUrl;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.plug.net.ResponseEntity;

/**
 * <h1>演示了所有InAll的用法</h1> <br>
 * 1 InAll可以绑定view <br>
 * 2 InAll可以绑定view的各种事件 <br>
 * 3 InAll可以绑定view的事件有单击 长按 触摸 列表点击 选中等 <br>
 * 4 InAll事件可以覆盖 <br>
 * 5 InAll资源可以解析 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月11日 下午12:31:13
 */
@InLayer(R.layout.activity_menu)
public class HttpActivity extends Activity implements OnItemClickListener {

	@InBean(R.layout.item_menu)
	private HttpAdapter adapter;
	@InView
	private ListView lv_menu;

	@Init
	private void init() {
		adapter.setData(Constant.https);
		adapter.setL(this);
		lv_menu.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("item", Integer.valueOf(parent.getTag().toString()));
		intent.putExtra("index", position);
		intent.setClass(this, HttpDemoActivity.class);
		startActivity(intent);
//		App.http.u(this).login("aaa", "bbb");
	}
	
	@InHttp(HttpUrl.LOGIN_KEY)
	public void result(ResponseEntity entity){
		System.out.println("result-------------"+entity);
	}
}
