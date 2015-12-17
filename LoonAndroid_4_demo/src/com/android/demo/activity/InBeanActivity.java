package com.android.demo.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.TextView;

import com.android.demo.entity.Parent;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.handler.Handler_Json;

/**
 * json转hashmap <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月9日 下午5:29:09
 */
@InLayer(R.layout.activity_inlayer)
public class InBeanActivity extends Activity {

	@InView
	TextView text;

	void init() {
		// 模拟json字符串

		JSONObject parent_json = new JSONObject();
		try {
			parent_json.put("name", "潘城");
			// 这个属性是父类里面的属性
			parent_json.put("common", "通用");
			parent_json.put("number", 1);
			parent_json.put("isTure", true);
			JSONArray stringArray = new JSONArray();
			stringArray.put("字符串一");
			stringArray.put("字符串二");
			parent_json.put("list_string", stringArray);
			JSONArray childrenArray = new JSONArray();
			JSONObject childMJson = new JSONObject();
			childMJson.put("name", "儿子");
			childMJson.put("age", 10);
			childMJson.put("isTure", false);
			JSONObject childWJson = new JSONObject();
			childWJson.put("name", "女儿");
			childWJson.put("age", 10);
			childWJson.put("isTure", true);
			childrenArray.put(childMJson);
			childrenArray.put(childWJson);
			parent_json.put("childrens", childrenArray);
			JSONObject ortherson = new JSONObject();
			ortherson.put("name", "干儿子");
			ortherson.put("age", 10);
			ortherson.put("isTure", false);
			parent_json.put("one", ortherson);
		} catch (Exception e) {
		}
		Parent event = Handler_Json.JsonToBean(Parent.class, parent_json.toString());
		text.setText(event.toString());
	}
}
