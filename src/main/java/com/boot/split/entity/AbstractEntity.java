package com.boot.split.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import tk.mybatis.mapper.annotation.KeySql;

@MappedSuperclass
public class AbstractEntity implements IBaseEntity {

	private static final long serialVersionUID = -7090262049055444012L;

	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
