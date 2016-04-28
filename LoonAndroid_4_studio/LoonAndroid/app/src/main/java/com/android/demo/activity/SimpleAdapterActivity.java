package com.android.demo.activity;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;

@InLayer(value = R.layout.activity_list)
public class SimpleAdapterActivity extends CommonActivity{
	@InAll
	Views views;

	private BaseAdapter mAdapter;

	class Views {
		TextView test;
		Button top;
		@InView(item = R.layout.simple_list_item_1)
		ListView list;
	}

	@Init
	void init() {
		mAdapter = (BaseAdapter) views.list.getAdapter();
		LoonAdapter<Good> loon = (LoonAdapter<Good>) mAdapter;
		// ----------------------------------------------------------------------------------
		// 模拟数据
		for (String title : mStrings) {
			Good good = new Good();
			good.setTv_test(title);
			loon.getData().add(good);
		}
		// ----------------------------------------------------------------------------------
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
