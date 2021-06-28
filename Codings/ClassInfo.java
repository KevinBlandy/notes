package io.springboot.demo.test.gen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import com.rabbitmq.client.Channel;



/**
 * 
 * 打印类信息
 *
 */
public class Main {

	public static Class<?> clazz = Channel.class;

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		parse(clazz);
	}

	public static void parse(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		String className = clazz.getSimpleName();
		printLine();
		System.out.println(className);
		printLine();

		System.out.println();
		
		printLine();
		System.out.println("static");
		printLine();

		parseStatic(clazz);
		
		System.out.println();

		printLine();
		System.out.println("this");
		printLine();
		
		parseThis(clazz);
	}

	public static void parseThis(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		
		// 构造器
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			
			if (!(Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers()))) { // public or protected
				continue;
			}
			
			StringBuilder stringBuilder = new StringBuilder("	");
			
			stringBuilder.append(Modifier.toString(constructor.getModifiers())).append(" ");
			stringBuilder.append(clazz.getSimpleName()).append("(");
			// 方法参数
			Parameter[] parameters = constructor.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				stringBuilder.append(parameter.getType().getSimpleName()).append(" ");
				stringBuilder.append(parameter.getName());
				if (i < (parameters.length - 1)) {
					stringBuilder.append(", ");
				}
			}
			stringBuilder.append(")");
			
			System.out.println(stringBuilder.toString());
		}
		
		System.out.println();
		
		// 字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) { // static
				continue;
			}
			if (!Modifier.isPublic(field.getModifiers())) { // public
				continue;
			}

			Class<?> fieldClass = field.getType();

			StringBuilder stringBuilder = new StringBuilder("	");

			stringBuilder.append(Modifier.toString(field.getModifiers())).append(" "); // 修饰
			stringBuilder.append(fieldClass.getSimpleName()).append(" "); // 字段类型简写
			stringBuilder.append(field.getName()); // 字段名称

			// 对象的属性，不能获取属性值
//			if (fieldClass.isPrimitive()) { // 基本数据类型
//				stringBuilder.append(" = ").append(field.get(clazz).toString());
//			} else if (fieldClass.equals(String.class)) { // 字符串数据类型
//				Object value = field.get(clazz);
//				stringBuilder.append(" = ").append("\"" + value == null ? "null" : value.toString() + "\"");
//			} else {
//				Object value = field.get(clazz);
//				stringBuilder.append(" = ").append(value == null ? "null" : "[object]");
//			}
			System.out.println(stringBuilder.toString());
		}

		System.out.println();

		// 方法
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (Modifier.isStatic(method.getModifiers())) { // static
				continue;
			}
			if (!Modifier.isPublic(method.getModifiers())) { // public
				continue;
			}

			Class<?> returnType = method.getReturnType();

			StringBuilder stringBuilder = new StringBuilder("	");
			stringBuilder.append(Modifier.toString(method.getModifiers())).append(" "); // 修饰
			stringBuilder.append(returnType.getSimpleName()).append(" "); // 返回值类型简写
			stringBuilder.append(method.getName()).append("("); // 方法名称

			// 方法参数
			Parameter[] parameters = method.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				stringBuilder.append(parameter.getType().getSimpleName()).append(" ");
				stringBuilder.append(parameter.getName());
				if (i < (parameters.length - 1)) {
					stringBuilder.append(", ");
				}
			}
			stringBuilder.append(")");

			System.out.println(stringBuilder.toString());
		}
	}

	public static void parseStatic(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {

		// 字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) { // static
				continue;
			}
			if (!Modifier.isPublic(field.getModifiers())) { // public
				continue;
			}

			Class<?> fieldClass = field.getType();

			StringBuilder stringBuilder = new StringBuilder("	");

			stringBuilder.append(Modifier.toString(field.getModifiers())).append(" "); // 修饰
			stringBuilder.append(fieldClass.getSimpleName()).append(" "); // 字段类型简写
			stringBuilder.append(field.getName()); // 字段名称

			if (fieldClass.isPrimitive()) { // 基本数据类型
				stringBuilder.append(" = ").append(field.get(clazz).toString());
			} else if (fieldClass.equals(String.class)) { // 字符串数据类型
				Object value = field.get(clazz);
				stringBuilder.append(" = ").append("\"" + value == null ? "null" : value.toString() + "\"");
			} else {
				Object value = field.get(clazz);
				stringBuilder.append(" = ").append(value == null ? "null" : "[object]");
			}
			System.out.println(stringBuilder.toString());
		}

		System.out.println();

		// 方法
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (!Modifier.isStatic(method.getModifiers())) { // static
				continue;
			}
			if (!Modifier.isPublic(method.getModifiers())) { // public
				continue;
			}

			Class<?> returnType = method.getReturnType();

			StringBuilder stringBuilder = new StringBuilder("	");
			stringBuilder.append(Modifier.toString(method.getModifiers())).append(" "); // 修饰
			stringBuilder.append(returnType.getSimpleName()).append(" "); // 返回值类型简写
			stringBuilder.append(method.getName()).append("("); // 方法名称

			// 方法参数
			Parameter[] parameters = method.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				stringBuilder.append(parameter.getType().getSimpleName()).append(" ");
				stringBuilder.append(parameter.getName());
				if (i < (parameters.length - 1)) {
					stringBuilder.append(", ");
				}
			}
			stringBuilder.append(")");

			System.out.println(stringBuilder.toString());
		}
	}

	public static void printLine() {
		System.out.println("---------------------------------------");
	}
}
