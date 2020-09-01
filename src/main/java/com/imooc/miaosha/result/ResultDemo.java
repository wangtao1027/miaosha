package com.imooc.miaosha.result;

public class ResultDemo<T> {

    private int code;
    private String message;
    private T data;

    //成功时调用
    public <T> ResultDemo<T> success(T data) {
        return new ResultDemo<T>(data);
    }

    //失败是调用
    public <T> ResultDemo<T> error(CodeMsg cm) {
        return new ResultDemo<T>(cm);
    }

    //成功时调用
    public ResultDemo(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    //失败时调用
    public ResultDemo(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.message = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    //一个成功方法一个失败方法
    //两个方法重载,错误传入信息
}
