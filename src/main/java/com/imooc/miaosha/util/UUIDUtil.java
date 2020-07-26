package com.imooc.miaosha.util;

import java.util.UUID;

public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");	//原生的uuid中间是带横岗的,将横杠替换掉
	}
}
