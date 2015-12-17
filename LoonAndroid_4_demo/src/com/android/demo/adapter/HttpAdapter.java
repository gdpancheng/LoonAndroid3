package com.android.demo.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.demo.util.HttpEntity;
import com.android.demo.util.MenuEntity;
import com.android.demo.util.MyGridView;
import com.android.demo.util.MyHashMap;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/**
 * 
 * 当前为自动注入的适配器<br>
 * <h1>以下为需要注意的</h1>
 * 写法分为两种<br>
 * 1  CustomAdapter 实现LoonAdapter接口，并声明LoonAdapter接口的泛型
 * 该泛型为你绑定到该Item的来源数据的类型<br>
 * 2  如果你不实现LoonAdapter接口，当这个适配器注入的时候 也会自动实现LoonAdapter接口，
 * 那么你在使用的时候，就必须强转<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月15日 上午10:30:12
 */
public abstract class HttpAdapter extends BaseAdapter implements LoonAdapter<HttpEntity> {

	OnItemClickListener l;

	@Override
	public boolean dealView(HttpEntity http, ViewHolder viewHolder) {
		MyGridView  grid= viewHolder.getView(R.id.gv_menu);
		TextView tv_label = viewHolder.getView(R.id.tv_label);
		tv_label.setTextColor(Color.BLACK);
		tv_label.setText(http.title);
		
		//--------------------------------------------------------------------------
		//创建按钮数据
		String[] mListItems = new String[http.menus.size()];
		int i = 0;
		for (MyHashMap<String, Integer> data : http.menus) {
			mListItems[i++] = (String) data.keySet().iterator().next();
		}
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(grid.getContext(), R.layout.simple_list_item_2, mListItems);
		grid.setAdapter(mAdapter);
		grid.setTag(viewHolder.getPosition());
		grid.setOnItemClickListener(this.l);
		return true;
	}

	public void setL(OnItemClickListener l) {
		this.l = l;
	}

}
