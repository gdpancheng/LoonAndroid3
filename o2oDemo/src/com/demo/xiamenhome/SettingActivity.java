package com.demo.xiamenhome;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.adapter.SettingAdapter;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnItemClick;

/**
 * 设置 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月11日 下午6:34:22
 */
@InLayer(value = R.layout.setting_list, parent = R.id.center)
public class SettingActivity extends BaseActivity {

	@InView(binder=@InBinder(listener = OnItemClick.class, method = "click"))
	ListView setting_list;

	@InBean(R.layout.setting_item)
	SettingAdapter adapter;

	@Init
	private void init() {
		setTitle("设置");
		setBottom(R.id.user);
		setting_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	};
	
	public void click(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			startActivity(new Intent(SettingActivity.this, UserActivity.class));
			break;
		case 1:
//			startActivity(new Intent(SettingActivity.this, ShoucangActivity.class));
			break;
		case 2:
//			startActivity(new Intent(SettingActivity.this, OrderActivity.class));
			break;
		case 3:
			startActivity(new Intent(SettingActivity.this, ShoppingCartActivity.class));
			break;
		case 4:
//			startActivity(new Intent(SettingActivity.this, AddressActivity.class));
			break;
		default:
			break;
		}
	}
}
