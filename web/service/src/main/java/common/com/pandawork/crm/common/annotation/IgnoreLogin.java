package com.pandawork.crm.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略登录注解
 * 加上这个注解后，登录拦截器不会对其进行拦截。注解支持类和方法的注解，其中类的优先级别高于方法级别 *
 * @author: zhangteng
 * @time: 15/10/16 上午10:37
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogin {
    String value() default "";
}
