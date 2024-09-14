package com.marre.aspect;


import com.marre.annotation.AutoFill;
import com.marre.constant.AutoFillConstant;
import com.marre.enumeration.OperationType;
import com.marre.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类， 实现自动填充公共字段
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //定义切入点，通知
    /**
     * 切入点： 对什么类的什么方法进行拦截
     * && 后加入自定义注解 来细化切入点使用的时间地点
     */
    @Pointcut("execution(* com.marre.mapper.*.*(..)) && @annotation(com.marre.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知，通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        //拦截数据库操作类型是 INSERT 还是 UPDATE
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解
        OperationType operationType = autoFill.value();//获得数据库的操作类型

        //获取拦截方法的 参数 -- 实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object entity = args[0];

        //准备需要被赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            log.warn("Current user ID is null. Please ensure it's set properly.");
        }

        //根据不同的数据库操作类型 为属性赋值
        if(operationType == OperationType.INSERT){
            //为4个公共字段赋值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
                log.info("AutoFill for INSERT completed successfully");
            } catch (Exception e) {
                log.error("Error during AutoFill for INSERT", e);
                throw new RuntimeException(e);
            }
        }else if(operationType == OperationType.UPDATE){
            // 为两个公共字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                // 通过反射赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
                log.info("AutoFill for UPDATE completed successfully");
            } catch (Exception e) {
                log.error("Error during AutoFill for INSERT", e);
                throw new RuntimeException(e);
            }
        }
    }
}
