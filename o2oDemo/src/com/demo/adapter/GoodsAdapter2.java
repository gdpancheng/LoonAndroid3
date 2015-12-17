package com.demo.adapter;

import java.util.HashMap;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
public abstract class GoodsAdapter2 extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		TextView title = arg1.getView(R.id.title);
		ImageView jiantou = arg1.getView(R.id.jiantou);
		jiantou.setVisibility(View.GONE);
		title.setText(arg0.get("name").toString());
		if (arg0.containsKey("isShow") && arg0.get("isShow").toString().equals("1")) {
			jiantou.setVisibility(View.VISIBLE);
		}
		return true;
	}
}