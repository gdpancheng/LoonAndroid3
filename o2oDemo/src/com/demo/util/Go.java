package com.demo.util;

import java.io.File;
import java.util.HashMap;

import com.demo.util.Constant.HttpUrl;
import com.loonandroid.pc.annotation.InForm;
import com.loonandroid.pc.annotation.InNet;
import com.loonandroid.pc.annotation.InPost;

/**
 * 网络接口范例 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * @author gdpancheng@gmail.com 2015年1月7日 下午2:16:21
 * @param <T>
 */
@InNet(HttpUrl.class)
public interface Go {
	
	@InPost//焦点图
	public void focus();
	@InPost//商品列表
	public void goods(HashMap<String, String> params);
	@InPost//商品详情
	public void goods_info(HashMap<String, String> params);
	@InPost//收藏
	public void add_favorite(HashMap<String, String> params);
	@InPost//留言
	public void leave_message(HashMap<String, String> params);
	@InPost//登录
	public void login(HashMap<String, String> params);
	@InPost//添加到购物车
	public void add_cart(HashMap<String, String> params);
	@InPost//品牌
	public void supplier(HashMap<String, String> params);
	@InPost//
	public void evaluate(HashMap<String, String> params);
	@InPost//购物车详情
	public void cart_info(HashMap<String, String> params);
	@InPost//更新购物车
	public void update_cart(HashMap<String, String> params);
	@InPost//更新购物车
	public void delete_goods(HashMap<String, String> params);
	@InPost//搜索
	public void search(HashMap<String, String> params);
	@InPost//用户信息
	public void user_info(HashMap<String, String> params);
	@InForm//编辑头像
	public void edit_avatar(HashMap<String, String> params,HashMap<String, File> files);
	@InPost//编辑个人信息
	public void edit_user(HashMap<String, String> params);
}
