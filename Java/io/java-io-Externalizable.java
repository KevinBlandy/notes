------------------------------
Externalizable				  |
------------------------------
	# 继承了:Serializable 接口
	# 提供了俩抽象方法,让开发者实现手动的序列化/反序列化对象

		void writeExternal(ObjectOutput out) throws IOException;
		void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;