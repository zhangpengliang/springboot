package com.boot.mapper.first;

import org.apache.ibatis.annotations.Mapper;

import com.boot.annotation.TargetDataSource;
import com.boot.bean.User;
import com.boot.enums.DataSourceConst.DataSourceType;
@Mapper
//@TargetDataSource(DataSourceType.dataSourceMaster)
public interface IUserDao extends tk.mybatis.mapper.common.Mapper<User> {

}
