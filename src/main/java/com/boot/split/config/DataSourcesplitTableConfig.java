package com.boot.split.config;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 分表不分库-01库配置
 * 
 * @author zpl
 *
 */
@Configuration
@MapperScan(basePackages = "com.boot.split.mapper", sqlSessionFactoryRef = "sqlSessionFactorySplitTable")
public class DataSourcesplitTableConfig {

	@Bean(name = "dataSourceSplitTable")
	@ConfigurationProperties(prefix = "spring.datasource.data01") // application.properteis中对应属性的前缀
	public DataSource dataSource01() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "sqlSessionFactorySplitTable")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource01") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 设置数据源
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 指定基础包
		sqlSessionFactoryBean.setTypeAliasesPackage("com.boot.split.entity");
		// 指定mapper位置
		sqlSessionFactoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

}
