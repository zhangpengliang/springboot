//package com.boot.split;
//
//import java.util.Collection;
//import java.util.Date;
//
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//public class MonthPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
//
//	@Override
//	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
//		System.out.println(availableTargetNames.toString());// 该数据库中所有的表的名称
//		System.out.println(shardingValue.getValue());// 分片指标(列名)的值
//		return shardingValue.getLogicTableName() + "_" + shardingValue.getValue();
//	}
//
//}
