package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static final String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    //输入框转form
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);    //第一次加密
    }

    //form转db(加盐)
    public static String formPassToDbPass(String formPass,String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);    //第二次加密
    }

    //输入框转db(传入随机盐) 新增用户生成密码随机盐,存入数据库
    public static String inputPassToDbPass(String src, String dbSalt) {
        String inputStr = inputPassToFormPass(src);
        String str = formPassToDbPass(inputStr, dbSalt);
        return str;
    }

    //测试
    public static void main(String[] args) {
        System.out.println(md5("123456"));  //e10adc3949ba59abbe56e057f20f883e
        System.out.println(inputPassToFormPass("123456"));  //d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(formPassToDbPass("123456","1a2b3c4d"));  //d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToDbPass("123456","1a2b3c4d")); //b7797cce01b4b131b433b6acf4add449
    }

}
