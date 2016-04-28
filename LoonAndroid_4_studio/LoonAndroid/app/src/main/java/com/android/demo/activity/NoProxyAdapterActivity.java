package com.android.demo.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.adapter.CustomAdapter;
import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;

@InLayer(value = R.layout.activity_list)
public class NoProxyAdapterActivity extends CommonActivity implements OnClickListener {

	@InAll(@InBinder(method = "onClick", listener = OnClick.class))
	Views views;

	@InBean(R.layout.simple_list_item_1)
	private CustomAdapter mAdapter;

	class Views {
		TextView test;
		Button top;
		ListView list;
	}

	@Init
	void init() {
		// ----------------------------------------------------------------------------------
		// 模拟数据
		for (String title : mStrings) {
			Good good = new Good();
			good.setTv_test(title);
			mAdapter.getData().add(good);
		}

		mAdapter.setL(this);
		views.list.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(NoProxyAdapterActivity.this, ((Button) arg0).getText(), Toast.LENGTH_SHORT).show();
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
