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
import com.boot.mapper.IUserDao;

@RestController
@RequestMapping("/data")
public class DataSourceController {

	@Resource
	private SqlSessionFactory sqlSessionFactory;
	@Resource
	private IUserDao userDao;

	@RequestMapping("/swith.json")
	public void dataSwitch() {
		DataSourceRouting.setMasterDataSource();
		try {
			Connection conenction = sqlSessionFactory.openSession().getConnection();
			DataSourceRouting.setSubDataSource();
			Connection conenction2 = sqlSessionFactory.openSession().getConnection();
			System.out.println(conenction.getMetaData().getUserName());
			System.out.println(conenction.getCatalog()); // 数据库名称
			System.out.println(conenction2.getMetaData().getUserName());
			System.out.println(conenction2.getCatalog()); // 数据库名称
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
				DataSourceRouting.setSubDataSource();
			} else {
				DataSourceRouting.setMasterDataSource();
			}
			userDao.insert(u);
		}
	}

}
