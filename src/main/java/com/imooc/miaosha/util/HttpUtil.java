package com.imooc.miaosha.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wangtao
 * Created on 2020/6/4.
 */
@Slf4j
public class HttpUtil {

    private static final int READ_TIMEOUT = 60000;

    private static final int CONNECT_TIMEOUT = 60000;

    //发送post请求
    public static String sendPost(String url, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        String substring = sb.toString().substring(0, sb.length() - 1);
        StringBuilder result = new StringBuilder();
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            byte contentbyte[] = substring.getBytes();
            //设置请求类型
//            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置表单长度
            conn.setRequestProperty("Content-Length", (new StringBuilder()).append(contentbyte.length).toString());
            //设置默认语言
            conn.setRequestProperty("Content-Language", "en-US");//zh-CN代表中国  默认为美式英语
            //连接主机的超时时间（单位：毫秒）
            conn.setConnectTimeout(60000);
            //从主机读取数据的超时时间（单位：毫秒)
            conn.setReadTimeout(60000);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            // http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "GBK"));
            bWriter.write(substring);
            bWriter.flush();
            bWriter.close();
            // 调用HttpURLConnection连接对象的getInputStream()函数,
            //此方法是用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                result.append(lines);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    //发送get请求
    public static String get(String urlAddr, Map<String, Object> paramsMap, int connectTimeout, int readTimeout) throws Exception {
        log.info("get request url: {}, params: {}", urlAddr, JSONObject.toJSONString(paramsMap));
        String line;
        String params = "";
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer result = new StringBuffer();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuffer str = new StringBuffer();
                Set set = paramsMap.keySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    if (paramsMap.get(key) == null) {
                        continue;
                    }
                    str.append(key).append("=").append(paramsMap.get(key)).append("&");
                }
                if (str.length() > 0) {
                    params = "?" + str.substring(0, str.length() - 1);
                }
            }
            URL url = new URL(urlAddr + params);
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            conn.connect();
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

}
