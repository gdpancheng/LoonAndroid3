package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.adapter.GoodsDetailAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnItemClick;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

@InLayer(value = R.layout.goods_search_list, parent = R.id.center)
public class GoodsSearchListActivity extends BaseActivity {

	@InView(pull=true,down=true,binder = @InBinder(listener = OnItemClick.class, method = "itemClick"))
	ListView good_list;
	@InBean(R.layout.goods_detail_list_item)
	private GoodsDetailAdapter goodsDetailAdapter;
	private PullToRefreshBase<?> refreshView;

	private ArrayList<HashMap<String, Object>> data;
	String keyword;
	boolean is_Load = false;
	int page = 1;
	private int limit = 10;

	@Init
	private void init() {
		setTitle("搜索结果");
		setBottom(R.id.list);
		data = goodsDetailAdapter.getData();
		good_list.setAdapter(goodsDetailAdapter);
		keyword = getIntent().getStringExtra("keyword");
	}

	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(GoodsSearchListActivity.this, GoodsDetailInfoActivity.class);
		intent.putExtra("id", data.get(arg2).get("id").toString());
		startActivity(intent);
	}

	private void getData(int page, int pageSize) {
		if (page == 1) {
			this.page = page;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", keyword);
		params.put("page", page + "");
		params.put("pagesize", pageSize + "");
		App.http.u(this).search(params);
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

	// ---------------------------------------网络请求回调-----------------------------------------------------
	@InHttp
	public void result(Result result) {
		goodsDetailAdapter.notifyDataSetChanged();
		refreshView.onRefreshComplete();

		if (result.isOk()) {
			HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
			if (http_data.containsKey("status") && http_data.get("status").equals("1")) {
				http_data = (HashMap<String, Object>) http_data.get("data");
				if (http_data.containsKey("product")) {
					HashMap<String, Object> product = (HashMap<String, Object>) http_data.get("product");
					if (page == 1) {
						data.clear();
					}
					if (product.containsKey("data")) {
						Object object = product.get("data");
						if (object instanceof ArrayList) {
							data.addAll((ArrayList<HashMap<String, Object>>) product.get("data"));
						}
					}
				}
				goodsDetailAdapter.notifyDataSetChanged();
			}
		}
	}
}
