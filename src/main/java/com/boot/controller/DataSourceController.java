package com.boot.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.bean.User;
import com.boot.config.DataSourceRouting;
import com.boot.mapper.first.IUserDao;
import com.boot.mapper.second.IUserDao2;
import com.boot.split.entity.StudentEntity;
import com.boot.split.mapper.IStudentDao;
import com.boot.split.service.StudentService;

import tk.mybatis.mapper.entity.Condition;

@RestController
@RequestMapping("/data")
public class DataSourceController {

	// @Resource
	// private SqlSessionFactory sqlSessionFactory;
	@Resource
	private IUserDao userDao;
	@Resource
	private IUserDao2 userDao2;
	@Resource
	private IStudentDao studentDao;
	@Resource
	private StudentService studentService;

	// @RequestMapping("/swith.json")
	// public void dataSwitch() {
	// DataSourceRouting.setMasterDataSource();
	// try {
	// Connection conenction = sqlSessionFactory.openSession().getConnection();
	// DataSourceRouting.setSubDataSource();
	// Connection conenction2 = sqlSessionFactory.openSession().getConnection();
	// System.out.println(conenction.getMetaData().getUserName());
	// System.out.println(conenction.getCatalog()); // 数据库名称
	// System.out.println(conenction2.getMetaData().getUserName());
	// System.out.println(conenction2.getCatalog()); // 数据库名称
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	@RequestMapping("/insert.json")
	public void insert() {
		List<User> list = new ArrayList<User>();
		list.add(new User(null, 1, "张三1", 1));
		list.add(new User(null, 2, "张三2", 2));
		list.add(new User(null, 3, "张三3", 3));
		list.add(new User(null, 4, "张三4", 4));
		list.add(new User(null, 5, "张三5", 5));
		list.add(new User(null, 6, "张三6", 6));
		for (User u : list) {
			Integer userId = u.getUserId();
			if (userId % 2 == 0) {
				userDao.insert(u);
			} else {
				userDao2.insert(u);
			}

		}
	}

	@RequestMapping("/split.json")
	public void insertSplitTable() {
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		list.add(new StudentEntity(1, "张三", 1));
		list.add(new StudentEntity(2, "张三2", 2));
		list.add(new StudentEntity(3, "张三3", 3));
		list.add(new StudentEntity(4, "张三4", 4));
		list.add(new StudentEntity(5, "张三5", 5));
		list.add(new StudentEntity(6, "张三6", 6));
		for (StudentEntity u : list) {
			studentDao.insert(u);
		}
	}

	@RequestMapping("/selectall.json")
	public void selectall() {
		// List<StudentEntity> entitys = studentDao.selectCount(record)
		// System.out.println(entitys.size());
		studentService.select();

		studentService.selectById(8L);

		studentService.selectWithOutContext();

		System.out.println(studentService.selectByPage().size());
	}

}
