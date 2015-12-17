package com.demo.xiamenhome;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.adapter.HomeBannerAdapter;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.util.Constant.HttpUrl;
import com.demo.view.CircleFlowIndicator;
import com.demo.view.ViewFlow;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InVa;
import com.loonandroid.pc.annotation.InVaER;
import com.loonandroid.pc.annotation.InVaOK;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.handler.Handler_Ui;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.validator.Validator;
import com.loonandroid.pc.validator.ValidatorCore;

/**
 * 商品详细信息 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 上午11:39:30
 */
@InLayer(value = R.layout.goods_detail_info, parent = R.id.center)
public class GoodsDetailInfoActivity extends BaseActivity implements TextWatcher {

	@InAll
	Views views;

	@InBean(R.layout.home_banner_item)
	HomeBannerAdapter adapter;

	private ArrayList<HashMap<String, Object>> banner;
	private String id;
	private String supplierid;

	// ----------------------------------------------------------------------------
	// view类
	
	static class Views {
		ViewFlow good_image;
		CircleFlowIndicator circle;
		@InBinder(listener = OnClick.class, method = "click")
		TextView more_message, suppliername, shoucang_title, gouwuche_title, submit;
		RelativeLayout frame;
		TextView image_title, price, saleprice, spec, serial, area, space, style, text_count, question, question_time, answer, answer_time;
		ImageView shuoming;
		@InVa(empty = false, msg = "请输入留言")
		EditText message;
	}

	// -------------------------------------------------------------初始化 类似oncreate---------------------------------------------
	@Init
	private void init() {
		setBottom(R.id.list);
		setTitle("商品详情");

		Handler_Ui.resetLLBack(views.frame);
		Handler_Ui.resetLLBack(views.shuoming);

		banner = adapter.getData();
		views.good_image.setAdapter(adapter);
		views.good_image.setFlowIndicator(views.circle);

		views.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		views.message.addTextChangedListener(this);
		// ------------------------------------------------------------------------------------------
		// 获取商品数据
		getInfo();
	}

	/**
	 * 获取商品详情
	 * 
	 * @author gdpancheng@gmail.com 2015年12月10日 下午12:09:24
	 * @return void
	 */
	private void getInfo() {
		id = getIntent().getStringExtra("id");
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		App.http.u(this).goods_info(params);
	}

	/**
	 * 收藏
	 * 
	 * @author gdpancheng@gmail.com 2015年12月10日 下午12:09:33
	 * @return void
	 */
	private void favorite() {
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		params.put("goods_id", id);
		App.http.u(this).add_favorite(params);
	}

	/**
	 * 留言
	 * 
	 * @author gdpancheng@gmail.com 2015年12月10日 下午12:09:41
	 * @return void
	 */
	private void message() {
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("desc_id", id);
		params.put("user_id", App.app.getData("user_id"));
		params.put("content", views.message.getText().toString());
		params.put("type", "2");
		App.http.u(this).leave_message(params);
	}

	/**
	 * 留言
	 * 
	 * @author gdpancheng@gmail.com 2015年12月10日 下午12:09:41
	 * @return void
	 */
	private void add_cart() {
		showProgress();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goods_id", id);
		params.put("id", App.app.getData("user_id"));
		params.put("num", "1");
		App.http.u(this).add_cart(params);
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
	public void result(Result data) {
		progressDimss();
		// 判断请求是否成功
		if (!data.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		banner.clear();
		HashMap<String, Object> result = Handler_Json.JsonToCollection(data.object);
		switch (data.key) {
		case HttpUrl.ADD_FAVORITE_KEY:
			if (result.containsKey("status") && result.get("status").toString().equals("1")) {
				showToast("收藏成功");
			}
			break;
		case HttpUrl.GOODS_INFO_KEY:
			if (!result.containsKey("data")) {
				return;
			}
			result = (HashMap<String, Object>) result.get("data");
			if (result.containsKey("gallery")) {
				Object object = result.get("gallery");
				if (object instanceof ArrayList) {
					banner.addAll((ArrayList<HashMap<String, Object>>) object);
					adapter.notifyDataSetChanged();
				}
			}
			supplierid = result.get("supplierid").toString();
			if (result.containsKey("name")) {
				views.image_title.setText(result.get("name").toString());
			}
			if (result.containsKey("price")) {
				views.price.setText(result.get("price").toString());
			}
			if (result.containsKey("saleprice")) {
				views.saleprice.setText(result.get("saleprice").toString());
			}
			if (result.containsKey("suppliername")) {
				views.suppliername.setText(result.get("suppliername").toString());
			}
			if (result.containsKey("spec")) {
				views.spec.setText(result.get("spec").toString());
			}
			if (result.containsKey("serial")) {
				views.serial.setText(result.get("serial").toString());
			}
			if (result.containsKey("area")) {
				views.area.setText(result.get("area").toString());
			}
			if (result.containsKey("space")) {
				views.space.setText(result.get("space").toString());
			}
			if (result.containsKey("style")) {
				views.style.setText(result.get("style").toString());
			}
			if (result.containsKey("message")) {
				Object object = result.get("message");
				if (object instanceof ArrayList) {
					ArrayList<HashMap<String, Object>> message = (ArrayList<HashMap<String, Object>>) object;
					if (message.size() > 0) {
						if (message.get(0).containsKey("content")) {
							views.question.setText(message.get(0).get("content").toString());
						}
						if (message.get(0).containsKey("rep_time")) {
							views.question_time.setText(message.get(0).get("rep_time").toString());
						}
						if (message.get(0).containsKey("rep_content")) {
							views.answer.setText(message.get(0).get("rep_content").toString());
						}
						if (message.get(0).containsKey("rep_time")) {
							views.answer_time.setText(message.get(0).get("rep_time").toString());
						}
					}
				}
			}
			break;
		case HttpUrl.LEAVE_MESSAGE_KEY:
			if (result.containsKey("status") && result.get("status").toString().equals("1")) {
				showToast("留言成功，等待审核");
				views.message.setText("");
			}
			break;
		case HttpUrl.ADD_CART_KEY:
			if (result.containsKey("status") && result.get("status").toString().equals("1")) {
				startActivity(new Intent(GoodsDetailInfoActivity.this, ShoppingCartActivity.class));
			}
			break;
		default:
			break;
		}
	}

	// ---------------------------------------点击事件-----------------------------------------------------
	/**
	 * 点击事件
	 * 
	 * @author gdpancheng@gmail.com 2013-7-29 下午5:12:52
	 * @param v
	 * @return void
	 */
	public void click(View v) {
		switch (v.getId()) {
		case R.id.suppliername:
			Intent intent1 = new Intent(this, IntroductionInfoActivity.class);
			intent1.putExtra("supplierid", supplierid);
			startActivity(intent1);
			break;
		case R.id.more_message:
			Intent intent = new Intent(this, MoreMessageActivity.class);
			intent.putExtra("id", id);
			startActivity(intent);
			break;
		case R.id.submit:
			if (App.app.getData("user_id").length() == 0) {
				startActivity(new Intent(this, LoginActivity.class));
				return;
			}
			Validator.verify(this);
			break;
		case R.id.gouwuche_title:
			if (App.app.getData("user_id").length() == 0) {
				startActivity(new Intent(this, LoginActivity.class));
				return;
			}
			add_cart();
			break;
		case R.id.shoucang_title:
			if (App.app.getData("user_id").length() == 0) {
				startActivity(new Intent(this, LoginActivity.class));
				return;
			}
			favorite();
			break;
		default:
			break;
		}
	}

	@InVaOK
	private void onValidationSucceeded() {
		message();
	}

	@InVaER
	public void onValidationFailed(ValidatorCore core) {
		EditText message = core.getView();
		message.requestFocus();
		showToast(core.getMsg());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		int counts = s.toString().length();
		if (counts > 300) {
			views.message.setText(s.toString().substring(0, 300));
			counts = 300;
		}
		views.text_count.setText("【" + counts + "/" + 300 + "】");
		if (s instanceof Spannable) {
			Spannable spanText = (Spannable) s;
			Selection.setSelection(spanText, s.length());
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
	}
}
