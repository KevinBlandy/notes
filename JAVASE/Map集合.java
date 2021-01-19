													Map
		该集合存储键值对，一对一往里存。而且要保证键的唯一性。
1，添加元素
	al.put(k key,v value);//添加元素。成功添加则返回null。如果值已经存在。就直接覆盖原值。而且把被覆盖的值返回。
	al.putAll(mak<? extends k? extends v> m);//
2，删除元素
	al.clear();//删除所有元素
	al.remove(Object key);//删除指定key的元素
3，判断元素
	al.containsValue(Object value);//判断集合中是否有value这个值。返回boolean类型
	al.containsKey(key);//判断集合中是否有key这个键所对应的值。boolean返回类型。
	al.isEmpty();//判断是否是空集合。
	al.remove(key);//删除指定键位所对应的值。如果存在，就返回该值。如果不存在返回null。
4，获取元素
	al.get(key);//根据指定值，获取对应元素。如果没有则返回null。
	al.values();//获取所有元素返回Collection<E>!
	al.keySet();//
Map 集合的两种取出方式。
KeySet();
	|--将map中所有的键都存入了Set集合！因为Set集合具备迭代器。所以可以通过迭代方式取出所有的键。再根据get方法。获取每一个键所对应的值！
entrySet();
	|--这个东西呢。返回的是一种称之为"关系"的类型。而且存储在Set集合中！这个"关系"。就是Map.Entry.这是一个内部接口。
拓展知识
	Map 集合被使用能够是因为具备映射关系。可以嵌套使用。一个 Map 集合里面包含了其他的集合来形成一种关系表达！类似于数据库！
------------------------------------------------------------------------------------------------------
1,Hashtable
	底层是哈希表数据结构，不可以存入 null 键 null 值。该集合是线程同步。	JDK1.0效率低
2,HashMap
	低层是哈希表数据结构，允许使用 null 键 null 值。该集合是不同步的。	JDK1.2效率高
	可以通过get方法的返回值，来判断元素是否存在。通过返回 null 判断！ 
	如果添加数据的时候。有相同的键。那么后添加的值会覆盖原来的值钱。并put方法会返回被覆盖的值！
hs.keySet();//返回的是一个Set集合。里面包含了hs集合的所有key！
map.Entry  //其实Entry也是一个接口。它是map借口中的一个内部接口。
3,TreeMap
	低层是二叉树数据结构。可以给用于给 Map 集合中的键进行排序。线程不同步。	
	跟 TreeSet 一样。也有对象具有可比性。
	对象类继承 Comparable Comparator-覆写 compare方法。
和 Set 很像。 Set 底层就是用了 Map 集合。
