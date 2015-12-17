package com.android.demo.activity;

import java.util.HashMap;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;

@InLayer(value = R.layout.activity_list)
public class SimpleAdapterActivity2 extends CommonActivity {

	@InAll
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
		LoonAdapter<HashMap<String, String>> loon = (LoonAdapter<HashMap<String, String>>) mAdapter;
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

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
