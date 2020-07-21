package com.imooc.miaosha.redis;

public interface KeyPrefix {
		
	public int expireSeconds();		//设置过期时间
	
	public String getPrefix();		//获取前缀
	
}
