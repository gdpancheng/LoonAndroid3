package com.loonandroid.pc.ioc.entity;

import android.app.Activity;

import com.loonandroid.pc.inject.InjectExcutor;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 设置父布局
 * @author gdpancheng@gmail.com 2014-11-13 下午6:29:36
 */
public class InPLayerEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 1L;
	private int id;
	private boolean isFull;
	private boolean isTile;
	private boolean isActivity;

	@Override
	public void invoke(Object object, Object... args) {
		if (id != LoonConstant.Number.ID_NONE&&isActivity) {
			InjectExcutor<Activity> excutor = LoonConstant.INJECT.get(LoonConstant.Key.ACTIVITY_KEY);
			excutor.setContentView((Activity)object, id);
		}
		this.object = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public boolean isTile() {
		return isTile;
	}

	public void setTile(boolean isTile) {
		this.isTile = isTile;
	}

	public boolean isActivity() {
		return isActivity;
	}

	public void setActivity(boolean isActivity) {
		this.isActivity = isActivity;
	}

}
