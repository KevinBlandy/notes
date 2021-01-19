
----------------------------
FastThreadLocal<V>			|
----------------------------
	# 构造方法
		FastThreadLocal()
	
	
	# 静态方法
		void destroy()
		void removeAll()
		int size()


	# 实例方法
		V get()
		V get(InternalThreadLocalMap threadLocalMap)

		boolean isSet()
		boolean isSet(InternalThreadLocalMap threadLocalMap)

		void remove()
		void remove(InternalThreadLocalMap threadLocalMap)

		void set(V value)
		void set(InternalThreadLocalMap threadLocalMap, V value)


		
		protected V initialValue()
		protected void onRemoval(@SuppressWarnings("UnusedParameters") V value)