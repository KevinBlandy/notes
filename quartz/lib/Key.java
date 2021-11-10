--------------------
Key
--------------------
	# 描述trigger或者job唯一标识类: public class Key<T>  implements Serializable, Comparable<Key<T>>
	
	# static
		public static final String DEFAULT_GROUP = "DEFAULT";
		public static String createUniqueName(String group)
			* 根据group创建一个唯一的名称
			* 如果group为null，则默认为: 
	
	# this
		public Key(String name, String group)
		public String getName()
		public String getGroup()
		public int compareTo(Key<T> o)