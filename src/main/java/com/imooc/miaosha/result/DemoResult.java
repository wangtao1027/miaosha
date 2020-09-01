package com.imooc.miaosha.result;

/**
 * 封装消息返回类
 */
public class DemoResult<T> {

    private int code;
    private String message;
    private T data;

    //成功访问
    public <T> DemoResult<T> success(T data) {
        return new DemoResult<T>(data);
    }

    //失败访问
    public <T> DemoResult<T> error(CodeMsg cm) {
        return new DemoResult<T>(cm);
    }

    //成功时访问
    public DemoResult(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    //失败时访问
    public DemoResult(CodeMsg cm) {
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
}
