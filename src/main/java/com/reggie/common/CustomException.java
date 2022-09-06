package com.reggie.common;

/**
 * @author TotoroNuo
 * @date 2022/8/30 18:11
 * @function :自定义异常类
 */


public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
