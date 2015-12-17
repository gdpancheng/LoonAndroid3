package com.android.demo.activity;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.Ignore;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.interfaces.LoonViewDeal;
import com.loonandroid.pc.listener.OnItemClick;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@SuppressWarnings("unchecked")
@InLayer(value = R.layout.activity_list)
public class PullListViewActivity extends CommonActivity {

	@InAll
	Views views;

	private BaseAdapter mAdapter;
	private ArrayList<String> mListItems;
	private PullToRefreshBase<?> pullToRefreshBase;

	class Views {
		TextView test;
		Button top;
		@Ignore
		@InView(pull = true, down = true, item = R.layout.simple_list_item_1)
		@InBinder(listener = OnItemClick.class, method = "itemClick")
		ListView list;
	}

	@Init
	void init() {
		mAdapter = (BaseAdapter) views.list.getAdapter();
		LoonAdapter<String> loon = (LoonAdapter<String>) mAdapter;
		mListItems = loon.getData();
		loon.setDeal(deal);
	}

	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("点击事件");
	}
	
	LoonViewDeal<String> deal = new LoonViewDeal<String>() {
		@Override
		public boolean dealView(String t, ViewHolder viewHolder) {
			viewHolder.setData(R.id.tv_test, t);
			return true;
		}
	};

	@InPullRefresh
	@InBack
	public void refresh(PullToRefreshBase<?> refreshView, Pull type) throws InterruptedException {
		pullToRefreshBase = refreshView;
		mListItems.add(0, "新增的条目");
		Thread.sleep(4000);
		showData();
	}

	@InUI
	public void showData() {
		mAdapter.notifyDataSetChanged();
		pullToRefreshBase.onRefreshComplete();
	}
}
