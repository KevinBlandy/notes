----------------------------
1,Java字节码操作			|
----------------------------
	# JAVA动态性的其中一种实现
		1,字节码操作
		2,反射
	
	# 运行时操作字节码,可以实现以下功能
		1,动态生成新的类
		2,动态改变某个类的结构(添加/删除/修改 新的属性/方法)
	
	# 优势
		1,比反射开销小,性能高
		2,Javaasist	性能高于反射,低于ASM
	

	# 常见的字节码操作类库
		BCEL
			* Byte Code Engineering Library(BCEL)
			* 是 Apache Software Foundation的Jakarta 项目的一部分
			* BCEL是 Java Classworking 广泛使用的一种框架.它可以深入 JVM汇编语言,进行类操作的细节
			* BCEL与 Javaassist有不同的处理字节码的方法,BCEL,在实际JVM指令层次,上进行操作(BCEL拥有丰富的JVM指令级支持),而Javassist强调的是源码级别的工作
		
		
		ASM
			* 是一个轻量级JAVA字节码操作框架,直接涉及到JVM底层的操作和指令
		
		CGLIB
			* 是一个强大,高性能.高质量的Code生成类库,基于ASM实现
		
		Javassist
			* 是一个开源的分析,编辑和创建Java字节码的类库,性能比ASM差,跟CGLIB差不多,但是很简单,很多开源框架都在使用
			* Dubbo
				<groupId>org.javassist</groupId>
				<artifactId>javassist</artifactId>
		
		* BCEL和ASM直接操作的是JVM的汇编指令.更难学
	

----------------------------
2,Javassist-API				|
----------------------------
	# Javassist是来自于日本东京大学的东西
	# Javassist的最外层的API和JAVA的反射包中的API颇为相似.
	# 它主要是由:CtClass,CtMethod,CtField 几个组成
	# 用以执行和JDK反射API中的几个类相同操作
		java.lang.Class
		java.lang.reflect.Method
		java.lang.reflect.Field
	
	# 提供了两个层次的API
		Source			//源码级别
		Bytecode		//字节码指令级别
	

----------------------------
3,Javassist-入门			|
----------------------------
	1,通过程序动态的创建一个类
		/**
		 * 创建类池
		 */
		ClassPool pool = ClassPool.getDefault();
		/**
		 * 	makeClass		创建Class
		 *  makePackage		创建Package
		 *  makeInterface	创建Interface
		 */
		CtClass demo = pool.makeClass("com.kevin.demo.Demo");
		/**
		 * 创建属性
		 */
		CtField nameFiled = CtField.make("private String name;", demo);
		CtField numFiled = CtField.make("private String num;", demo);
		/**
		 * 添加到Class
		 */
		demo.addField(nameFiled);
		demo.addField(numFiled);
		/**
		 * 创建方法
		 */
		CtMethod getName = CtMethod.make("public String getName(){return this.name;}", demo);
		CtMethod setName = CtMethod.make("public void setName(String name){this.name = name;}", demo);
		/**
		 * 添加到Class
		 */
		demo.addMethod(getName);
		demo.addMethod(setName);
		/**
		 * 创建构造器
		 * 指定构造器参数类型
		 * 如果是八大基本数据类型,需要通过 CtClass.xxxType;获取
		 * 如果是String或者其他的数据类型,需要通过:pool.get("com.x.x.x.x"); 来获取
		 * 如果是无参构造器,直接不传递参数就好
		 */
		CtConstructor constructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String"),pool.get("java.lang.String")}, demo);
		/**
		 * 设置构造器方法体
		 */
		constructor.setBody("{this.name = name;this.num = num;}");
		/**
		 * 添加构造器到Class
		 */
		demo.addConstructor(constructor);
		/**
		 * 把这个动态生成的类型,序列化到硬盘
		 */
		demo.writeFile("E:\\CLASSES");

	2,通过Xjad反编译该类
		package com.kevin.demo;
		public class Demo
		{

			private String name;
			private String num;

			public String getName()
			{
				return name;
			}

			public void setName(String s)
			{
				name = s;
			}

			public Demo(String s, String s1)
			{
				name = name;
				num = num;
			}
		}


----------------------------
4,Javassist-API详解			|
----------------------------
	
	