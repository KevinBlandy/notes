-----------------
Expiry
-----------------
	# interface Expiry<K, V>

	long expireAfterCreate(K key, V value, long currentTime);
	long expireAfterUpdate(K key, V value, long currentTime, @NonNegative long currentDuration);
	long expireAfterRead(K key, V value, long currentTime, @NonNegative long currentDuration);
