package com.loonandroid.pc.handler;

import com.loonandroid.pc.animation.entity.AnimationEntity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-14
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class Handler_Animation {

	
	/**
	 * 移动
	 * @author gdpancheng@gmail.com 2014-11-14 下午2:25:37
	 * @return
	 * @return Animation
	 */
	public static Animation getTranslateAnimation(AnimationEntity animation) {
		return getTranslateAnimation(animation.getFx(), animation.getTx(), animation.getFy(), animation.getTy(), animation.getDuration());
	}
	/**
	 * 移动
	 * @author gdpancheng@gmail.com 2014-11-14 下午2:25:37
	 * @return
	 * @return Animation
	 */
	public static Animation getTranslateAnimation(float fx,float tx,float fy,float ty,int duration) {
		// 实例化TranslateAnimation
		// 以自身为坐标系和长度单位，从(0,0)移动到(1,1)
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fx, Animation.RELATIVE_TO_SELF, tx, Animation.RELATIVE_TO_SELF, fy, Animation.RELATIVE_TO_SELF, ty);
		// 设置动画插值器 被用来修饰动画效果,定义动画的变化率
		animation.setInterpolator(new DecelerateInterpolator());
		// 设置动画执行时间
		animation.setDuration(1000);
		return animation;
	}

	/**
	 * 透明效果
	 * 
	 * @return
	 */
	public static Animation getAlphaAnimation() {
		// 实例化 AlphaAnimation 主要是改变透明度
		// 透明度 从 1-不透明 0-完全透明
		Animation animation = new AlphaAnimation(1.0f, 0.5f);
		// 设置动画插值器 被用来修饰动画效果,定义动画的变化率
		animation.setInterpolator(new DecelerateInterpolator());
		// 设置动画执行时间
		animation.setDuration(1000);
		return animation;
	}

	/**
	 * 缩放动画
	 * 
	 * @return
	 */
	public static Animation getScaleAnimation() {
		// 实例化 ScaleAnimation 主要是缩放效果
		// 参数：fromX-动画开始前，x坐标 toX-动画结束后x坐标
		// fromY-动画开始前，Y坐标 toY-动画结束后Y坐标
		// pivotXType - 为动画相对于物件的X坐标的参照物 pivotXValue - 值
		// pivotYType - 为动画相对于物件的Y坐标的参照物 pivotYValue - 值
		Animation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// 设置动画插值器 被用来修饰动画效果,定义动画的变化率
		animation.setInterpolator(new DecelerateInterpolator());
		// 设置动画执行时间
		animation.setDuration(1000);
		return animation;
	}

	/**
	 * 旋转
	 * 
	 * @return
	 */
	public static Animation getRotateAnimation() {
		// 实例化RotateAnimation
		// 以自身中心为圆心，旋转360度 正值为顺时针旋转，负值为逆时针旋转
		RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// 设置动画插值器 被用来修饰动画效果,定义动画的变化率
		animation.setInterpolator(new DecelerateInterpolator());
		// 设置动画执行时间
		animation.setDuration(1000);
		return animation;
	}
}
