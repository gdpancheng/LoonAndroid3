package com.android.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.demo.adapter.MenuAdapter;
import com.android.demo.util.Constant;
import com.example.loonandroid2.R;

/**
 * demo 入口 这个页面为了对比 是原生写法<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月11日 上午12:49:44
 */
public class MenuActivity extends Activity {

	private ListView lv_menu;
	public MenuAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		lv_menu = (ListView) findViewById(R.id.lv_menu);
		// 具体每个demo以及跳转的类请参考当前包名下的Constant
		adapter = new MenuAdapter(this, Constant.entities);
		lv_menu.setAdapter(adapter);

		// ContentProvider.getContext();
		// App.http.newHttp(this).login("55607999", "123456");
		// LinkedHashMap<String, File> mapHashMap = new LinkedHashMap<String, File>();
		// mapHashMap.put("sss", new File("aaa"));
		// App.http.newHttp(this).login(mapHashMap);
		// HashMap map = new HashMap();
		// map.put("ooooo", "测试");
		// requests.login(map);
		// App.http.newHttp(this).login("aaa", "bbb");
		// requests.asyncLoginPost("afdsaf", "sssss");
		// requests.LoginWeb("测试");
		// requests.asyncLoginWeb("cccc", "dddd");
		// requests.LoginForm("eee", "rrrr");
		// requests.asyncLoginForm("pppp", "ooooo");

	}
}
