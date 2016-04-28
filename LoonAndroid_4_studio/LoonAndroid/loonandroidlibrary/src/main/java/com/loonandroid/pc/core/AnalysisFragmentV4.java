package com.loonandroid.pc.core;

import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.entity.FragmentV4Entity;
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
public class AnalysisFragmentV4 extends AnalysisCore<FragmentV4Entity> implements Analysis<FragmentV4Entity> {

	@Override
	public FragmentV4Entity process() {
		if (LoonConfig.instance().isDepend()&&null!=clazz.getAnnotation(InLayer.class)) {
			BeanFactory.load(clazz, new Class[]{LoonFragment.class}, null);
			Ioc.getIoc().getLogger().i(" fragment "+clazz+" 挂载完毕");
		}
		return super.process();
	}
}
