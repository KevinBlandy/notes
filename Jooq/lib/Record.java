---------------------------
Record
---------------------------
	# ���ĵĽӿڣ���ʾ ��һ�����ݡ�
		public interface Record extends Fields, Attachable, Comparable<Record>, Formattable
	
		* ��������ڴ������ݿ�����¼��һ���ӿڣ�����Ҫ�ǽ�һ�����ֶε��б��ֵ���б�ʹ����ͬ��˳�򴢴���һ��
		* ��������Java�����ʱ��ͬʱ���ɱ��еĽӿڣ���ô Record �л�����2����д�ķ��� into/from ��ʹ�õ��ǽӿڣ������Ƿ��䣬���ܻ����


	Row valuesRow();
		* ��ȡֵ����һ��

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
		* ����ָ���ֶΣ��Ƿ��޸Ĺ�

	void changed(boolean changed);
	void changed(Field<?> field, boolean changed);
	void changed(int fieldIndex, boolean changed);
	void changed(String fieldName, boolean changed);
	void changed(Name fieldName, boolean changed);
		* �˷��������޸��ֶθ��±�ʶ��һ�� update/insert �������ʹ�ã�����ͨ������true/false����ָ���ֶ��Ƿ� ����/����

	void reset();
	void reset(Field<?> field);
	void reset(int fieldIndex);
	void reset(String fieldName);
	void reset(Name fieldName);
		* �˷������������ֶθ��±�ʶ��Ч���� changed(Field field, false) ��ͬ

	Object[] intoArray();
	List<Object> intoList();
	Stream<Object> intoStream();
	Map<String, Object> intoMap();
		* ת��Ϊ����/list/stream/map

	Record into(Field<?>... fields);
		* ת��Ϊ�µ�Record��ָ���ֶ�
		

	<T1> Record1<T1> into(Field<T1> field1);
	...
	<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22);
		* ѡ��ָ�����ֶΣ�ת��Ϊ�̶����ȵ� Record (ϵͳ���ݱ����ɵ���Щ������)
		* intoָ������Щ�ֶΣ����صĽ��������ͬ���ȵ�  Record

	<E> E into(Class<? extends E> type) throws MappingException;
		* ת��Ϊָ�������Ͷ���

	<E> E into(E object) throws MappingException;	
		* ��䵽ָ���Ķ���

	<R extends Record> R into(Table<R> table);
		* ת��Ϊָ���� Table

	ResultSet intoResultSet()
		* ת��Ϊ ResultSet

	<E> E map(RecordMapper<Record, E> mapper);
		* ͨ�� Map ���������������

	void from(Object source) throws MappingException;
	void from(Object source, Field<?>... fields) throws MappingException;
	void from(Object source, String... fieldNames) throws MappingException;
	void from(Object source, Name... fieldNames) throws MappingException;
	void from(Object source, int... fieldIndexes) throws MappingException;
		* ����ָ���Ķ�����䵽 record

	void fromMap(Map<String, ?> map);
	void fromMap(Map<String, ?> map, Field<?>... fields);
	void fromMap(Map<String, ?> map, String... fieldNames);
	void fromMap(Map<String, ?> map, Name... fieldNames);
	void fromMap(Map<String, ?> map, int... fieldIndexes);
		* ����ָ����Map����䵽 record

	void fromArray(Object... array);
	void fromArray(Object[] array, Field<?>... fields);
	void fromArray(Object[] array, String... fieldNames);
	void fromArray(Object[] array, Name... fieldNames);
	void fromArray(Object[] array, int... fieldIndexes);
		* ����ָ�������飬��䵽 record

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




