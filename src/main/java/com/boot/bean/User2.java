package com.boot.bean;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Table(name = "t_user_00")
public class User2 {

	@Id
	@KeySql(useGeneratedKeys = true)
	private Integer id;
	/** 用户id */
	private Integer userId;
	/** 用户名称 */
	private String name;
	/** 年龄 */
	private Integer age;

}
