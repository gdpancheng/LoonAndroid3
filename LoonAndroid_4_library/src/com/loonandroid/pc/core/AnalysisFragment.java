package com.loonandroid.pc.core;


import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.entity.FragmentEntity;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonFragment;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class AnalysisFragment extends AnalysisCore<FragmentEntity> implements Analysis<FragmentEntity> {

	@Override
	public FragmentEntity process() {
		if (LoonConfig.instance().isDepend()&&null!=clazz.getAnnotation(InLayer.class)) {
			BeanFactory.load(clazz, new Class[]{LoonFragment.class}, null);
			Ioc.getIoc().getLogger().i(" fragment"+clazz+" 挂载完毕");
		}
		return super.process();
	}
}
