package com.xxq.common.annotation;
import java.lang.annotation.*;
/**
 * 锁的参数
 * @author Levin
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributLockParam {
    /**
     * 字段名称
     * @return String
     */
    String name() default "";
}