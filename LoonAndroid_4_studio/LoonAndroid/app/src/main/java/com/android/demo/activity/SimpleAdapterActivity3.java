package com.android.demo.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.CommonAdapter;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;

@InLayer(value = R.layout.activity_list)
public class SimpleAdapterActivity3 extends CommonActivity implements OnClickListener {

	@InAll
	Views views;
	ArrayList<Good> arrayList = new ArrayList<Good>();

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
			arrayList.add(good);
		}
		// ----------------------------------------------------------------------------------
		views.list.setAdapter(new CommonAdapter<Good>(this, arrayList, R.layout.simple_list_item_1) {
			@Override
			public void convert(ViewHolder helper, Good item) {
				helper.setData(R.id.tv_test, item.getTv_test());
				helper.getView(R.id.bt_test).setOnClickListener(SimpleAdapterActivity3.this);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(SimpleAdapterActivity3.this, ((Button) arg0).getText(), Toast.LENGTH_SHORT).show();
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };
}
