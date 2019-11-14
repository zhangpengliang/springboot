package com.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.bean.User;
@Mapper
public interface IUserDao extends tk.mybatis.mapper.common.Mapper<User> {

}
