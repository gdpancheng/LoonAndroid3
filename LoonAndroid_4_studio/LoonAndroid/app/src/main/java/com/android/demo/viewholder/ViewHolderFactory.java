package com.android.demo.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月8日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

public class ViewHolderFactory {

	@InLayer(R.layout.simple_list_item_1)
	public static class TestHolder {
		public View root;
		public TextView tv_test;
		@InView
		public Button bt_test;
	}
	
	@InLayer(R.layout.grid_item)
	public static class StringHolder {
		public TextView text1;
	}
}
