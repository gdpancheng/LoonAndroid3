package com.loonandroid.pc.ioc;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.entity.OrtherEntity;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonModule;
import com.loonandroid.pc.ioc.entity.ModuleEntity;
import com.loonandroid.pc.ioc.view.DataView;
import com.loonandroid.pc.util.LoonConstant;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月13日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
/**
 * 组件解析类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:09:28
 */
public class XmlFactory implements Factory {

	private Context activity;

	public XmlFactory(Context activity) {
		this.activity = activity;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// 说明是自定义View
		if (name.indexOf(".") != -1) {
			Class<?> clazz = AnalysisManager.getClass(name);
			if (clazz == null) {
				return null;
			}
			// ---------------------------------------------------------------------------
			//
			if (!View.class.isAssignableFrom(clazz)) {
				OrtherEntity ortherEntity = Ioc.getIoc().getAnalysisEntity(name);
				if (ortherEntity == null || ortherEntity.getModuleEntity().size() == 0) {
					return null;
				}
				// -----------------------------------------------------------------------
				// 获取解析记录中的模块 暂不支持模块中嵌套模块
				ModuleEntity entity = ortherEntity.getModuleEntity().get(0);
				if (entity.getId() == LoonConstant.Number.ID_NONE) {
					Ioc.getIoc().getLogger().e(" 组件的布局未设置 请检查 ");
					return null;
				}
				try {
					// ----------------------------------------------------------------------
					// 开始创建代理 为了是让这个组件中的几个方法可以被我们拦截
					// 这个组件的代理类被我实现了 LoonModule这个接口的一系列方法

					// 先创建一个拦截器
					IocModuleHandler moduleHandler = new IocModuleHandler();
					// 创建一个代理类
					Object object = BeanFactory.instance(clazz, new Class[] { LoonModule.class }, moduleHandler);
					// 创建一个布局 然后解析
					LayoutInflater inflater = LayoutInflater.from(context);
					DataView layout = new DataView(context, attrs);
					View view = inflater.inflate(entity.getId(), null);
					moduleHandler.setView(view);
					AnalysisManager.analysisModule(view, object,false);
					LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					layout.addView(view, params);
					layout.setData(object);
					if (null != activity) {
						Field[] fields = activity.getClass().getDeclaredFields();
						for (int i = 0; i < fields.length; i++) {
							Field field = fields[i];
							if (field.getType() == clazz && field.getAnnotation(InModule.class) != null) {
								field.setAccessible(true);
								field.set(activity, object);
								break;
							}
						}
					}
					activity = null;
					return layout;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
}
