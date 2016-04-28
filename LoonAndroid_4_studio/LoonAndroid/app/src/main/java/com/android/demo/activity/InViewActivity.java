package com.android.demo.activity;

import java.util.ArrayList;

import android.app.Activity;
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
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.listener.OnCompoundChecked;
import com.loonandroid.pc.listener.OnItemClick;
import com.loonandroid.pc.listener.OnItemSelected;
import com.loonandroid.pc.listener.OnLongClick;
import com.loonandroid.pc.listener.OnRadioChecked;
import com.loonandroid.pc.listener.OnTouch;

/**
 * <h1>演示了所有inView的用法</h1> <br>
 * 1 InView可以绑定view <br>
 * 2 InView可以绑定view的各种事件 <br>
 * 3 InView可以绑定view的事件有单击 长按 触摸 列表点击 选中等 <br>
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
public class InViewActivity extends Activity {

	@InView
	TextView tv_first;// 变量名等于ID

	@InView(R.id.tv_first)
	TextView first;// 变量名不等于ID

	@InView(binder = @InBinder(method = "click", listener = OnClick.class))
	Button bt_onclick;// 绑定事件 其中listener必须继承OnListener 类别有单击 长按 触摸 列表点击 选中等

	@InView(binder = @InBinder(method = "longClick", listener = OnLongClick.class))
	Button bt_long;

	@InView(binder = @InBinder(method = "check", listener = OnCompoundChecked.class))
	CheckBox cb_select, cb_select1, cb_select2, cb_select3;

	@InView(binder = @InBinder(method = "onCheckedChanged", listener = OnRadioChecked.class))
	RadioGroup rg_radio;

	@InView(binder = @InBinder(method = "itemSelected", listener = OnItemSelected.class))
	Spinner sp_spinner;

	@InView(binder = @InBinder(method = "clicks", listener = OnItemClick.class))
	ListView lv_list;

	@InView(binder = @InBinder(method = "onTouch", listener = OnTouch.class))
	TextView tv_touch;

	@Init
	private void init() {
		tv_first.setText("第一 自动绑定单击事件");
		ArrayList<String> key = new ArrayList<String>();
		key.add("第一个");
		key.add("第二个");
		key.add("第三个");
		key.add("第四个");
		lv_list.setAdapter(new ListViewAdpter(this, key));
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		key.add("第四个");
		sp_spinner.setAdapter(new ListViewAdpter(this, key));
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
