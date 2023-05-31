---------------------------
Record
---------------------------
	# 核心的接口，表示 “一行数据”
		public interface Record extends Fields, Attachable, Comparable<Record>, Formattable
	
		* 定义的用于储存数据库结果记录的一个接口，其主要是将一个表字段的列表和值的列表使用相同的顺序储存在一起
		* 建议生成Java对象得时候，同时生成表列的接口，那么 Record 中会新增2个复写的方法 into/from ，使用的是接口，而不是反射，性能会提高


	Row valuesRow();
		* 获取值的那一行

	<T> T get(Field<T> field) throws IllegalArgumentException;
	<U> U get(Field<?> field, Class<? extends U> type) throws IllegalArgumentException, DataTypeException;
	<T, U> U get(Field<T> field, Converter<? super T, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object get(String fieldName) throws IllegalArgumentException;
	<U> U get(String fieldName, Class<? extends U> type) throws IllegalArgumentException, DataTypeException;
	<U> U get(String fieldName, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object get(Name fieldName) throws IllegalArgumentException;
	<U> U get(Name fieldName, Class<? extends U> type) throws IllegalArgumentException, DataTypeException;
	<U> U get(Name fieldName, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object get(int index) throws IllegalArgumentException;
	<U> U get(int index, Class<? extends U> type) throws IllegalArgumentException, DataTypeException;
	<U> U get(int index, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	<T> void set(Field<T> field, T value);
	<T, U> void set(Field<T> field, U value, Converter<? extends T, ? super U> converter);
	<T> Record with(Field<T> field, T value);
	<T, U> Record with(Field<T> field, U value, Converter<? extends T, ? super U> converter);
	int size();
	Record original();
	<T> T original(Field<T> field);
	Object original(int fieldIndex);
	Object original(String fieldName);
	Object original(Name fieldName);

	boolean changed();
	boolean changed(Field<?> field);
	boolean changed(int fieldIndex);
	boolean changed(String fieldName);
	boolean changed(Name fieldName);
		* 返回指定字段，是否被修改过

	void changed(boolean changed);
	void changed(Field<?> field, boolean changed);
	void changed(int fieldIndex, boolean changed);
	void changed(String fieldName, boolean changed);
	void changed(Name fieldName, boolean changed);
		* 此方法用于修改字段更新标识，一般 update/insert 方法配合使用，可以通过设置true/false控制指定字段是否 更新/储存

	void reset();
	void reset(Field<?> field);
	void reset(int fieldIndex);
	void reset(String fieldName);
	void reset(Name fieldName);
		* 此方法用于重置字段更新标识，效果和 changed(Field field, false) 相同

	Object[] intoArray();
	List<Object> intoList();
	Stream<Object> intoStream();
	Map<String, Object> intoMap();
		* 转换为数组/list/stream/map

	Record into(Field<?>... fields);
		* 转换为新的Record，指定字段
		

	<T1> Record1<T1> into(Field<T1> field1);
	...
	<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22);
		* 选择指定的字段，转换为固定长度的 Record (系统根据表生成的那些就是了)
		* into指定了哪些字段，返回的结果就是相同长度的  Record

	<E> E into(Class<? extends E> type) throws MappingException;
		* 转换为指定的类型对象

	<E> E into(E object) throws MappingException;	
		* 填充到指定的对象

	<R extends Record> R into(Table<R> table);
		* 转换为指定的 Table

	ResultSet intoResultSet()
		* 转换为 ResultSet

	<E> E map(RecordMapper<Record, E> mapper);
		* 通过 Map 处理成其他的数据

	void from(Object source) throws MappingException;
	void from(Object source, Field<?>... fields) throws MappingException;
	void from(Object source, String... fieldNames) throws MappingException;
	void from(Object source, Name... fieldNames) throws MappingException;
	void from(Object source, int... fieldIndexes) throws MappingException;
		* 解析指定的对象，填充到 record

	void fromMap(Map<String, ?> map);
	void fromMap(Map<String, ?> map, Field<?>... fields);
	void fromMap(Map<String, ?> map, String... fieldNames);
	void fromMap(Map<String, ?> map, Name... fieldNames);
	void fromMap(Map<String, ?> map, int... fieldIndexes);
		* 解析指定的Map，填充到 record

	void fromArray(Object... array);
	void fromArray(Object[] array, Field<?>... fields);
	void fromArray(Object[] array, String... fieldNames);
	void fromArray(Object[] array, Name... fieldNames);
	void fromArray(Object[] array, int... fieldIndexes);
		* 解析指定的数组，填充到 record

	int hashCode();
	boolean equals(Object other);
	int compareTo(Record record);

	<T> T getValue(Field<T> field) throws IllegalArgumentException;
	<T> T getValue(Field<?> field, Class<? extends T> type) throws IllegalArgumentException, DataTypeException;
	<T, U> U getValue(Field<T> field, Converter<? super T, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object getValue(String fieldName) throws IllegalArgumentException;
	<T> T getValue(String fieldName, Class<? extends T> type) throws IllegalArgumentException, DataTypeException;
	<U> U getValue(String fieldName, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object getValue(Name fieldName) throws IllegalArgumentException;
	<T> T getValue(Name fieldName, Class<? extends T> type) throws IllegalArgumentException, DataTypeException;
	<U> U getValue(Name fieldName, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	Object getValue(int index) throws IllegalArgumentException;
	<T> T getValue(int index, Class<? extends T> type) throws IllegalArgumentException, DataTypeException;
	<U> U getValue(int index, Converter<?, ? extends U> converter) throws IllegalArgumentException, DataTypeException;
	<T> void setValue(Field<T> field, T value);
	<T, U> void setValue(Field<T> field, U value, Converter<? extends T, ? super U> converter);




