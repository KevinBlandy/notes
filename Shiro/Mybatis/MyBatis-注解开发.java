————————————————————————
1,MyBatis注解开发		|
————————————————————————
	
————————————————————————
2,注解开发 - SQL映射	|
————————————————————————
	* 其实这种方式,就是'mapper代理开发'的方法.
	1,自定义'接口',并且添加相应的注解
		package com.kevin.mapper;
		import java.util.List;
		import org.apache.ibatis.annotations.Delete;
		import org.apache.ibatis.annotations.Insert;
		import org.apache.ibatis.annotations.Select;
		import org.apache.ibatis.annotations.Update;
		import com.kevin.vo.User;
		/**
		 * UserMapper,注解开发
		 * */
		public interface UserMapper 
		{
			/**
			 * 保存一个对象
			 * */
			@Insert(value="INSERT INTO user VALUES(#{id},#{name},#{gender})")
			public void save(User user);
			/**
			 * 删除一个对象
			 * */
			@Delete(value="DELET FROM use WHERE id=#{id}")
			public void delete(User user);
			/**
			 * 根据ID查询一个对象
			 * */
			@Select(value="SELECT * FROM user WHERE id=#{id}")
			public User findById(User user);
			/**
			 * 根据ID修改一个用户
			 * */
			@Update(value="UPDATE user as u SET u.name=#{name},u.gender=#{gender} WHERE u.id=#{id}")
			public void updateUser(User user);
			/**
			 * 获取记录总数
			 * */
			@Select(value="SELECT COUNT(*) FROM user")
			public Integer getCount();
			/**
			 * 获取所有对象
			 * */
			@Select(value="SELECT * FROM user")
			public List<User> getAll();
		}

	2,在核心配置文件(SqlMapConfig.xml)中配置该类
		<mapper class="com.kevin.mapper.UserMapper"/>

	3,通过SqlSession的'getMapper(T.class);'方法,传入接口的 Class 类型来获取代理对象
		UserMapper userMapper = session.getMapper(UserMapper.class);

	4,直接执行返回接口引用对象的抽象方法,就能自动执行接口抽象方法上定义的SQL语句
		User u = userMapper.findById(user);