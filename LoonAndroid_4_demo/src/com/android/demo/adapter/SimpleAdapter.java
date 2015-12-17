package com.android.demo.adapter;

import android.view.View;
import android.widget.BaseAdapter;

import com.android.demo.entity.Good;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/**
 * 
 * <br>
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
public abstract class SimpleAdapter extends BaseAdapter implements LoonAdapter<Good> {

	View.OnClickListener l;

	@Override
	public boolean dealView(Good good, ViewHolder viewHolder) {
		viewHolder.setData(R.id.bt_test, "按钮" + viewHolder.getPosition()).setOnClickListener(l);
		return false;
	}

	public void setL(View.OnClickListener l) {
		this.l = l;
	}

}
