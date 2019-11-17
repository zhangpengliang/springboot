package com.boot.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(-1)
public class DynamicDataSourceAspect {

	@Before("execution(* com.*.mapper..*.*(..))")
	public void before(JoinPoint point) {
		// 本来想通过拦截接口上的注解，通过注解的值来获取需要的数据源，然后通过dataSourceRouting来设置，但是研究过后，发现无法拦截接口上的注解
		
	}

	@After("execution(* com.*.mapper..*.*(..))")
	public void after(JoinPoint point) {
		DataSourceRouting.clearDataSource();
	}
}
