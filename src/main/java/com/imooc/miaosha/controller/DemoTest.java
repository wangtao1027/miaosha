package com.imooc.miaosha.controller;

import com.imooc.miaosha.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/11 17:02
 */
public class DemoTest {
    public static void main(String[] args) {
        Map<String,Object> ms = new HashMap<String, Object>();
        ms.put("orgCode","含香");
        ms.put("params","嘿嘿");
        ms.put("serviceCode","呵呵");
        ms.put("tokenValue","嘻嘻");

        String url = "http://47.100.173.184:9602/dataGet/getMessage";
        String post = HttpClientUtil.post(url, ms);
        System.out.println(post);
    }

}
