package com.loonandroid.pc.validator;

import java.lang.ref.WeakReference;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.loonandroid.pc.annotation.InVa.StringType;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月2日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class ValidatorCore {

	private WeakReference<View> view;
	private String msg;
	private String reg;
	private String preStr;
	private boolean empty = true;
	private int minLength;
	private int maxLength;
	private StringType type;
	private double gt;
	private double lt;
	private double eq;
	private boolean checked;

	public ValidatorCore() {
		init();
	}

	/**
	 * 所有回调的检测 默认走这里
	 */
	public boolean check() {
		if (view==null||view.get()==null) {
	        return false;
        }
		if (CompoundButton.class.isAssignableFrom(view.get().getClass())) {
	        CompoundButton button = (CompoundButton) view.get();
	        if (checked&&!button.isChecked()) {
	            return false;
            }
        }
		String str = ((TextView)(view.get())).getText().toString().trim();
		if (!empty&&str.trim().length()==0) {
	        return false;
        }
		
		if (str.length()<minLength||str.length()>maxLength) {
			return false;
        }
		
		boolean result = true;
		switch (type) {
		case INTEGER:
			result = Regex.Regular(str, Regex.POSITIVE_INTEGER_REGEXP);
			break;
		case LONG:
			result = Regex.Regular(str, Regex.REGEX_INTEGER);
			result = result?(Long.valueOf(str)>Integer.MAX_VALUE||Long.valueOf(str)<Integer.MIN_VALUE):result;
			break;
		case DOUBLE:
			result = Regex.Regular(str, Regex.DOUBLE);
			result = result?(Double.valueOf(str)<gt&&Double.valueOf(str)>lt):result;
			if (Double.MAX_VALUE!=eq) {
				result = eq == Double.valueOf(str);
            }
			break;
		case STRING:
			result = true;
			break;
		}
		if (!result) {
	        return false;
        }
		
		if (reg!=null&&reg.trim().length()>0) {
	        return Regex.Regular(str, reg.trim());
        }
		
		return true;
	}

	public void init() {
	}

	public <T> T getView() {
		return (T) (view==null?null:view.get());
	}

	public void setView(View view) {
		this.view = new WeakReference<View>(view);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public StringType getType() {
		return type;
	}

	public void setType(StringType type) {
		this.type = type;
	}

	public double getGt() {
		return gt;
	}

	public void setGt(double gt) {
		this.gt = gt;
	}

	public double getLt() {
		return lt;
	}

	public void setLt(double lt) {
		this.lt = lt;
	}

	public double getEq() {
		return eq;
	}

	public void setEq(double eq) {
		this.eq = eq;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPreStr() {
		return preStr;
	}

	public void setPreStr(String preStr) {
		this.preStr = preStr;
	}
	@Override
    public String toString() {
	    return "ValidatorCore [view=" + view + ", msg=" + msg + ", reg=" + reg + ", preStr=" + preStr + ", empty=" + empty + ", minLength=" + minLength + ", maxLength=" + maxLength + ", type=" + type + ", gt=" + gt + ", lt=" + lt + ", eq=" + eq + ", checked=" + checked + "]";
    }

}
