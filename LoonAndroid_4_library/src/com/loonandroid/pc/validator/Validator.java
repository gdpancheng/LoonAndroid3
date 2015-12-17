package com.loonandroid.pc.validator;

import com.loonandroid.pc.core.AnalysisManager;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月26日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 字符串验证类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年11月26日 下午5:04:26
 */
public class Validator {

	/**
	 * 有关UI上文字的验证
	 * 
	 * @author gdpancheng@gmail.com 2015年11月26日 下午5:06:03
	 * @param object
	 * @return void
	 */
	public static void verify(Object object) {
		AnalysisManager.analysisValidator(object);
	}
}
