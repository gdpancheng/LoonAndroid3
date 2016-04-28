package com.loonandroid.pc.countdown;

import com.loonandroid.pc.tinybus.TinyBus;

import android.os.CountDownTimer;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-6-19
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class IocCountDownTimer extends CountDownTimer {

	public static class TimeEntity{
		
	}
	
	public IocCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {
		TimeEntity entity = new TimeEntity();
		TinyBus.send(entity);
	}

	@Override
	public void onTick(long arg0) {
		TimeEntity entity = new TimeEntity();
		TinyBus.send(entity);
	}
}
