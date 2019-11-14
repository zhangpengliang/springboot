package com.boot.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

//@Configuration
public class ShardingJDBCDataSourceConfig {

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.shardingsphere.datasource.TEST") // application.properteis中对应属性的前缀
	public DataSource dataSource() {
		return DruidDataSourceBuilder.create().build();
	}

}
