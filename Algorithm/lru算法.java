

# 最近最少使用算法(Least Recently Used 最近最少使用)

# 一般用于缓存场景
	1. 新数据插入到链表头部；
	2. 每当缓存命中(即缓存数据被访问),则将数据移到链表头部
	3. 当链表满的时候,将链表尾部的数据丢弃


# Java 实现
	* LinkedHashMap 本身就提供了数据访问排序以及移除最少访问key的功能

	class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {  
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 4318100161701136149L;
		
		public static final float DEFAULT_LOAD_FACTOR = 0.75f;
		
		// 最大容量,超过该容量后会移除最近最少使用的元素
		private int maxCapacity;
		
		public LRULinkedHashMap(int maxCapacity) {
			// 根据maxCapacity和加载因子计算hashmap的capactiy
			// +1确保当达到cacheSize上限时不会触发hashmap的扩容
			super((int) Math.ceil(maxCapacity / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_LOAD_FACTOR, true);
			this.maxCapacity = maxCapacity;
		}

		// 超过最大容量后,尝试移除最少使用的key的策略

		// 该方法会在每次执行添加操作后回调,如果返回true,则删除链表最后的一个元素
		@Override 
		protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {  
			return super.size() > this.maxCapacity;  
		}  
	}  

# python 实现

import collections
class LRUDict (object):
    def __init__(self,size):
        self.size = size
        self.dict = collections.OrderedDict()
    
    def set(self,key,value):
        if key in self.dict:
            # key存在,直接替换
            self.dict[key] = value
        else:
            if len(self.dict) > self.size:
                # 移除最后一个元素,也就是不常用的
                self.dict.popitem()
            # key 不存在,执行新增
            self.dict[key] = value
        # 新增/修改的元素都移动到顶部
        self.dict.move_to_end(key,False)
    
    def get(self,key):
        if key in self.dict:
            # 命中缓存,移到顶部
            self.dict.move_to_end(key,False)
            return self.dict.get(key)
        else:
            return None
        
    def __str__(self, *args, **kwargs):
        return self.dict.__str__()


