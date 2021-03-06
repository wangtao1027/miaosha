package com.imooc.miaosha.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验手机号工具类
 */
public class ValidatorUtil {
	
//	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	private static final Pattern mobile_pattern = Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$");

	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
	
//	public static void main(String[] args) {
//			System.out.println(isMobile("18912341234"));
//			System.out.println(isMobile("1891234123"));
//	}
}
