package com.lab.annotation;

import com.lab.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)//标识当前注解会加在什么位置；指定这个注解只能加载在方法上边
@Retention(RetentionPolicy.RUNTIME)//固定写法
public @interface AutoFill {
    //数据库操作类型，UPDATE,INSERT
    OperationType value();
}
