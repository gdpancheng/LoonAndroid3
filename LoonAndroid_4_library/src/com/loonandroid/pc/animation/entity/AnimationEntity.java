package com.loonandroid.pc.animation.entity;
/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-14
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class AnimationEntity {

	private float fx = 0.0f;
	private float tx = 0.0f;
	private float fy = 0.0f;
	private float ty = 0.0f;
	private int duration = 0;
	
	private float fAlpha = 0.0f;
	private float tAlpha = 0.0f;
	
	private float fRotate = 0.0f;
	private float tRotate = 0.0f;
	

	public float getFx() {
		return fx;
	}
	public float getTx() {
		return tx;
	}
	public float getFy() {
		return fy;
	}
	public float getTy() {
		return ty;
	}
	public int getDuration() {
		return duration;
	}
	public float getfAlpha() {
		return fAlpha;
	}
	public float gettAlpha() {
		return tAlpha;
	}
	public float getfRotate() {
		return fRotate;
	}
	public float gettRotate() {
		return tRotate;
	}
	
	@Override
    public String toString() {
	    return "AnimationEntity [fx=" + fx + ", tx=" + tx + ", fy=" + fy + ", ty=" + ty + ", duration=" + duration + ", fAlpha=" + fAlpha + ", tAlpha=" + tAlpha + ", fRotate=" + fRotate + ", tRotate=" + tRotate + "]";
    }
}
