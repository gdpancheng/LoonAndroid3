package com.demo.adapter;

import java.util.HashMap;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.application.App;
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
public abstract class GoodsAdapter extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	private boolean isShow;

	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		ImageView image = arg1.getView(R.id.image);
		TextView title = arg1.getView(R.id.title);
		ImageView jiantou2 = arg1.getView(R.id.jiantou2);

		int id = arg1.getConvertView().getResources().getIdentifier("p_" + arg1.getPosition(), "drawable", App.app.getPackageName());
		image.setImageResource(id);

		if (isShow) {
			title.setVisibility(View.INVISIBLE);
			jiantou2.setVisibility(View.INVISIBLE);
		}
		title.setText(arg0.get("name").toString());
		ImageView jiantou = arg1.getView(R.id.jiantou);
		jiantou.setVisibility(View.GONE);
		if (arg0.containsKey("isShow") && arg0.get("isShow").toString().equals("1")) {
			jiantou.setVisibility(View.VISIBLE);
		}
		return true;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

}