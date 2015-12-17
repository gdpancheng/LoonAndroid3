package com.demo.util;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-7-23
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
public class Constant {

	/**
	 * URL变量名必须大写 才能和Go中的方法匹配 <br>
	 * ----------------------------------------------- <br>
	 * oooO............... <br>
	 * (....) ... Oooo... <br>
	 * .\..(.....(.....)....... <br>
	 * ..\_)..... )../........ <br>
	 * .......... (_/.......... <br>
	 * 微信 gdpancheng <br>
	 * -----------------------------------------------
	 * 
	 * @author gdpancheng@gmail.com 2015年12月9日 下午7:21:57
	 */
	public static class HttpUrl {
		/**
		 * 通用url
		 */
		public static String url_common = "http://cs2.137home.com/index.php?m=api&a=";
		/**
		 * 获取首页焦点图
		 */
		public static String FOCUS = url_common + "get_focus";
		/**
		 * 获取商品列表
		 */
		public static String GOODS = url_common + "get_goods_list";
		/**
		 * 获取商品详细信息
		 */
		public static String GOODS_INFO = url_common + "get_detail";
		/**
		 * 注册
		 */
		public static String url_register = url_common + "reg";
		/**
		 * 根据关键字搜索
		 */
		public static String SEARCH = url_common + "get_goods_list_by_keywords";
		/**
		 * 登录
		 */
		public static String LOGIN = url_common + "login";
		/**
		 * 注册
		 */
		public static String url_reg = url_common + "reg";
		/**
		 * 获取用户信息
		 */
		public static String USER_INFO = url_common + "get_user_info";
		/**
		 * 获取品牌信息
		 */
		public static String SUPPLIER = url_common + "get_supplier";

		/**
		 * 编辑头像
		 */
		public static String EDIT_AVATAR = url_common + "edit_avatar";
		/**
		 * 编辑用户信息
		 */
		public static String EDIT_USER = url_common + "edit_user";
		/**
		 * 获取收获地址
		 */
		public static String address_info = url_common + "address_list";
		/**
		 * 增加收获地址
		 */
		public static String add_address = url_common + "address";
		/**
		 * 抢购
		 */
		public static String get_scare = url_common + "scare";

		/**
		 * 留言
		 */
		public static String LEAVE_MESSAGE = url_common + "evaluate";

		/**
		 * 拍照的存储
		 */
		public static final String save_user_photo = "xiamen_photo.jpg";
		/**
		 * 添加到购物车
		 */
		public static String ADD_CART = url_common + "add_cart";
		/**
		 * 获取购物车信息
		 */
		public static String CART_INFO = url_common + "get_cart_info";
		/**
		 * 收藏商品
		 */
		public static String ADD_FAVORITE = url_common + "add_favorite";
		/**
		 * 收藏列表
		 */
		public static String my_favorite = url_common + "my_favorite";
		/**
		 * 删除收藏
		 */
		public static String del_favorite = url_common + "del_favorite";

		/**
		 * 收藏列表
		 */
		public static String EVALUATE = url_common + "evaluate_list";

		/**
		 * 收藏列表
		 */
		public static String UPDATE_CART = url_common + "update_cart";

		/**
		 * 收藏列表
		 */
		public static String DELETE_GOODS = url_common + "delete_item";
		/**
		 * 删除地址
		 */
		public static String delete_address = url_common + "delete_address";

		/**
		 * 更新地址
		 */
		public static String update_address = url_common + "update_address";

		/**
		 * 下单
		 */
		public static String order = url_common + "order";

		/**
		 * 订单列表
		 */
		public static String order_list = url_common + "order_list";

		// ---------------------------------------------------------------------------
		// 在有需要的地方 以 请求url变量名后面_KEY 来声明编号 以此对应一个类中有多个网络请求返回
		public static final int GOODS_INFO_KEY = 0;
		public static final int ADD_FAVORITE_KEY = 1;
		public static final int LEAVE_MESSAGE_KEY = 2;
		public static final int ADD_CART_KEY = 3;
		public static final int CART_INFO_KEY = 4;
		public static final int UPDATE_CART_KEY = 5;
		public static final int DELETE_GOODS_KEY = 6;
		public static final int USER_INFO_KEY = 7;
		public static final int EDIT_AVATAR_KEY = 8;
	}

	public static ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>() {
		{
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("name", "卫浴瓷砖");
			add(params);
			HashMap<String, Object> params1 = new HashMap<String, Object>();
			params1.put("name", "地板木业");
			add(params1);
			HashMap<String, Object> params2 = new HashMap<String, Object>();
			params2.put("name", "精品家具");
			add(params2);
			HashMap<String, Object> params3 = new HashMap<String, Object>();
			params3.put("name", "橱柜厨电");
			add(params3);
			HashMap<String, Object> params4 = new HashMap<String, Object>();
			params4.put("name", "五金软装");
			add(params4);
		}
	};

	public static HashMap<String, ArrayList<HashMap<String, Object>>> data2 = new HashMap<String, ArrayList<HashMap<String, Object>>>() {
		{
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("name", "卫浴");
			params.put("id", "472");
			list.add(params);
			HashMap<String, Object> params1 = new HashMap<String, Object>();
			params1.put("name", "瓷砖");
			params1.put("id", "483");
			list.add(params1);
			put("卫浴瓷砖", list);

			ArrayList<HashMap<String, Object>> list1 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> params2 = new HashMap<String, Object>();
			params2.put("name", "地板");
			params2.put("id", "452");
			list1.add(params2);
			HashMap<String, Object> params3 = new HashMap<String, Object>();
			params3.put("name", "门窗");
			params3.put("id", "561");
			list1.add(params3);
			put("地板木业", list1);

			ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> params4 = new HashMap<String, Object>();
			params4.put("name", "家具");
			params4.put("id", "492");
			list2.add(params4);
			HashMap<String, Object> params5 = new HashMap<String, Object>();
			params5.put("name", "家具尚品");
			params5.put("id", "492");
			list2.add(params5);
			put("精品家具", list2);

			ArrayList<HashMap<String, Object>> list3 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> params6 = new HashMap<String, Object>();
			params6.put("name", "橱柜厨电");
			params6.put("id", "459");
			list3.add(params6);
			HashMap<String, Object> params7 = new HashMap<String, Object>();
			params7.put("name", "名品私厨");
			params7.put("id", "459");
			list3.add(params7);
			put("橱柜厨电", list3);

			ArrayList<HashMap<String, Object>> list4 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> params8 = new HashMap<String, Object>();
			params8.put("name", "灯饰");
			params8.put("id", "548");
			list4.add(params8);
			HashMap<String, Object> params9 = new HashMap<String, Object>();
			params9.put("name", "软装");
			params9.put("id", "562");
			list4.add(params9);
			HashMap<String, Object> params10 = new HashMap<String, Object>();
			params10.put("name", "五金");
			params10.put("id", "572");
			list4.add(params10);
			HashMap<String, Object> params11 = new HashMap<String, Object>();
			params11.put("name", "其他");
			params11.put("id", "574");
			list4.add(params11);
			put("五金软装", list4);

		}
	};

	public static HashMap<String, ArrayList<HashMap<String, Object>>> data3 = new HashMap<String, ArrayList<HashMap<String, Object>>>();

	public static void setData(String key, String name, String id, String type) {
		if (!data3.containsKey(key)) {
			data3.put(key, new ArrayList<HashMap<String, Object>>());
		}
		ArrayList<HashMap<String, Object>> list = data3.get(key);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("id", id);
		map.put("type", type);
		list.add(map);
	}

	// 三级
	public static void init() {
		// 0表示分类 1 表示品牌
		setData("卫浴", "马桶/座便器", "473", "0");
		setData("卫浴", "淋浴房", "474", "0");
		setData("卫浴", "浴缸", "475", "0");
		setData("卫浴", "浴室柜", "477", "0");
		setData("卫浴", "龙头", "478", "0");
		setData("卫浴", "花洒", "479", "0");
		setData("卫浴", "木桶/浴桶", "826", "0");
		setData("卫浴", "其他卫浴", "482", "0");
		setData("卫浴", "TOTO", "35", "1");
		setData("卫浴", "科勒", "2", "1");
		setData("卫浴", "中宇卫浴", "38", "1");
		setData("卫浴", "九牧", "27", "1");
		setData("卫浴", "帝王", "30", "1");
		setData("卫浴", "辉煌", "7", "1");

		setData("瓷砖", "地砖", "484", "0");
		setData("瓷砖", "内墙砖", "485", "0");
		setData("瓷砖", "玻化砖", "827", "0");
		setData("瓷砖", "瓷片", "830", "0");
		setData("瓷砖", "其他瓷砖", "490", "0");
		setData("瓷砖", "诺贝尔", "36", "1");
		setData("瓷砖", "意大利蜜蜂瓷砖", "15", "1");
		setData("瓷砖", "特地", "37", "1");
		setData("瓷砖", "意大利名砖世家", "17", "1");
		setData("瓷砖", "马可波罗", "25", "1");
		setData("瓷砖", "斯米克瓷砖", "12", "1");

		setData("地板", "实木地板", "453", "0");
		setData("地板", "强化复合地板", "455", "0");
		setData("地板", "圣象地板", "90", "1");
		setData("地板", "大自然地板", "95", "1");
		setData("地板", "宜人地板", "89", "1");
		setData("地板", "阿姆斯壮", "54", "1");
		setData("地板", "安信", "65", "1");
		setData("地板", "圣保罗", "56", "1");
		setData("地板", "绿精灵", "93", "1");
		setData("地板", "生活家", "55", "1");

		setData("门窗", "原木门", "850", "0");
		setData("门窗", "复合门", "851", "0");
		setData("门窗", "防盗门", "852", "0");
		setData("门窗", "铝合金门窗", "853", "0");
		setData("门窗", "楼梯", "854", "0");
		setData("门窗", "富奥斯木门", "83", "1");
		setData("门窗", "大自然木门", "72", "1");
		setData("门窗", "圣象木门", "82", "1");
		setData("门窗", "金迪木门 ", "64", "1");
		setData("门窗", "迦南", "91", "1");
		setData("门窗", "枫景", "88", "1");
		setData("门窗", "福万家", "81", "1");

		setData("家具", "实木家具", "493", "0");
		setData("家具", "板式家具", "494", "0");
		setData("家具", "儿童家具", "500", "0");
		setData("家具", "沙发", "506", "0");
		setData("家具", "床", "512", "0");
		setData("家具", "椅子", "520", "0");
		setData("家具", "桌子", "533", "0");
		setData("家具", "柜子", "543", "0");
		setData("家具", "茶几", "545", "0");
		setData("家具", "其他家具", "547", "0");

		setData("家具尚品", "巨桑家私", "151", "1");
		setData("家具尚品", "我爱我家至白", "163", "1");
		setData("家具尚品", "皇朝傢俬", "70", "1");
		setData("家具尚品", "榜样尚品", "143", "1");
		setData("家具尚品", "左尚明舍", "75", "1");
		setData("家具尚品", "麦仕", "98", "1");
		setData("家具尚品", "芝华仕", "105", "1");
		setData("家具尚品", "中至信", "190", "1");
		setData("家具尚品", "紫金梨木", "167", "1");
		setData("家具尚品", "森林夏娃", "161", "1");
		setData("家具尚品", "顾家家居 ", "106", "1");
		setData("家具尚品", "有缘阁", "179", "1");
		setData("家具尚品", "摩卡生活", "138", "1");
		setData("家具尚品", "DE工艺", "170", "1");
		setData("家具尚品", "诺亚", "124", "1");
		setData("家具尚品", "朗乐福", "126", "1");

		setData("橱柜厨电", "橱柜", "460", "0");
		setData("橱柜厨电", "厨房电器", "462", "0");
		setData("橱柜厨电", "热水器", "468", "0");
		setData("橱柜厨电", "净水机", "469", "0");
		setData("橱柜厨电", "厨房配件", "470", "0");
		setData("橱柜厨电", "整体厨房", "471", "0");

		setData("名品私厨", "美的", "58", "1");
		setData("名品私厨", "美大", "41", "1");
		setData("名品私厨", "华帝厨房", "44", "1");
		setData("名品私厨", "老板", "45", "1");
		setData("名品私厨", "欧派橱柜", "51", "1");
		setData("名品私厨", "AO史密斯", "46", "1");
		setData("名品私厨", "柏林世家", "63", "1");
		setData("名品私厨", "好兆头", "60", "1");
		setData("名品私厨", "好来屋", "84", "1");
		setData("名品私厨", "金牌橱柜", "94", "1");
		setData("名品私厨", "苏泊尔", "270", "1");
		setData("名品私厨", "华君石", "50", "1");
		setData("名品私厨", "方太", "42", "1");

		setData("灯饰", "吊灯", "549", "0");
		setData("灯饰", "吸顶灯", "550", "0");
		setData("灯饰", "壁灯", "551", "0");
		setData("灯饰", "其他灯", "557", "0");
		setData("灯饰", "文统照明", "212", "1");
		setData("灯饰", "松下照明", "200", "1");
		setData("灯饰", "欧普", "213", "1");
		setData("灯饰", "佳信昌", "204", "1");
		setData("灯饰", "鸿雁", "206", "1");
		setData("灯饰", "金品灯饰", "199", "1");

		setData("软装", "窗帘", "563", "0");
		setData("软装", "床品", "569", "0");
		setData("软装", "壁纸", "570", "0");
		setData("软装", "装饰品", "571", "0");
		setData("软装", "地毯", "848", "0");
		setData("软装", "墙布", "849", "0");
		setData("软装", "白璧", "210", "1");
		setData("软装", "洛可可饰品", "246", "1");
		setData("软装", "汉帛布艺", "217", "1");

		setData("五金", "全部", "572", "1");
		setData("五金", "好太太", "198", "1");
		setData("五金", "雅洁", "208", "1");

		setData("其他", "硅藻泥", "842", "0");
		setData("其他", "晾衣架", "844", "0");
		setData("其他", "保险柜", "847", "0");
		setData("其他", "今顶", "225", "1");
		setData("其他", "迪堡", "195", "1");
		setData("其他", "康华", "196", "1");
		setData("其他", "沃土", "201", "1");
		setData("其他", "氧宜多", "218", "1");

	}
}
