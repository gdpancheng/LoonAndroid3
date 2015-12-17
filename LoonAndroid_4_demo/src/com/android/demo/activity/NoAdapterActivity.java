package com.android.demo.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.interfaces.LoonViewDeal;
import com.loonandroid.pc.listener.OnClick;

/**
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月19日 下午1:18:53
 */
@InLayer(value = R.layout.activity_list)
public class NoAdapterActivity extends CommonActivity implements OnClickListener {

	@InAll(@InBinder(method = "onClick", listener = OnClick.class))
	Views views;

	private BaseAdapter mAdapter;
	private ArrayList<Good> mListItems;

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
		mListItems = loon.getData();
		//----------------------------------------------------------------------------------
		//模拟数据
		for (String title : mStrings) {
			Good good = new Good();
			good.setTv_test(title);
			mListItems.add(good);
		}
		//----------------------------------------------------------------------------------
		loon.setDeal(deal);
	}

	LoonViewDeal<Good> deal = new LoonViewDeal<Good>() {
		@Override
		public boolean dealView(Good t, ViewHolder viewHolder) {
			viewHolder.setData(R.id.bt_test, "按钮" + viewHolder.getPosition()).setOnClickListener(NoAdapterActivity.this);
			return false;
		}
	};

	@Override
	public void onClick(View arg0) {
		Toast.makeText(NoAdapterActivity.this, ((Button) arg0).getText(), Toast.LENGTH_SHORT).show();
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
