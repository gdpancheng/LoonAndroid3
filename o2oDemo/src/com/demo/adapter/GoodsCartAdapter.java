package com.demo.adapter;

import java.util.HashMap;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bluemobi.config.Configuration;
import com.bluemobi.imageload.AsynImageView;
import com.bluemobi.imageload.ImageDownloader;
import com.demo.xiamenhome.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-5-8
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class GoodsCartAdapter extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	View.OnClickListener l;

	public void setL(View.OnClickListener l) {
		this.l = l;
	}

	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		TextView title = arg1.getView(R.id.title);
		final EditText count = arg1.getView(R.id.count);
		TextView saleprice = arg1.getView(R.id.saleprice);
		TextView saleprice_count = arg1.getView(R.id.saleprice_count);
		ImageButton delete_bt = arg1.getView(R.id.delete_bt);
		AsynImageView image = arg1.getView(R.id.photo);

		Configuration configuration = new Configuration();
		configuration.setListView(true);
		configuration.setPostion(arg1.getPosition());
		configuration.setMax_width(207);
		configuration.setMax_height(150);
		ImageDownloader.getInstance().download(arg0.get("logo").toString(), image, configuration);

		delete_bt.setTag(arg1.getPosition());
		delete_bt.setOnClickListener(this.l);

		String saleprices = arg0.get("saleprice").toString();

		ImageButton add = arg1.getView(R.id.add);
		add.setTag(R.id.tag_one, count);
		add.setTag(R.id.tag_two, saleprice_count);
		add.setTag(R.id.tag_three, saleprices);
		add.setTag(R.id.tag_four, arg1.getPosition());
		add.setOnClickListener(click);
		ImageButton delete = arg1.getView(R.id.delete);
		delete.setTag(R.id.tag_one, count);
		delete.setTag(R.id.tag_two, saleprice_count);
		delete.setTag(R.id.tag_three, saleprices);

		delete.setTag(R.id.tag_four, arg1.getPosition());
		delete.setOnClickListener(click);

		saleprice.setText(saleprices);
		count.setText(arg0.get("count").toString());
		title.setText(arg0.get("new_maintitle").toString());
		saleprice_count.setText(arg0.get("count_saleprice").toString());

		count.setTag(R.id.tag_one, count);
		count.setTag(R.id.tag_two, saleprice_count);
		count.setTag(R.id.tag_three, saleprices);
		count.setTag(R.id.tag_four, arg1.getPosition());
		count.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				TextView saleprice_count = (TextView) count.getTag(R.id.tag_two);
				String saleprices = count.getTag(R.id.tag_three).toString();
				int index = Integer.valueOf(count.getTag(R.id.tag_four).toString());
				int count = 0;
				if (s.toString().trim().length() == 0) {
					count = 0;
				} else {
					count = Integer.valueOf(s.toString());
				}
				double count_price = Double.valueOf(saleprices) * count;
				getData().get(index).put("count_saleprice", count_price + "");
				getData().get(index).put("count", count + "");
				if (update != null) {
					update.onUpdate();
				}
				saleprice_count.setText(count_price + "");
			}
		});

		return true;
	}

	View.OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			EditText editText = (EditText) v.getTag(R.id.tag_one);
			TextView saleprice_count = (TextView) v.getTag(R.id.tag_two);
			String saleprices = v.getTag(R.id.tag_three).toString();
			int index = Integer.valueOf(v.getTag(R.id.tag_four).toString());
			int count = Integer.valueOf(editText.getText().toString());
			switch (v.getId()) {
			case R.id.add:
				count = (count + 1);
				editText.setText(count + "");
				break;
			default:
				if (count > 1) {
					count = (count - 1);
					editText.setText(count + "");
				}
				break;
			}
			double count_price = Double.valueOf(saleprices) * count;
			getData().get(index).put("count_saleprice", count_price + "");
			getData().get(index).put("count", count + "");
			if (update != null) {
				update.onUpdate();
			}
			saleprice_count.setText(count_price + "");
		}
	};

	public interface Update {
		public void onUpdate();
	}

	Update update;

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}
}