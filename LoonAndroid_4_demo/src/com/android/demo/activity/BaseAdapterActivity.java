package com.android.demo.activity;

import java.util.Arrays;
import java.util.HashMap;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.interfaces.LoonViewDeal;
import com.loonandroid.pc.listener.OnClick;

@InLayer(value = R.layout.activity_list)
public class BaseAdapterActivity extends CommonActivity implements OnClickListener {

	@InAll(@InBinder(method = "onClick", listener = OnClick.class))
	Views views;

	@InBean(R.layout.simple_list_item_1)
	private BaseAdapter mAdapter;

	class Views {
		TextView test;
		Button top;
		ListView list;
	}

	@Init
	void init() {
		//从BaseAdapter中获得回掉自定义处理对象
		LoonAdapter<HashMap<String, String>> loon = (LoonAdapter<HashMap<String, String>>) mAdapter;
		//然后设置设置View的拦截处理方法
		loon.setDeal(deal);
		// ----------------------------------------------------------------------------------
		// 模拟数据
		for (String title : mStrings) {
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("tv_test", title);
			loon.getData().add(data);
		}
		// ----------------------------------------------------------------------------------
		views.list.setAdapter(mAdapter);
	}

	
	LoonViewDeal<HashMap<String, String>> deal = new LoonViewDeal<HashMap<String, String>>() {
		@Override
		public boolean dealView(HashMap<String, String> t, ViewHolder viewHolder) {
			viewHolder.setData(R.id.bt_test, "按钮" + viewHolder.getPosition()).setOnClickListener(BaseAdapterActivity.this);
			return false;
		}
	};

	@Override
	public void onClick(View arg0) {
		Toast.makeText(BaseAdapterActivity.this, ((Button) arg0).getText(), Toast.LENGTH_SHORT).show();
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
