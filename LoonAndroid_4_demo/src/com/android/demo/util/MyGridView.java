package com.android.demo.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 测试MyGridView TODO(这里用一句话描述这个类的作用)
 * 
 * @author gdpancheng@gmail.com 2014年12月9日 上午10:42:48
 */
public class MyGridView extends GridView {

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
