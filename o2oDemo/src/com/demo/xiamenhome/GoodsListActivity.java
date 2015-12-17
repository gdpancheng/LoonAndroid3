package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.adapter.GoodsAdapter;
import com.demo.adapter.GoodsAdapter2;
import com.demo.adapter.GoodsAdapter3;
import com.demo.util.Constant;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnItemClick;

/**
 * 商品分类
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年12月10日 下午5:12:52
 */
@InLayer(value = R.layout.goods_list, parent = R.id.center)
public class GoodsListActivity extends BaseActivity {

	@InAll
	Views views;
	
	/**
	 * 第一级数据
	 */
	ArrayList<HashMap<String, Object>> list_second;
	ArrayList<HashMap<String, Object>> list_threed;
	/**
	 * 数据集合
	 */
	ArrayList<HashMap<String, Object>> data;
	
	@InBean(R.layout.goods_list_1_item)
	GoodsAdapter goodsAdapter1;
	@InBean(R.layout.goods_list_2_item)
	GoodsAdapter2 goodsAdapter2;
	@InBean(R.layout.goods_list_3_item)
	GoodsAdapter3 goodsAdapter3;
	
	String cid, bid;
	
	static class Views{
		@InBinder(listener = OnItemClick.class, method = "itemClick")
		ListView good_list_1, good_list_2, good_list_3;
	}
	
	@Init
	private void init(){
		setBottom(R.id.list);
		setTitle("商品分类");
		
		data = goodsAdapter1.getData();
		data.addAll(Constant.data);
		
		list_second= goodsAdapter2.getData();
		list_threed = goodsAdapter3.getData();
		
		views.good_list_1.setAdapter(goodsAdapter1);
	}

	
	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.good_list_1:
			list_second.clear();
			list_second.addAll(Constant.data2.get(data.get(arg2).get("name").toString()));
			if (views.good_list_2.getVisibility() == View.GONE) {
				views.good_list_2.setVisibility(View.VISIBLE);
				views.good_list_2.setAdapter(goodsAdapter2);
			}
			changeData();
			changeData2();
			data.get(arg2).put("isShow", "1");
			System.out.println(goodsAdapter3.getData());
			goodsAdapter1.setShow(true);
			goodsAdapter1.notifyDataSetChanged();
			goodsAdapter2.notifyDataSetChanged();
			views.good_list_3.setVisibility(View.INVISIBLE);
			break;
		case R.id.good_list_2:
			cid = null;
			cid = list_second.get(arg2).get("id").toString();
			changeData2();
			list_threed.clear();
			list_threed.addAll(Constant.data3.get(list_second.get(arg2).get("name").toString()));
			list_second.get(arg2).put("isShow", "1");
			if (views.good_list_3.getVisibility() == View.INVISIBLE) {
				views.good_list_3.setVisibility(View.VISIBLE);
				views.good_list_3.setAdapter(goodsAdapter3);
			}
			goodsAdapter3.notifyDataSetChanged();
			goodsAdapter2.notifyDataSetChanged();
			break;
		case R.id.good_list_3:
			bid = null;
			String id = list_threed.get(arg2).get("id").toString();
			String type = list_threed.get(arg2).get("type").toString();
			if (type.equals("1")) {
				bid = id;
			} else {
				cid = id;
			}
			Intent intent = new Intent(GoodsListActivity.this, GoodsDetailListActivity.class);
			if (bid != null) {
				intent.putExtra("bid", bid);
			}
			if (cid != null) {
				intent.putExtra("cid", cid);
			}
			startActivity(intent);
			break;
		}
	}
	
	public void changeData() {
		for (HashMap<String, Object> entity : data) {
			entity.put("isShow", "0");
		}
	}

	public void changeData2() {
		for (HashMap<String, Object> entity : list_second) {
			entity.put("isShow", "0");
		}
	}
}

