package com.demo.adapter;

import java.util.HashMap;

import android.graphics.Color;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.xiamenhome.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/**
 * 
 * 当前为自动注入的适配器<br>
 * <h1>以下为需要注意的</h1> 写法分为两种<br>
 * 1 CustomAdapter 实现LoonAdapter接口，并声明LoonAdapter接口的泛型 该泛型为你绑定到该Item的来源数据的类型<br>
 * 2 如果你不实现LoonAdapter接口，当这个适配器注入的时候 也会自动实现LoonAdapter接口， 那么你在使用的时候，就必须强转<br>
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
public abstract class CategoryAdapter extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		TextView title =arg1.getView(R.id.title);
		title.setTextColor(Color.WHITE);
		if (arg0.containsKey("ishow")&&arg0.get("ishow").toString().equals("click")) {
			title.setTextColor(Color.YELLOW);
		}
		title.setText(arg0.get("name").toString());
		return true;
	}

}
