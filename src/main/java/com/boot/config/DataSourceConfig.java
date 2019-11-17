package com.boot.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 多数据源切换,只差AOP了
 * 
 * @author zpl
 *
 */
// @Configuration
public class DataSourceConfig {

	@Bean(name = "dataSource01")
	@ConfigurationProperties(prefix = "spring.datasource.data01") // application.properteis中对应属性的前缀
	public DataSource dataSource01() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "dataSource02")
	@ConfigurationProperties(prefix = "spring.datasource.data02") // application.properteis中对应属性的前缀
	public DataSource dataSource02() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "dataSource")
	@Primary // 多数据源的情况下,这个注解很关键,否则报:expected single matching bean but found 3:
				// dataSource01,dataSource02,dataSou
	public DataSource dataSource() {
		DataSourceRouting routing = new DataSourceRouting();
		DataSource dataSource01 = dataSource01();
		// 设置默认数据源
		routing.setDefaultTargetDataSource(dataSource01);

		// 配置多数据源
		Map<Object, Object> dataSources = new ConcurrentHashMap<Object, Object>();
		dataSources.put(DataSourceRouting.DATA_SOURCE_MASTER, dataSource01);
		dataSources.put(DataSourceRouting.DATA_SOURCE_SUB, dataSource02());
		routing.setTargetDataSources(dataSources);
		return routing;
	}

	// @Bean
	// public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws
	// Exception
	// {
	// SqlSessionFactoryBean sqlSessionFactoryBean = new
	// SqlSessionFactoryBean();
	// //设置数据源
	// sqlSessionFactoryBean.setDataSource(dataSource);
	// //指定基础包
	// sqlSessionFactoryBean.setTypeAliasesPackage("com.microservice.dbandcache.model");
	// //指定mapper位置
	// sqlSessionFactoryBean.setMapperLocations(new
	// PathMatchingResourcePatternResolver()
	// .getResources("classpath:mapping/*.xml"));
	//
	// return sqlSessionFactoryBean.getObject();
	// }

}
