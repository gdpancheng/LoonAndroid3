package com.demo.adapter;

import java.util.HashMap;

import android.widget.BaseAdapter;
import android.widget.TextView;

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
public abstract class UserMessageAdapter extends BaseAdapter  implements LoonAdapter<HashMap<String, Object>> {
	
	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		TextView question =arg1.getView(R.id.question);
		TextView question_time =arg1.getView(R.id.question_time);
		TextView answer =arg1.getView(R.id.answer);
		TextView answer_time =arg1.getView(R.id.answer_time);
		if (arg0.containsKey("content")) {
			question.setText(arg0.get("content").toString());
		}
		if (arg0.containsKey("create_time")) {
			question_time.setText(arg0.get("create_time").toString());
		}
		if (arg0.containsKey("rep_content")) {
			answer.setText(arg0.get("rep_content").toString());
		}
		if (arg0.containsKey("rep_time")) {
			answer_time.setText(arg0.get("rep_time").toString());
		}
		return true;
	}
}