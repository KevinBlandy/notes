
-------------------------------
常规模版检索					|
-------------------------------

public List<?> all(Class<?> clazz);
	* 检索出所有的记录

public List<?> all(Class<?> clazz,int start,int size);
	* 检索出所有的记录,带分页

public int allCount(Class<?> clazz);
	* 获取记录总数

public List<T> template(T t);
	* 根据非空参数检索出符合条件的记录
	* 该检索方式,并不包含时间段,也不包含排序

public List<T> template(T t,int start,int size);
	* 同上,带分页的

public T templateOne(T t) ;
	* 根据模板查询，返回一条结果，如果没有找到，返回null

public long templateCount(T t) 
	* 获取符合条件的个数



-------------------------------
根据SQLID来进行检索				|
-------------------------------
	public List select(String sqlId, Class clazz, Map paras) 
		* 根据sqlid来查询，参数是个map
	
	public List select(String sqlId, Class clazz, Map paras, int start, int size)
		* 同上,增加翻页

	public List select(String sqlId, Class clazz, Object paras) 
		* 根据sqlid来查询，参数是个pojo
	
	public List select(String sqlId, Class clazz, Object paras, int start, int size) 
		* 同上增加翻页

	public List select(String sqlId, Class clazz) 
		* 根据sqlid来查询，无参数
	
	public T selectSingle(String id,Object paras, Class target) 
		* 根据sqlid查询，输入是Pojo，将对应的唯一值映射成指定的taget对象，如果未找到，则返回空。
		* 需要注意的时候，有时候结果集本生是空，这时候建议使用unique
	public T selectSingle(String id,Map paras, Class target) 
		* 根据sqlid查询，输入是Map，将对应的唯一值映射成指定的taget对象，
		* 如果未找到，则返回空。需要注意的时候，有时候结果集本生是空，这时候建议使用unique

	public T selectUnique(String id,Object paras, Class target) 
		* 根据sqlid查询，输入是Pojo，将对应的唯一值映射成指定的taget对象
		* 如果未找到，则抛出异常
	public T selectUnique(String id,Map paras, Class target) 
		* 根据sqlid查询，输入是Map，将对应的唯一值映射成指定的taget对象
		* 如果未找到，则抛出异常

	public Integer intValue(String id,Object paras) 
		* 查询结果映射成Integer，如果找不到，返回null，输入是object
	
	public Integer intValue(String id,Map paras) 
		* 查询结果映射成Integer，如果找不到，返回null，输
		* 入是map，其他还有 'longValue'，'bigDecimalValue'
	
	public T unique(Class clazz,Object pk) 
		* 根据主键查询，如果未找到，抛出异常.

-------------------------------
翻页检索API						|
-------------------------------
	public void pageQuery(String sqlId,Class clazz,PageQuery query)