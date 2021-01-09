package com.ccx.basic;

import com.ccx.result.CodeException;
import com.ccx.result.ResultEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/10/19 10:53
 * @description：
 * @version: 1.0
 */
@RestControllerAdvice
public class BasicExceptionHandler {


    /**
     * BindCodeException 参数错误异常
     */
    @ExceptionHandler(CodeException.class)
    public ResultEntity<Void> handleMethodArgumentNotValidException(CodeException e) {
        return ResultEntity.fail(e);
    }

    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler(Exception.class)
    public ResultEntity<Void> handleMethodArgumentNotValidException(Exception e) {
        return ResultEntity.fail(e);
    }


}
