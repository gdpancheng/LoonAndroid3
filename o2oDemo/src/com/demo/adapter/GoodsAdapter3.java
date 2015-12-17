package com.demo.adapter;

import java.util.HashMap;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.xiamenhome.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-5-8
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class GoodsAdapter3 extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		TextView title = arg1.getView(R.id.title);
		title.setText(arg0.get("name").toString());
		return true;
	}
}