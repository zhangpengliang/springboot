package com.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分表字段的注解，按照该注解字段分配表名
 * 
 * @author Administrator
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {
	int value() default 1;// 代表一共有几张表

	String splicName() default "_";// 分表连接规则
}
