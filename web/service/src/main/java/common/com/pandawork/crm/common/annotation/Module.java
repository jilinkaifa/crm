package com.pandawork.crm.common.annotation;

import com.pandawork.crm.common.enums.other.ModuleEnums;

import java.lang.annotation.*;

/**
 * Module注解
 *
 * @author: zhangteng
 * @time: 15/10/16 上午10:41
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {

    ModuleEnums value() default ModuleEnums.Null;

    ModuleEnums extModule() default ModuleEnums.Null;
}
