package com.android.demo.module;

import java.util.ArrayList;
import java.util.Arrays;

import android.view.View;
import android.widget.GridView;

import com.android.demo.adapter.GridAdapter;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-18
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

@InModule(R.layout.activity_grid)
public class ViewManager implements View.OnClickListener {

	@InAll(@InBinder(method = "onClick", listener = OnClick.class))
	Views views;
	@InBean(R.layout.simple_list_item_1)
	private GridAdapter mAdapter;

	private ArrayList<String> mListItems;
	private PullToRefreshBase<GridView> pullToRefreshBase;

	class Views {
		@InPullRefresh(pull = true, down = true)
		GridView test;
	}

	@Init
	void init() {
		views.test.setNumColumns(3);
		mListItems = mAdapter.getData();
		mListItems.addAll(Arrays.asList(mStrings));
		views.test.setAdapter(mAdapter);
	}

	public void onClick(View v) {
		System.out.println("ViewManager 点击：" + v.toString());
	}

	@InPullRefresh
	private void refresh(PullToRefreshBase<GridView> refreshView, Pull type) throws InterruptedException {
		pullToRefreshBase = refreshView;
		get();
	}

	@InBack
	public void get() throws InterruptedException {
		Thread.sleep(4000);
		showData();
	}

	@InUI
	public void showData() {
		mListItems.add(0, "新增条目");
		mAdapter.notifyDataSetChanged();
		pullToRefreshBase.onRefreshComplete();
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
