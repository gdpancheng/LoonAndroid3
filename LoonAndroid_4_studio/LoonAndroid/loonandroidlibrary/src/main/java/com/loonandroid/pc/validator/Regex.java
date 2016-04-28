package com.loonandroid.pc.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月2日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class Regex {

	public static final String EMPTY_STRING = "";
	/**
	 * 数字
	 */
	public static final String REGEX_INTEGER = "\\d+";
	/**
	 * 十进制小数位
	 */
	public static final String REGEX_DECIMAL = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
	// public static final String REGEX_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/**
	 * IP地址
	 */
	public static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * Email正则表达式="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	 */
	// public static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";;
	public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
	/**
	 * 电话号码正则表达式= (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
	 */
	public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
	/**
	 * 手机号码正则表达式=^(13[0-9]|15[0-9]|18[0-9])\d{8}$
	 */
	public static final String MOBILE = "^(13[0-9]|15[0-9]|18[0-9])\\d{8}$";

	/**
	 * Integer正则表达式 ^-?(([1-9]\d*$)|0)
	 */
	public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
	/**
	 * 正整数正则表达式 >=0 ^[1-9]\d*|0$
	 */
	public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
	/**
	 * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
	 */
	public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
	/**
	 * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
	 */
	public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
	/**
	 * 正Double正则表达式 >=0 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$　
	 */
	public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
	/**
	 * 负Double正则表达式 <= 0 ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
	 */
	public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
	/**
	 * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
	 */
	public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
	/**
	 * 邮编正则表达式 [0-9]\d{5}(?!\d) 国内6位邮编
	 */
	public static final String CODE = "[0-9]\\d{5}(?!\\d)";
	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
	 */
	public static final String STR_ENG_NUM_ = "^\\w+$";
	/**
	 * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
	 */
	public static final String STR_ENG_NUM = "^[A-Za-z0-9]+";
	/**
	 * 匹配由26个英文字母组成的字符串 ^[A-Za-z]+$
	 */
	public static final String STR_ENG = "^[A-Za-z]+$";
	/**
	 * 过滤特殊字符串正则 regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	 */
	public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	/***
	 * 日期正则 支持： YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD的形式
	 */
	public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" + "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" + "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" + "(0?2)([-\\/\\._]?)(29)$)" + "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" + "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|"
	        + "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
	/***
	 * 日期正则 支持： YYYY-MM-DD
	 */
	public static final String DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	/**
	 * URL正则表达式 匹配 http www ftp
	 */
	public static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 身份证正则表达式
	 */
	public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" + "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" + "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
	/**
	 * 匹配图象
	 *
	 *
	 * 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png)
	 *
	 * 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp
	 *
	 *
	 * 不匹配: c:/admins4512.gif
	 *
	 */
	public static final String ICON_REGEXP = "^(/{0,1}//w){1,}//.(gif|dmp|png|jpg)$|^//w{1,}//.(gif|dmp|png|jpg)$";

	/**
	 * 匹配email地址
	 *
	 *
	 * 格式: XXX@XXX.XXX.XX
	 *
	 * 匹配 : foo@bar.com 或 foobar@foobar.com.au
	 *
	 * 不匹配: foo@bar 或 $$$@bar.com
	 *
	 */
	public static final String EMAIL_REGEXP = "(?://w[-._//w]*//w@//w[-._//w]*//w//.//w{2,3}$)";

	/**
	 * 匹配匹配并提取url
	 *
	 *
	 * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	 *
	 * 匹配 : http://www.suncer.com 或news://www
	 *
	 * 不匹配: c:/window
	 *
	 */
	public static final String URL_REGEXP = "(//w+)://([^/:]+)(://d*)?([^#//s]*)";

	/**
	 * 匹配并提取http
	 *
	 * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或 https://XXX
	 *
	 * 匹配 : http://www.suncer.com:8080/index.html?login=true
	 *
	 * 不匹配: news://www
	 *
	 */
	public static final String HTTP_REGEXP = "(http|https|ftp)://([^/:]+)(://d*)?([^#//s]*)";

	/**
	 * 匹配日期
	 *
	 *
	 * 格式(首位不为0): XXXX-XX-XX或 XXXX-X-X
	 *
	 *
	 * 范围:1900--2099
	 *
	 *
	 * 匹配 : 2005-04-04
	 *
	 *
	 * 不匹配: 01-01-01
	 *
	 */
	public static final String DATE_BARS_REGEXP = "^((((19){1}|(20){1})\\d{2})|\\d{2})-[0,1]?\\d{1}-[0-3]?\\d{1}$";

	/**
	 * 匹配日期
	 *
	 *
	 * 格式: XXXX/XX/XX
	 *
	 *
	 * 范围:
	 *
	 *
	 * 匹配 : 2005/04/04
	 *
	 *
	 * 不匹配: 01/01/01
	 *
	 */
	public static final String DATE_SLASH_REGEXP = "^[0-9]{4}/(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))$";

	/**
	 * 匹配电话
	 *
	 *
	 * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或
	 *
	 * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或 XXXXXXXXXXX(11位首位不为0)
	 *
	 *
	 * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或 010-12345678 或 12345678912
	 *
	 *
	 * 不匹配: 1111-134355 或 0123456789
	 *
	 */
	public static final String PHONE_REGEXP = "^(?:0[0-9]{2,3}[-//s]{1}|//(0[0-9]{2,4}//))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * 匹配身份证
	 *
	 * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或 XXXXXXXXXXXXXXXXXX(18位)
	 *
	 * 匹配 : 0123456789123
	 *
	 * 不匹配: 0123456
	 *
	 */
	public static final String ID_CARD_REGEXP = "^//d{10}|//d{13}|//d{15}|//d{18}$";

	/**
	 * 匹配邮编代码
	 *
	 * 格式为: XXXXXX(6位)
	 *
	 * 匹配 : 012345
	 *
	 * 不匹配: 0123456
	 *
	 */
	public static final String ZIP_REGEXP = "^[0-9]{6}$";// 匹配邮编代码

	/**
	 * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠/ 即空格,制表符,回车符等 )
	 *
	 * 格式为: x 或 一个一上的字符
	 *
	 * 匹配 : 012345
	 *
	 * 不匹配: 0123456 // ;,:-<>//s].+$";//
	 */
	public static final String NON_SPECIAL_CHAR_REGEXP = "^[^'/";
	// 匹配邮编代码

	/**
	 * 匹配非负整数（正整数 + 0)
	 */
	public static final String NON_NEGATIVE_INTEGERS_REGEXP = "^//d+$";

	/**
	 * 匹配不包括零的非负整数（正整数 > 0)
	 */
	public static final String NON_ZERO_NEGATIVE_INTEGERS_REGEXP = "^[1-9]+//d*$";

	/**
	 *
	 * 匹配正整数
	 *
	 */
	public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";

	/**
	 *
	 * 匹配非正整数（负整数 + 0）
	 *
	 */
	public static final String NON_POSITIVE_INTEGERS_REGEXP = "^((-//d+)|(0+))$";

	/**
	 *
	 * 匹配负整数
	 *
	 */
	public static final String NEGATIVE_INTEGERS_REGEXP = "^-[0-9]*[1-9][0-9]*$";

	/**
	 *
	 * 匹配整数
	 *
	 */
	public static final String INTEGER_REGEXP = "^-?//d+$";

	/**
	 *
	 * 匹配非负浮点数（正浮点数 + 0）
	 *
	 */
	public static final String NON_NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^//d+(//.//d+)?$";

	/**
	 *
	 * 匹配正浮点数
	 *
	 */
	public static final String POSITIVE_RATIONAL_NUMBERS_REGEXP = "^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 *
	 * 匹配非正浮点数（负浮点数 + 0）
	 *
	 */
	public static final String NON_POSITIVE_RATIONAL_NUMBERS_REGEXP = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";

	/**
	 *
	 * 匹配负浮点数
	 *
	 */
	public static final String NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^(-(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 *
	 * 匹配浮点数
	 *
	 */
	public static final String RATIONAL_NUMBERS_REGEXP = "^(-?//d+)(//.//d+)?$";

	/**
	 *
	 * 匹配由26个英文字母组成的字符串
	 *
	 */
	public static final String LETTER_REGEXP = "^[A-Za-z]+$";

	/**
	 *
	 * 匹配由26个英文字母的大写组成的字符串
	 *
	 */
	public static final String UPWARD_LETTER_REGEXP = "^[A-Z]+$";

	/**
	 *
	 * 匹配由26个英文字母的小写组成的字符串
	 *
	 */
	public static final String LOWER_LETTER_REGEXP = "^[a-z]+$";

	/**
	 *
	 * 匹配由数字和26个英文字母组成的字符串
	 *
	 */
	public static final String LET_NUM_REG = "^[A-Za-z0-9]+$";

	/**
	 *
	 * 匹配由数字、26个英文字母或者下划线组成的字符串
	 *
	 */
	public static final String LET_NUM_UNLINE_REG = "\\w+$";
	/**
	 * 机构代码
	 */
	public static final String JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";

	/**
	 * 匹配数字组成的字符串 ^[0-9]+$
	 */
	public static final String STR_NUM = "^[0-9]+$";

	// //------------------验证方法
	/**
	 * 判断字段是否为空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static synchronized boolean StrisNull(String str) {
		return null == str || str.trim().length() <= 0 ? true : false;
	}

	/**
	 * 判断字段是非空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean StrNotNull(String str) {
		return !StrisNull(str);
	}

	/**
	 * 字符串null转空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String nulltoStr(String str) {
		return StrisNull(str) ? "" : str;
	}

	/**
	 * 字符串null赋值默认值
	 * 
	 * @param str
	 *            目标字符串
	 * @param defaut
	 *            默认值
	 * @return String
	 */
	public static String nulltoStr(String str, String defaut) {
		return StrisNull(str) ? defaut : str;
	}

	/**
	 * 判断字段是否为Email 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		return Regular(str, EMAIL);
	}

	/**
	 * 判断是否为电话号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isPhone(String str) {
		return Regular(str, PHONE);
	}

	/**
	 * 判断是否为手机号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		return Regular(str, MOBILE);
	}

	/**
	 * 判断是否为Url 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isUrl(String str) {
		return Regular(str, URL);
	}

	/**
	 * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		return Regular(str, DOUBLE);
	}

	/**
	 * 判断字段是否为INTEGER 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		return Regular(str, INTEGER);
	}

	/**
	 * 判断字段是否为正整数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isINTEGER_NEGATIVE(String str) {
		return Regular(str, INTEGER_NEGATIVE);
	}

	/**
	 * 判断字段是否为负整数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isINTEGER_POSITIVE(String str) {
		return Regular(str, INTEGER_POSITIVE);
	}

	/**
	 * 判断字段是否为DOUBLE 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDouble(String str) {
		return Regular(str, DOUBLE);
	}

	/**
	 * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDOUBLE_NEGATIVE(String str) {
		return Regular(str, DOUBLE_NEGATIVE);
	}

	/**
	 * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDOUBLE_POSITIVE(String str) {
		return Regular(str, DOUBLE_POSITIVE);
	}

	/**
	 * 判断字段是否为日期 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDate(String str) {
		return Regular(str, DATE_ALL);
	}

	/**
	 * 验证2010-12-10
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate1(String str) {
		return Regular(str, DATE_FORMAT1);
	}

	/**
	 * 判断字段是否为年龄 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isAge(String str) {
		return Regular(str, AGE);
	}

	/**
	 * 判断字段是否超长 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
	 * 
	 * @param str
	 * @param leng
	 * @return boolean
	 */
	public static boolean isLengOut(String str, int leng) {
		return StrisNull(str) ? false : str.trim().length() > leng;
	}

	/**
	 * 判断字段是否为身份证 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIdCard(String str) {
		if (StrisNull(str))
			return false;
		if (str.trim().length() == 15 || str.trim().length() == 18) {
			return Regular(str, IDCARD);
		} else {
			return false;
		}

	}

	/**
	 * 判断字段是否为邮编 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isCode(String str) {
		return Regular(str, CODE);
	}

	/**
	 * 判断字符串是不是全部是英文字母
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEnglish(String str) {
		return Regular(str, STR_ENG);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isENG_NUM(String str) {
		return Regular(str, STR_ENG_NUM);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字+下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isENG_NUM_(String str) {
		return Regular(str, STR_ENG_NUM_);
	}

	/**
	 * 过滤特殊字符串 返回过滤后的字符串
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String filterStr(String str) {
		Pattern p = Pattern.compile(STR_SPECIAL);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 校验机构代码格式
	 * 
	 * @return
	 */
	public static boolean isJigouCode(String str) {
		return Regular(str, JIGOU_CODE);
	}

	/**
	 * 判断字符串是不是数字组成
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isSTR_NUM(String str) {
		return Regular(str, STR_NUM);
	}

	/**
	 * 匹配是否符合正则表达式pattern 匹配返回true
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param pattern
	 *            匹配模式
	 * @return boolean
	 */
	public static boolean Regular(String str, String pattern) {
		if (null == str || str.trim().length() <= 0)
			return false;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

}
