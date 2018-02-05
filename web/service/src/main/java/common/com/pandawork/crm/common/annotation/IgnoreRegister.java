package com.pandawork.crm.common.annotation;

import java.lang.annotation.*;

/**
 * IgnoreRegister
 * 忽略注册机注解
 *
 * 加上这个注解后，注册机拦截器不会对其进行拦截。注解支持类和方法的注解，其中类的优先级别高于方法级别
 *
 * @author: yangch
 * @time: 2016/8/3 19:26
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreRegister {
    String value() default "";
}
