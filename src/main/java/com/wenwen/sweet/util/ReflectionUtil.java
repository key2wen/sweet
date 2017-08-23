package com.wenwen.sweet.util;

import java.lang.reflect.Field;

/**
 * @author yunxiang.zyx
 */
public class ReflectionUtil {

	/**
	 * 获取一个类所有的字段，包括父类的
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		// 本类所有的字段
		Field[] fields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && !superClass.equals(Object.class)) {
			Field[] superFields = getAllDeclaredFields(superClass);
			Field[] allFields = new Field[fields.length + superFields.length];
			System.arraycopy(fields, 0, allFields, 0, fields.length);
			System.arraycopy(superFields, 0, allFields, fields.length, superFields.length);
			return allFields;
		}
		return fields;
	}
}
