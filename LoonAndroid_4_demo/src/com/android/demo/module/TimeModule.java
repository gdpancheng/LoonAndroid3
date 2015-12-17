package com.android.demo.module;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.TextView;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.countdown.IocCountDownTimer.TimeEntity;
import com.loonandroid.pc.handler.Handler_Time;
import com.loonandroid.pc.interfaces.LoonModule;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.tinybus.Subscribe;
import com.loonandroid.pc.tinybus.TinyBus;

/**
 * 2.2系统中组件的方法必须为public <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月17日 下午8:06:48
 */
@InModule(R.layout.activity_time)
public abstract class TimeModule implements LoonModule {

	private long startTime = 0;
	private long limitTime = 0;
	private Finish finish;
	private ShowText showText;
	
	@InView
	TextView tv_time;

	@Init
	public void init() {
		TinyBus bus = TinyBus.from(Ioc.getIoc().getApplication());
		if (!bus.hasRegistered(TimeModule.this)) {
			TinyBus.from(Ioc.getIoc().getApplication()).register(TimeModule.this);
        }
		tv_time.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
			@Override
			public void onViewDetachedFromWindow(View arg0) {
				TinyBus bus = TinyBus.from(Ioc.getIoc().getApplication());
				if (bus.hasRegistered(TimeModule.this)) {
					TinyBus.from(Ioc.getIoc().getApplication()).unregister(TimeModule.this);
                }
			}

			@Override
			public void onViewAttachedToWindow(View arg0) {
				TinyBus bus = TinyBus.from(Ioc.getIoc().getApplication());
				if (!bus.hasRegistered(TimeModule.this)) {
					TinyBus.from(Ioc.getIoc().getApplication()).register(TimeModule.this);
                }
			}
		});
	}
	
	public void setLimtTime(long startTime, long limitTime) {
		this.startTime = startTime;
		this.limitTime = limitTime;
		showTime();
	}
	
	public void setEndTime(long startTime, long endTime) {
		this.startTime = startTime;
		limitTime = endTime-startTime;
		showTime();
	}
	
	private void showTime(){
		if (startTime == 0 || limitTime == 0) {
			return;
		}
		long s = (startTime + limitTime) - System.currentTimeMillis();
		if (s<0) {
			if (finish!=null) {
				finish.finished(tv_time);
            }
			tv_time.setText("时间已到");
			return;
        }
		if (null!=showText) {
			String time = Handler_Time.formatDuring(s);
			tv_time.setText(showText.show(time.substring(0, time.length())));
        }else {
        	tv_time.setText(Handler_Time.formatDuring(s));
		}
	}
	
	public interface Finish{
		public  void  finished(TextView view);
	}
	
	public interface ShowText{
		public  CharSequence  show(String time);
	}
	
	public Finish getFinish() {
		return finish;
	}

	public void setFinish(Finish finish) {
		this.finish = finish;
	}
	
	@Subscribe
	public void event(TimeEntity event) {
		showTime();
	}
	
	public ShowText getShowText() {
		return showText;
	}

	public void setShowText(ShowText showText) {
		this.showText = showText;
	}

}
