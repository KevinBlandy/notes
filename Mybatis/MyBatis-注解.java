----------------------------
MyBatis-注解				|
----------------------------
	@Param
		* 这个注解,是标识在Mapper接口的形参上
		* 其实就是说,Mapper接口的形参,可以是多个简单的数据类型,通过注解来声明名称,在mapper.xml中就使用注解标识的名称来代表参数名称
		* 通过这个注解,来指定入参的参数名
		['Demo']
			public User queryByNameAndPass(
										@Param(value="userName")String userName,
										@Param(value="password")String password);

			<select id="queryByNameAndPass" resultType="User">
				SELECT * FROM tb_user WHERE user_name = #{userName} AND password = #{password}
			</select>
	
	@Insert
	@Update
	@Delete
	@Selct
		# 以上注解,一眼就看明白
	
	@Result
	@Results

----------------------------
MyBatis-demo				|
----------------------------
@Repository
public interface FooMapper extends BaseMapper<FooEntity>{
	
	class FooSqlProvider extends BaseSqlProvider<FooEntity> {
		
	}
	
    //===================================
	//获取自增主键
	@Options(useGeneratedKeys = true,keyProperty = "fooId",keyColumn = "foo_id")					
	@InsertProvider(type = FooSqlProvider.class,method = "create")
	//也可以获取自增主键
//	@SelectKey(before = false,keyColumn = "foo_id",keyProperty = "fooId",resultType = Integer.class,statement = "SELECT LAST_INSERT_ID();")
	int create(FooEntity foo);


    //===================================
	@SelectProvider(type = FooSqlProvider.class,method = "queryByPrimaryKey")
	@ResultMap(value = "BASE_RESULT_MAP")
	FooEntity queryByPrimaryKey(Serializable primaryKey);

    @SelectProvider(type = FooSqlProvider.class,method = "queryByParamSelective")
    @ResultMap(value = "BASE_RESULT_MAP")
	FooEntity queryByParamSelectiveUnique(FooEntity fooEntity);
	
	@SelectProvider(type = FooSqlProvider.class,method = "queryByParamSelective")
	@Results(id = "BASE_RESULT_MAP",value = {
		@Result(id = true,column = "foo_id",property = "fooId"),
		@Result(column = "create_date",property = "createDate"),
		@Result(column = "modify_date",property = "modifyDate"),
		@Result(column = "create_user",property = "createUser"),
		@Result(column = "status",property = "status"),
		@Result(column = "sorted",property = "sorted"),
		@Result(column = "remark",property = "remark"),
	})
	List<FooEntity> queryByParamSelective(FooEntity foo);

    //===================================
	@UpdateProvider(type = FooSqlProvider.class,method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FooEntity foo);

    @UpdateProvider(type = FooSqlProvider.class,method = "updateByPrimaryKey")
	int updateByPrimaryKey(FooEntity foo);

    //===================================
	@DeleteProvider(type = FooSqlProvider.class,method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(Serializable deleteByPrimaryKey);
	
	@DeleteProvider(type = FooSqlProvider.class,method = "deleteByParamSelective")
	@ResultMap(value = "BASE_RESULT_MAP")
	int deleteByParamSelective(FooEntity fooEntity);
	
	@SelectProvider(type = FooSqlProvider.class,method = "queryByParamSelective")
	@ResultMap(value = "BASE_RESULT_MAP")
	PageList<FooEntity> queryByPage(FooEntity fooEntity,PageBounds pageBounds);
}




# Provider的接口支持一个参数
	ProviderContext
		private final Class<?> mapperType; // mapper接口
		private final Method mapperMethod; // mapper方法

