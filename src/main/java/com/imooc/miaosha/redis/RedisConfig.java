package com.imooc.miaosha.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  //配置类
@ConfigurationProperties(prefix = "redis")  //添加前缀,可以将配置类中的类读取到
public class RedisConfig {

    private String host;

    private int port;

    private int timeout;    //秒

    private String password;

    private int poolMaxTotal;

    private int poolMaxIdle;

    private int poolMaxWait;    //秒

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    @Override
    public String toString() {
        return "RedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", timeout=" + timeout +
                ", password='" + password + '\'' +
                ", poolMaxTotal=" + poolMaxTotal +
                ", poolMaxIdle=" + poolMaxIdle +
                ", poolMaxWait=" + poolMaxWait +
                '}';
    }
}
