package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.BindException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> getException(HttpServletResponse response, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            return Result.error(globalException.getCm());
        } else if (e instanceof BindException) {
            return null;
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
