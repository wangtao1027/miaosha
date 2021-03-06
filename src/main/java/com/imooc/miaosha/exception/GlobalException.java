package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 695586319123279591L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());   //这一步其实需要不需要都可以
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
