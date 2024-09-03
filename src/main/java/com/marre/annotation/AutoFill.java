package com.marre.annotation;


import com.marre.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某方法需要进行功能字段自动填充
 */

//表示注解生效的地点时间
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型 UPDATE INSERT
    OperationType value();


}
