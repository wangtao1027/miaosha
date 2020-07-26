package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice      //声明切面
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)  //拦截所有异常,如果拦截指定异常只需输入想要的异常类型
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();    //打印异常详细信息
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return Result.error(globalException.getCm());
        } else if (e instanceof BindException) {    //参数绑定异常
            org.springframework.validation.BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();   //取很多个错误列表
            ObjectError error = errors.get(0);  //默认取第一(为什么要取第一个,因为报错的时候通常第一个是引起的错误)
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));  //绑定异常,参数替换
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
