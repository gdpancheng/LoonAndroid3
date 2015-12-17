package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.ListView;

import com.demo.adapter.UserMessageAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;
/**
 * 留言列表
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年12月10日 下午4:12:06
 */
@InLayer(value = R.layout.more_message_list, parent = R.id.center)
public class MoreMessageActivity extends BaseActivity {

	@InView(pull = true, down = true)
	ListView message_list;
	@InBean(R.layout.user_message_item)
	UserMessageAdapter adapter;
	String id;
	int page = 1;
	private int limit = 10;
	PullToRefreshBase<?> refreshView;
	private ArrayList<HashMap<String, Object>> data;

	@Init
	public void init() {
		setBottom(R.id.list);
		setTitle("留言列表");
		
		message_list.setAdapter(adapter);
		id = getIntent().getStringExtra("id");
	}

	// ---------------------------------------下拉刷新-----------------------------------------------------
	@InPullRefresh
	public void refresh(PullToRefreshBase<?> refreshView, Pull pull) throws InterruptedException {
		this.refreshView = refreshView;
		switch (pull) {
		case DOWN:
			page = 1;
			break;
		default:
			page++;
			break;
		}
		getData(page, limit);
	}

	private void getData(int page, int limit) {
		if (page == 1) {
			this.page = page;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("desc_id", id);
		params.put("type", 2 + "");
		params.put("page ", page + "");
		App.http.u(this).evaluate(params);
	}

	// ---------------------------------------网络请求回调-----------------------------------------------------
	@InHttp
	public void result(Result result) {
		progressDimss();
		refreshView.onRefreshComplete();

		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
		if (http_data.containsKey("data")) {
			Object object = http_data.get("data");
			if (object instanceof HashMap) {
				HashMap<String, Object> datas = (HashMap<String, Object>) object;
				if (datas.containsKey("data")) {
					ArrayList<HashMap<String, Object>> datalist = (ArrayList<HashMap<String, Object>>) datas.get("data");
					if (page == 1) {
						data.clear();
					}
					data.addAll(datalist);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}
}
