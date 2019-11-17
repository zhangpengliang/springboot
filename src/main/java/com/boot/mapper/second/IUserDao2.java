package com.boot.mapper.second;

import org.apache.ibatis.annotations.Mapper;

import com.boot.annotation.TargetDataSource;
import com.boot.bean.User;
import com.boot.enums.DataSourceConst.DataSourceType;
@Mapper
//@TargetDataSource(DataSourceType.dataSourceSub)
public interface IUserDao2 extends tk.mybatis.mapper.common.Mapper<User> {

}
