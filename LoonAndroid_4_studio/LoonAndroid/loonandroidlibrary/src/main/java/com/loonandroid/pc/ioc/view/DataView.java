package com.loonandroid.pc.ioc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月7日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class DataView extends LinearLayout {

	public <T> T getData() {
		return (T) data;
	}

	public <T> void setData(T data) {
		this.data = data;
	}

	private Object data;

	public DataView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DataView(Context context) {
		super(context);
	}

}
