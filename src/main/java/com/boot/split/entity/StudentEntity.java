package com.boot.split.entity;

import java.lang.reflect.Field;

import javax.persistence.Table;

import com.boot.annotation.TableField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mybatis.mapper.entity.IDynamicTableName;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_student")
public class StudentEntity extends AbstractEntity implements IDynamicTableName {

	private static final long serialVersionUID = 1L;

	@TableField(value = 2, splicName = "_0")
	private Integer studentId;

	private String name;

	private Integer age;

	@Override
	public String getDynamicTableName() {

		String tableName = this.getClass().getAnnotation(Table.class).name();
		try {
			java.lang.reflect.Field fields[] = this.getClass().getDeclaredFields();
			Integer count = fields.length;
			for (int i = 0; i < count; i++) {

				Field field = fields[i];
				if (field.isAnnotationPresent(TableField.class)) {
					field.setAccessible(true);
					Integer obj1 = (Integer) field.get(this);
					TableField annotaion = field.getAnnotation(TableField.class);
					Integer value = annotaion.value();
					int modle = obj1.intValue() % value;
					return tableName + annotaion.splicName() + modle;
				}

			}

			return tableName;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableName;
	}

}
