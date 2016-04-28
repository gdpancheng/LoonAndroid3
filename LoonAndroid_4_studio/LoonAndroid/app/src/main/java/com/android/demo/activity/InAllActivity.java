package com.android.demo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.adapter.ListViewAdpter;
import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.CommonAdapter;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.Ignore;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.listener.OnCompoundChecked;
import com.loonandroid.pc.listener.OnItemClick;
import com.loonandroid.pc.listener.OnItemSelected;
import com.loonandroid.pc.listener.OnLongClick;
import com.loonandroid.pc.listener.OnRadioChecked;
import com.loonandroid.pc.listener.OnTouch;

/**
 * <h1>演示了所有InAll的用法</h1> <br>
 * 1 InAll可以绑定view <br>
 * 2 InAll可以绑定view的各种事件 <br>
 * 3 InAll可以绑定view的事件有单击 长按 触摸 列表点击 选中等 <br>
 * 4 InAll事件可以覆盖 <br>
 * 5 InAll资源可以解析 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月11日 下午12:31:13
 */
@InLayer(R.layout.activity_inview)
public class InAllActivity extends Activity {

	// -----------------------------------------------------------------------------------------------
	// 这里是第一种，统一添加点击事件 如果没加@Ignore标签，除了AdapterView和图片字符串以外
	// 全部会加上点击事件
	@InAll(@InBinder(method = "click", listener = OnClick.class))
	Views1 views1;

	 static class Views1 {
		TextView tv_first;// 变量名等于ID
		Button bt_long;
		// 加了这个表示忽略InAll的点击事件
		@Ignore
		Button bt_onclick;
		CheckBox cb_select, cb_select1, cb_select2, cb_select3;
		RadioGroup rg_radio;
		Spinner sp_spinner;
		ListView lv_list;
		TextView tv_touch;
		Drawable ic_launcher;
		String hello_world;
	}

	// -----------------------------------------------------------------------------------------------
	// 这里是第二种，单独定义
	@InAll
	Views2 views2;

	class Views2 {
		TextView tv_first;// 变量名等于ID
		@InBinder(method = "longClick", listener = OnLongClick.class)
		Button bt_long;
		@InBinder(method = "click", listener = OnClick.class)
		Button bt_onclick;
		@InBinder(method = "check", listener = OnCompoundChecked.class)
		CheckBox cb_select, cb_select1, cb_select2, cb_select3;
		@InBinder(method = "onCheckedChanged", listener = OnRadioChecked.class)
		RadioGroup rg_radio;
		@InBinder(method = "itemSelected", listener = OnItemSelected.class)
		Spinner sp_spinner;
		@InBinder(method = "clicks", listener = OnItemClick.class)
		ListView lv_list;
		@InBinder(method = "onTouch", listener = OnTouch.class)
		TextView tv_touch;
		Drawable ic_launcher;
		String hello_world;
	}

	// -----------------------------------------------------------------------------------------------
	// 这里是第三种，@InBinder覆盖@InAll中的@InBinder
	@InAll(@InBinder(method = "click", listener = OnClick.class))
	Views3 views3;

	class Views3 {
		TextView tv_first;// 变量名等于ID
		@InBinder(method = "longClick", listener = OnLongClick.class)
		Button bt_long;
		@InBinder(method = "click", listener = OnClick.class)
		Button bt_onclick;
		@InBinder(method = "check", listener = OnCompoundChecked.class)
		CheckBox cb_select, cb_select1, cb_select2, cb_select3;
		@InBinder(method = "onCheckedChanged", listener = OnRadioChecked.class)
		RadioGroup rg_radio;
		@InBinder(method = "itemSelected", listener = OnItemSelected.class)
		Spinner sp_spinner;
		@InBinder(method = "clicks", listener = OnItemClick.class)
		ListView lv_list;
		@InBinder(method = "onTouch", listener = OnTouch.class)
		TextView tv_touch;
		Drawable ic_launcher;
		String hello_world;
	}

	@Init
	private void init() {

		System.out.println(views1.ic_launcher);
		System.out.println(views1.hello_world);

		views1.tv_first.setText("第一 自动绑定单击事件");
		ArrayList<String> key = new ArrayList<String>();
		key.add("第一个");
		key.add("第二个");
		key.add("第三个");
		key.add("第四个");
		views1.lv_list.setAdapter(new CommonAdapter<String>(this, key, android.R.layout.simple_list_item_1) {
			@Override
			public void convert(ViewHolder helper, String item) {
				helper.setData(android.R.id.text1, item);
			}
		});
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		views1.sp_spinner.setAdapter(new ListViewAdpter(this, key));
	}

	private void click(View v) {
		MakeToast("单击");
	}

	private void longClick(View v) {
		MakeToast("长按");
	}

	private void check(CompoundButton buttonView, boolean isChecked) {
		MakeToast("多选");
	}

	public void clicks(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MakeToast("列表单击");
	}

	private boolean onTouch(View v, MotionEvent event) {
		MakeToast("触摸");
		return true;
	}

	private void onCheckedChanged(RadioGroup group, int checkedId) {
		MakeToast("单选");
	}

	public void itemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MakeToast("列表选择");
	}

	public void noneItemSelected(AdapterView<?> arg0) {
		MakeToast("列表么有选择");
	}

	public void itemSelected(AdapterView<?> arg0) {
		MakeToast("列表选择");
	}

	private void MakeToast(String title) {
		Toast.makeText(this, title, Toast.LENGTH_LONG).show();
	}
}
