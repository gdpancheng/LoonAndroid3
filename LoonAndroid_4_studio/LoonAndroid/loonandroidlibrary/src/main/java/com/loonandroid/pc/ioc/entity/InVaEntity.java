package com.loonandroid.pc.ioc.entity;

import android.view.View;
import android.widget.TextView;

import com.loonandroid.pc.annotation.InVa.StringType;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.validator.VaRule;
import com.loonandroid.pc.validator.ValidatorCore;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 验证实体类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月3日 上午11:20:54
 */
public class InVaEntity extends Invoker implements InjectInvoker {

	@Override
	public String toString() {
		return "InVaEntity [type=" + type + ", msg=" + msg + ", reg=" + reg + ", index=" + index + ", empty=" + empty + ", minLength=" + minLength + ", maxLength=" + maxLength + ", number=" + strType + ", gt=" + gt + ", lt=" + lt + ", eq=" + eq + ", checked=" + checked + "]";
	}

	private Class type;
	private String msg;
	private String reg;
	private int index;
	private boolean empty;
	private int minLength;
	private int maxLength;
	private StringType strType;
	private double gt;
	private double lt;
	private double eq;
	private boolean checked;
	private InViewEntity inViewEntity;
	private InVaEntity preEntity;
	private InVaOkEntity vaOkEntity;
	private InVaErEntity vaErEntity;
	/**
	 * 验证结果是否成功
	 */
	private boolean isOk = false;

	@Override
	public <T> void invoke(T beanObject, Object... args) {
		if (beanObject != null) {
			inViewEntity.setObject(beanObject);
		}
		if (VaRule.class.isAssignableFrom(type)) {
			try {
				ValidatorCore vaDefault;
				Object va=null;
				if (!type.getName().equals(VaRule.class.getName())) {
					va = type.newInstance();
					vaDefault = (ValidatorCore) va;
				}else {
					vaDefault = new ValidatorCore();
				}
				vaDefault.setChecked(checked);
				vaDefault.setEmpty(empty);
				vaDefault.setEq(eq);
				vaDefault.setGt(gt);
				vaDefault.setLt(lt);
				vaDefault.setPreStr("");
				if (preEntity != null) {
					InVaEntity pre = preEntity;
					View view = pre.getInViewEntity().getView(beanObject);
					while (!TextView.class.isAssignableFrom(view.getClass())) {
						pre = pre.getPreEntity();
						view = pre.getInViewEntity().getView(beanObject);
					}
					vaDefault.setPreStr(((TextView) view).getText().toString().trim());
				}
				vaDefault.setMaxLength(maxLength);
				vaDefault.setMinLength(minLength);
				if (msg.trim().length() > 0) {
					vaDefault.setMsg(msg);
				}
				if (reg.trim().length() > 0) {
					vaDefault.setReg(reg);
				}
				vaDefault.setType(strType);

				View view = inViewEntity.getView(beanObject);
				if (!TextView.class.isAssignableFrom(view.getClass())) {
					isOk = false;
					Ioc.getIoc().getLogger().e("当前验证的View不是TextView");
					;
					return;
				}
				vaDefault.setView((TextView) view);

				if (va!=null) {
					VaRule object = (VaRule) va;
					isOk = object.check();
                }else {
                	isOk = vaDefault.check();
				}
				if (!isOk && vaErEntity != null) {
					vaErEntity.invoke(beanObject, vaDefault);
				}
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		isOk = true;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public StringType getStrType() {
		return strType;
	}

	public void setStrType(StringType strType) {
		this.strType = strType;
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

	public InViewEntity getInViewEntity() {
		return inViewEntity;
	}

	public void setInViewEntity(InViewEntity inViewEntity) {
		this.inViewEntity = inViewEntity;
	}

	public InVaEntity getPreEntity() {
		return preEntity;
	}

	public void setPreEntity(InVaEntity preEntity) {
		this.preEntity = preEntity;
	}

	public InVaOkEntity getVaOkEntity() {
		return vaOkEntity;
	}

	public void setVaOkEntity(InVaOkEntity vaOkEntity) {
		this.vaOkEntity = vaOkEntity;
	}

	public InVaErEntity getVaErEntity() {
		return vaErEntity;
	}

	public void setVaErEntity(InVaErEntity vaErEntity) {
		this.vaErEntity = vaErEntity;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
}
