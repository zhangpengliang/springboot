package com.boot.split.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.boot.split.entity.StudentEntity;
import com.boot.split.mapper.IStudentDao;

import tk.mybatis.mapper.common.Mapper;

@Component
public class StudentService extends AbstractService<StudentEntity> {
	@Resource
	private IStudentDao studentDao;

	protected Mapper<StudentEntity> getMapper() {
		return studentDao;
	}

	protected StudentService() {
		super(StudentEntity.class);
	}

	public void select() {
		List<StudentEntity> entitys = this.selectAll();
		System.out.println(entitys.size());
	}

	public void selectWithOutContext() {
		List<StudentEntity> entitys = this.listALLWithOutContext();
		System.out.println(entitys.size());
	}

}
