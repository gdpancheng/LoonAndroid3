package com.android.demo.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.util.MenuEntity;
import com.android.demo.util.MyGridView;
import com.android.demo.util.MyHashMap;
import com.example.loonandroid2.R;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-9-15
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
public class MenuAdapter extends BaseAdapter {

	public ArrayList<MenuEntity> data;
	public Context activity;

	public MenuAdapter(Activity activity, ArrayList<MenuEntity> data) {
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(activity, R.layout.item_menu, null);
		TextView tv_label = (TextView) view.findViewById(R.id.tv_label);
		MyGridView gv_menu = (MyGridView) view.findViewById(R.id.gv_menu);
		MenuEntity menuEntity = data.get(position);
		tv_label.setTextColor(Color.BLACK);
		tv_label.setText(menuEntity.title);
		String[] mListItems = new String[menuEntity.menus.size()];
		int i = 0;
		for (MyHashMap<String, Class> data : menuEntity.menus) {
			mListItems[i++] = (String) data.keySet().iterator().next();
		}
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(activity, R.layout.simple_list_item_2, mListItems);
		gv_menu.setAdapter(mAdapter);
		gv_menu.setTag(menuEntity);
		gv_menu.setOnItemClickListener(clickListener);
		return view;
	}

	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			MenuEntity menuEntity = (MenuEntity) parent.getTag();
			ArrayList<MyHashMap<String, Class>> maps = menuEntity.menus;
			HashMap<String, Class> item = maps.get(position);
			String key = ((TextView) view).getText().toString();
			if (item.get(key) == null) {
				Toast.makeText(parent.getContext(), "该功能等待添加", Toast.LENGTH_SHORT).show();
				return;
			}
			activity.startActivity(new Intent(activity, item.get(key)));
			System.out.println("跳转");
		}
	};
}
