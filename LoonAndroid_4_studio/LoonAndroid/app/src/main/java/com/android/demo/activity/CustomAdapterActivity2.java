package com.android.demo.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.adapter.CustomAdapter2;
import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.listener.OnClick;

/**
 * 自动注入了一个自定义适配器 该自定义适配器没有实现LoonAdapter接口
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年1月11日 下午4:45:32
 */
@InLayer(value = R.layout.activity_list)
public class CustomAdapterActivity2 extends CommonActivity implements OnClickListener {

	/**
	 * 对View布局进行自动注入
	 */
	@InAll(@InBinder(method = "onClick", listener = OnClick.class))
	Views views;

	@InBean(R.layout.simple_list_item_1)
	private CustomAdapter2 mAdapter;

	/**
	 * 包含了当前类布局中 所需要find出来的所有的View
	 * 	<br>-----------------------------------------------
	 * <br>oooO...............
	 * <br>(....) ... Oooo...
	 * <br>.\..(.....(.....).......
	 * <br>..\_)..... )../........
	 * <br>.......... (_/..........
	 * <br>微信 gdpancheng
	 * <br>-----------------------------------------------
	 * @author gdpancheng@gmail.com 2015年1月11日 下午4:47:12
	 */
	class Views {
		TextView test;
		Button top;
		ListView list;
	}

	@Init
	void init() {
		// ----------------------------------------------------------------------------------
		// 模拟数据 模拟了N条数据
		for (String title : mStrings) {
			Good good = new Good();
			good.setTv_test(title);
			((LoonAdapter<Good>) mAdapter).getData().add(good);
		}
		// ----------------------------------------------------------------------------------
		//把当前类的点击事件传递给注入的adapter
		mAdapter.setL(this);
		views.list.setAdapter(mAdapter);
	}

	/**
	 * listview item的点击事件会走这里
	 */
	@Override
	public void onClick(View arg0) {
		Toast.makeText(CustomAdapterActivity2.this, ((Button) arg0).getText(), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 数据源
	 */
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
