package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.adapter.CategoryAdapter;
import com.demo.adapter.GoodsDetailAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.view.BounceInterpolator;
import com.demo.view.EasingType.Type;
import com.demo.view.Panel;
import com.demo.view.Panel.OnPanelListener;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.listener.OnItemClick;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;

/**
 * 商品分类列表
 * 
 * @author gdpancheng@gmail.com 2013-7-15 下午6:20:34
 */
@InLayer(value = R.layout.goods_detail_list, parent = R.id.center)
public class GoodsDetailListActivity extends BaseActivity {

	@InAll
	Views v;
	// ----------------------------------------------------------------------------
	// 自动创建适配器
	@InBean(R.layout.goods_detail_list_item)
	private GoodsDetailAdapter goodsDetailAdapter;
	@InBean(R.layout.category_item)
	CategoryAdapter cAdapter;
	@InBean(R.layout.category_item)
	CategoryAdapter bAdapter;
	PullToRefreshBase<?> refreshView;
	private ArrayList<HashMap<String, Object>> data;
	private ArrayList<HashMap<String, Object>> brand;
	private ArrayList<HashMap<String, Object>> category;
	private String cid, bid;
	private int cadap, badap;
	private int limit = 10;
	private String sort, order;
	int page = 1;

	// ----------------------------------------------------------------------------
	// view类
	static class Views {
		@InBinder(listener = OnItemClick.class, method = "itemClick")
		Gallery brand_gallery, category_gallery;
		@InView(pull = true, down = true)
		@InBinder(listener = OnItemClick.class, method = "itemClick")
		ListView good_list;
		Panel top_panel;
		ImageView open;
		TextView all_brand, all_category;
		@InBinder(listener = OnClick.class, method = "click")
		TextView price_sort, time_sort, number_sort;
	}

	@Init
	private void init() {
		setBottom(R.id.list);
		// -----------------------------------------------------------------------------
		// 抽屉
		v.top_panel.setInterpolator(new BounceInterpolator(Type.OUT));
		v.top_panel.setOnPanelListener(onPanelListener);

		data = goodsDetailAdapter.getData();
		category = cAdapter.getData();
		brand = bAdapter.getData();

		v.good_list.setCacheColorHint(0);
		v.good_list.setSelector(new ColorDrawable(Color.TRANSPARENT));
		v.good_list.setAdapter(goodsDetailAdapter);
		v.category_gallery.setAdapter(cAdapter);
		v.brand_gallery.setAdapter(bAdapter);

		// ---------------------------------------获取数据-----------------------------------------------------
		Intent intent = getIntent();
		if (intent.hasExtra("cid")) {
			cid = intent.getStringExtra("cid");
		}
		if (intent.hasExtra("bid")) {
			bid = intent.getStringExtra("bid");
		}
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
		getData(page, limit, this.sort, this.order);
	}

	// ---------------------------------------点击事件-----------------------------------------------------
	public void click(View v) {
		Object tag = v.getTag();
		if (tag == null) {
			tag = "false";
		}
		switch (v.getId()) {
		case R.id.price_sort:
			getData(1, limit, "saleprice", Boolean.valueOf(tag.toString()) ? "asc" : "desc");
			break;
		case R.id.time_sort:
			getData(1, limit, "create_time", Boolean.valueOf(tag.toString()) ? "asc" : "desc");
			break;
		case R.id.number_sort:
			getData(1, limit, "id", Boolean.valueOf(tag.toString()) ? "asc" : "desc");
			break;
		}
		v.setTag(!Boolean.valueOf(tag.toString()));
	}

	// ---------------------------------------列表点击事件-----------------------------------------------------
	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.brand_gallery:
			v.all_brand.setBackgroundColor(Color.TRANSPARENT);
			for (HashMap<String, Object> entity : brand) {
				entity.remove("ishow");
			}
			bid = brand.get(arg2 % brand.size()).get("id").toString();
			brand.get(arg2 % brand.size()).put("ishow", "click");
			bAdapter.notifyDataSetChanged();
			showProgress();
			getData(1, limit, sort, order);
			break;
		case R.id.category_gallery:
			v.all_category.setBackgroundColor(Color.TRANSPARENT);
			for (HashMap<String, Object> entity : category) {
				entity.remove("ishow");
			}
			cid = category.get(arg2 % category.size()).get("id").toString();
			category.get(arg2 % category.size()).put("ishow", "click");
			cAdapter.notifyDataSetChanged();
			showProgress();
			getData(1, limit, sort, order);
			break;
		default:
			Intent intent = new Intent(GoodsDetailListActivity.this, GoodsDetailInfoActivity.class);
			intent.putExtra("id", data.get(arg2).get("id").toString());
			startActivity(intent);
			break;
		}
	}

	// ---------------------------------------获取数据-----------------------------------------------------
	private void getData(int page, int pageSize, String sort, String order) {
		if (page == 1) {
			this.page = page;
		}
		this.sort = sort;
		this.order = order;
		HashMap<String, String> params = new HashMap<String, String>();
		if (bid != null) {
			params.put("bid", bid);
		}
		if (cid != null) {
			params.put("cid", cid);
		}
		params.put("page", page + "");
		params.put("pagesize", pageSize + "");
		if (sort != null) {
			params.put("sort", sort);
		}
		if (order != null) {
			params.put("order", order);
		}
		App.http.u(this).goods(params);
	}

	// ---------------------------------------网络请求回调-----------------------------------------------------
	@InHttp
	public void result(Result result) {
		progressDimss();
		goodsDetailAdapter.notifyDataSetChanged();
		refreshView.onRefreshComplete();

		if (result.isOk()) {
			HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
			if (http_data.containsKey("status") && http_data.get("status").equals("1")) {
				http_data = (HashMap<String, Object>) http_data.get("data");
				brand.clear();
				Object b = http_data.get("brand");
				if (b instanceof ArrayList) {
					brand.addAll((ArrayList<HashMap<String, Object>>) b);
				}

				for (int i = 0; i < brand.size(); i++) {
					if (brand.get(i).get("id").toString().equals(bid)) {
						brand.get(i).put("ishow", "click");
						badap = i;
						break;
					}
				}
				bAdapter.notifyDataSetChanged();
				v.brand_gallery.setSelection(badap == 0 ? 1 : badap);
				category.clear();
				b = http_data.get("category");
				if (b instanceof ArrayList) {
					category.addAll((ArrayList<HashMap<String, Object>>) b);
				}
				for (int i = 0; i < category.size(); i++) {
					if (category.get(i).get("id").toString().equals(cid)) {
						category.get(i).put("ishow", "click");
						cadap = i;
						break;
					}
				}
				cAdapter.notifyDataSetChanged();
				v.category_gallery.setSelection(cadap == 0 ? 1 : cadap);
				if (http_data.containsKey("product")) {
					HashMap<String, Object> product = (HashMap<String, Object>) http_data.get("product");
					if (Integer.valueOf(result.params.get("page").toString()) == 1) {
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
			} else {
				Toast.makeText(this, http_data.get("error").toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	OnPanelListener onPanelListener = new OnPanelListener() {

		@Override
		public void onPanelOpened(Panel panel) {
			v.open.setImageResource(R.drawable.pannel_bt_up);
		}

		@Override
		public void onPanelClosed(Panel panel) {
			v.open.setImageResource(R.drawable.pannel_bt_down);
		}
	};
}
