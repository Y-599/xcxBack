package com.ruoyi.web.wexin.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于跳过token的验证
 */
//{ElementType.METHOD用于的方法的注解目标
//{ElementType.TYPE用于类，接口，枚举，注解的注解目标
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)//这种注解将被jvm或被反射机制的代码锁读取和使用
public @interface PassToken {
    boolean required() default true;
}
