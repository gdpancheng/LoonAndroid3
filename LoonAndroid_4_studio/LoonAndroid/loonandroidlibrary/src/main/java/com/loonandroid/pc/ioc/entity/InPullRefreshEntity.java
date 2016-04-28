package com.loonandroid.pc.ioc.entity;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 下拉和上拉的对象
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:26:33
 */
public class InPullRefreshEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8113260534550077991L;

	private boolean pull;
	private boolean down;

	public boolean isPull() {
		return pull;
	}

	public void setPull(boolean pull) {
		this.pull = pull;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
}
