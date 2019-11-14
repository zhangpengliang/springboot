package com.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源路由
 * 
 *
 * @date 2018年9月21日 上午11:06:01
 *
 */
public class DataSourceRouting extends AbstractRoutingDataSource {

	public static final String DATA_SOURCE_MASTER = "dataSourceMaster";
	public static final String DATA_SOURCE_SUB = "dataSourceSub";
	private static final ThreadLocal<String> localContext = new ThreadLocal<String>();
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceRouting.class);

	@Override
	protected Object determineCurrentLookupKey() {
		if (null == localContext.get()) {
			return null;
		}
		LOGGER.info("数据源切换至" + localContext.get());
		return localContext.get();
	}

	public static void setMasterDataSource() {
		localContext.set(DATA_SOURCE_MASTER);
	}

	public static void setSubDataSource() {
		localContext.set(DATA_SOURCE_SUB);
	}
}
