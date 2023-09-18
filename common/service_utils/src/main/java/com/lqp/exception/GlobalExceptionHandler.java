package com.lqp.exception;

import com.lqp.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName GlobalExceptionHandler
 * @Package : com.lqp.swagger.exception
 * @Description :
 * @Create on : 2023/8/20 13:00
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
    * 统一异常处理
    * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    /*
    * 特定异常处理
    * */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<Object> error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    /*
    * 自定义异常处理
    * */
    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result<Object> error(GgktException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }

}
