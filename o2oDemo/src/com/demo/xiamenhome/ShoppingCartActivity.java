package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.adapter.GoodsCartAdapter;
import com.demo.adapter.GoodsCartAdapter.Update;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.util.Constant.HttpUrl;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InResume;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnClick;

/**
 * 购物车 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 下午4:12:32
 */
@InLayer(value = R.layout.goods_cart, parent = R.id.center)
public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {

	@InAll
	Views views;

	static class Views {
		@InBinder(listener = OnClick.class, method = "onClick")
		private TextView num, update;
		@InBinder(listener = OnClick.class, method = "onClick")
		private ImageButton order_submit;
		ListView cart_list;
	}

	private ArrayList<HashMap<String, Object>> data;
	@InBean(R.layout.goods_cart_item)
	GoodsCartAdapter goodsCartAdapter;
	private int postion;

	@Init
	private void init() {
		setBottom(R.id.user);
		setTitle("购物车");

		data = goodsCartAdapter.getData();
		goodsCartAdapter.setL(this);
		goodsCartAdapter.setUpdate(new Update() {
			@Override
			public void onUpdate() {
				double count = 0;
				for (HashMap<String, Object> entity : data) {
					count = count + Double.valueOf(entity.get("count_saleprice").toString());
				}
				views.num.setText(count + "");
			}
		});
		views.cart_list.setAdapter(goodsCartAdapter);
	}

	@InResume
	protected void resume() {
		info();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete_bt:
			postion = Integer.valueOf(v.getTag().toString());
			delete(data.get(postion).get("goods_id").toString());
			break;
		case R.id.update:
			update();
			break;
		case R.id.order_submit:
			// Intent intent = new Intent();
			// intent.setClass(this, OrderSubmitActivity.class);
			// Bundle bundle = new Bundle();
			// intent.putExtra("num", num.getText().toString());
			// bundle.putParcelableArrayList("order", (ArrayList) data);
			// intent.putExtras(bundle);
			// startActivity(intent);
			break;
		default:
			break;
		}
	}
	// ---------------------------------------获取数据-----------------------------------------------------

	private void info() {
		// -------------------------------------------------------------
		// 获取购物车信息
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		App.http.u(this).cart_info(params);
	}

	private void update() {
		showProgress();
		String goods_id = "";
		String num = "";
		for (HashMap<String, Object> entity : data) {
			goods_id = goods_id + entity.get("goods_id").toString() + "-";
			num = num + entity.get("count").toString() + "-";
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		params.put("goods_id", goods_id.substring(0, goods_id.length() - 1));
		params.put("num", num.substring(0, num.length() - 1));
		App.http.u(this).update_cart(params);
	}

	private void delete(String good) {
		showProgress();
		String goods_id = "";
		String num = "";
		for (HashMap<String, Object> entity : data) {
			goods_id = goods_id + entity.get("goods_id").toString() + "-";
			num = num + entity.get("count").toString() + "-";
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		params.put("goods_id", good);
		App.http.u(this).delete_goods(params);
	}

	// ---------------------------------------网络请求回调-----------------------------------------------------
	/**
	 * 网络请求回调
	 * 
	 * @author gdpancheng@gmail.com 2015年12月9日 下午4:43:46
	 * @param result
	 * @return void
	 */
	@InHttp
	public void result(Result result) {
		System.out.println(result);
		progressDimss();
		// 判断请求是否成功
		if (!result.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
		switch (result.key) {
		case HttpUrl.CART_INFO_KEY:
			if (http_data.containsKey("status") && http_data.get("status").toString().equals("1")) {
				data.clear();
				Object object = http_data.get("data");
				if (object instanceof HashMap) {
					http_data = (HashMap<String, Object>) object;
					object = http_data.get("goods");
					views.num.setText(http_data.get("totle_saleprice").toString());
					if (object instanceof ArrayList) {
						data.addAll((ArrayList<HashMap<String, Object>>) object);
					}
				}
			}
			break;
		case HttpUrl.UPDATE_CART_KEY:
			if (http_data.containsKey("status") && http_data.get("status").toString().equals("1")) {
				Object object = http_data.get("data");
				if (object instanceof HashMap) {
					http_data = (HashMap<String, Object>) object;
					object = http_data.get("goods");
					if (object instanceof ArrayList) {
						data.clear();
						data.addAll((ArrayList<HashMap<String, Object>>) object);
					}
				}
				showToast("购物车修改成功");
			}
			break;
		case HttpUrl.DELETE_GOODS_KEY:
			if (http_data.containsKey("status") && http_data.get("status").toString().equals("1")) {
				data.remove(postion);
			} else {
				showToast("删除失败");
			}
			break;
		default:
			break;
		}
		goodsCartAdapter.notifyDataSetChanged();
	}
}
