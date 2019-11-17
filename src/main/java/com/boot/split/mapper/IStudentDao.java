package com.boot.split.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.annotation.TargetDataSource;
import com.boot.bean.User;
import com.boot.enums.DataSourceConst.DataSourceType;
import com.boot.split.entity.StudentEntity;
@Mapper
public interface IStudentDao extends tk.mybatis.mapper.common.Mapper<StudentEntity> {

}
