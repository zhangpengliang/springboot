package com.boot.split.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.apache.ibatis.session.RowBounds;

import com.boot.annotation.TableField;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.IDynamicTableName;

public abstract class AbstractService<T> {

	protected abstract Mapper<T> getMapper();

	protected Class<T> clazz;

	protected AbstractService(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 不分表获取所有数据
	 * 
	 * @return
	 */
	public List<T> listALLWithOutContext() {
		TableField tableField = getTableField();
		String tableName = getBaseTableName();
		List<T> list = new ArrayList<>();
		if (tableField == null) {
			// 查默认表表名
			return getMapper().selectAll();
		} else {
			Integer index = tableField.value();
			for (int i = 0; i < index; i++) {
				tableName = tableName + tableField.splicName() + i;
				Example e = new Example(clazz);
				e.setTableName(getTableName());
				list.addAll(getMapper().selectByExample(e));
			}
			return list;
		}

	}

	/**
	 * 获取该实体分表字段上的注解
	 * 
	 * @return
	 */
	private TableField getTableField() {
		Field fields[] = clazz.getDeclaredFields();
		if (fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				TableField tableField = fields[i].getAnnotation(TableField.class);
				if (tableField != null) {
					return tableField;
				}
			}
		}
		return null;
	}

	/**
	 * 获取指定表的所有数据(通过上下文获取账号id分表名)
	 * 
	 * @return
	 */
	public List<T> selectAll() {
		// // 判断账号，获取表名
		Example e = new Example(clazz);
		e.setTableName(getTableName());
		return getMapper().selectByExample(e);
	}

	/**
	 * 获取对应表中的主键id的数据
	 * 
	 * @param id
	 * @return
	 */
	public T selectById(Long id) {
		Example e = new Example(clazz);
		e.setTableName(getTableName());
		e.and().andEqualTo("id", id);
		return getMapper().selectOneByExample(e);
	}

	// TODO 还有一个问题就是:两张表关联查询,以及自定义SQL

	// TODO 分页查询
	public List<T> selectByPage(){
		Example e = new Example(clazz);
		e.setTableName(getTableName());
		e.setOrderByClause("age desc");
		return getMapper().selectByExampleAndRowBounds(e, new RowBounds(0, 10));
	}
	
	

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	public String getTableName() {
		// 通过获取上下文,拿到当前登录人信息
		Integer accountNum = 102;
		String tableName = null;
		if (IDynamicTableName.class.isAssignableFrom(clazz)) {
			Table annotaion = clazz.getAnnotation(Table.class);
			if (annotaion != null) {
				tableName = annotaion.name();
				TableField tableField = this.getTableField();
				Integer value = tableField.value();
				return tableName + tableField.splicName() + Math.abs(accountNum % value);// 拼接获取表名
			}
		}
		return tableName;
	}

	/**
	 * 获取默认表名
	 * 
	 * @return
	 */
	public String getBaseTableName() {
		String tableName = null;
		Table annotaion = clazz.getAnnotation(Table.class);
		if (annotaion != null) {
			return annotaion.name();
		}
		return tableName;
	}

}
