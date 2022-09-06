package com.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author TotoroNuo
 * @date 2022/8/27 15:13
 * @function :全局异常处理
 */

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * 主要用于添加时id冲突
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            //空格切字符串
            String[] split = e.getMessage().split(" ");
            String msg = split[2] + "已经存在";
            return R.error(msg);
        }
        return R.error("SQL错误");
    }

    /**
     * 自定义异常
     * 用于删除时菜品绑定
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e) {
        return R.error(e.getMessage());
    }
}
