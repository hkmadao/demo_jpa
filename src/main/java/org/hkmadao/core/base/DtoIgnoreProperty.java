package org.hkmadao.core.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个dto的忽略字段，此字段在转换时不进行赋值，用于dto和entity字段不一致的注解，或者造成循环依赖的字段的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoIgnoreProperty {
    String value() default "";
}
