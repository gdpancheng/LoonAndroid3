package com.loonandroid.pc.ioc.entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loonandroid.pc.entity.ActivityEntity;
import com.loonandroid.pc.inject.InjectExcutor;
import com.loonandroid.pc.ioc.Ioc;
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
 * 初始化布局
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:26:22
 */
public class InLayerEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5164082430241717128L;
	private int id;
	private boolean isFull;
	private boolean isTile;
	private boolean isActivity;
	private int parent;
	private Class pClazz;

	@Override
	public <T> void invoke(T object, Object... args) {

		if (id == LoonConstant.Number.ID_NONE) {
			return;
		}
		InjectExcutor<T> excutor;
		if (isActivity) {
			excutor = LoonConstant.INJECT.get(LoonConstant.Key.ACTIVITY_KEY);
			if (parent == LoonConstant.Number.ID_NONE) {
				excutor.setContentView(object, id);
				return;
			}
		} else {
			excutor = LoonConstant.INJECT.get(LoonConstant.Key.VIEW_KEY);
		}

		if (parent != LoonConstant.Number.ID_NONE && pClazz != null) {
			ActivityEntity activityEntity = Ioc.getIoc().getAnalysisEntity(pClazz.getName());
			if (activityEntity == null || activityEntity.getPLayer() == null) {
				// --------------------------------------------------------------------------------------
				// 给出提示
				return;
			}
			View v = excutor.findViewById(object, parent);
			if (v == null) {
				activityEntity.getPLayer().invoke(object, args);
				v = excutor.findViewById(object, parent);
			}
			ViewGroup view = (ViewGroup) v;

			LayoutInflater laInflater = LayoutInflater.from(view.getContext());
			if (LinearLayout.class.isAssignableFrom(view.getClass())) {
				LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
				view.addView(laInflater.inflate(id, null), layout);
			}
			if (RelativeLayout.class.isAssignableFrom(view.getClass())) {
				RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
				view.addView(laInflater.inflate(id, null), layout);
			}
			if (AbsoluteLayout.class.isAssignableFrom(view.getClass())) {
				AbsoluteLayout.LayoutParams layout = new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0);
				view.addView(laInflater.inflate(id, null), layout);
			}
			if (FrameLayout.class.isAssignableFrom(view.getClass())) {
				FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
				view.addView(laInflater.inflate(id, null), layout);
			}
			try {
				Class<?> clazz = Class.forName("android.widget.GridLayout");
				if (clazz.isAssignableFrom(view.getClass())) {
					view.addView(laInflater.inflate(id, null), (ViewGroup.LayoutParams) Class.forName("android.widget.GridLayout.LayoutParams").newInstance());
				}
			} catch (Exception e) {
			}
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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public Class getpClazz() {
		return pClazz;
	}

	public void setpClazz(Class pClazz) {
		this.pClazz = pClazz;
	}
}
