package com.demo.adapter;

import java.util.HashMap;

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
public abstract class SettingAdapter extends BaseAdapter implements LoonAdapter<Object> {

	String[] titles;
	String[] id;

	@Override
	public boolean dealView(Object arg0, ViewHolder arg1) {
		if (titles == null) {
			titles = arg1.getConvertView().getResources().getStringArray(R.array.setting_title);
			id = arg1.getConvertView().getResources().getStringArray(R.array.setting_image);
		}
		ImageView image = arg1.getView(R.id.image);
		TextView title = arg1.getView(R.id.title);
		image.setBackgroundResource(arg1.getConvertView().getResources().getIdentifier(id[arg1.getPosition()], "drawable", App.app.getPackageName()));
		title.setText(titles[arg1.getPosition()]);
		return true;
	}

	public int getCount() {
		return 5;
	}
}