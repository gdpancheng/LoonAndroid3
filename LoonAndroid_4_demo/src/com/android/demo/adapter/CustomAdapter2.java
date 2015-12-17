package com.android.demo.adapter;

import android.view.View;
import android.widget.BaseAdapter;

import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;

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
public abstract class CustomAdapter2 extends BaseAdapter {

	View.OnClickListener l;

	public boolean dealView(Good good, ViewHolder viewHolder) {
		viewHolder.setData(R.id.bt_test, "按钮" + viewHolder.getPosition()).setOnClickListener(l);
		return false;
	}

	public void setL(View.OnClickListener l) {
		this.l = l;
	}

}
