--------------------
SequencedMap
--------------------
	# 序列map
		public interface SequencedMap<K, V> extends Map<K, V> 
	

--------------------
SequencedMap
--------------------
	SequencedMap<K, V> reversed();
	Map.Entry<K,V> firstEntry();
	Map.Entry<K,V> lastEntry();
	Map.Entry<K,V> pollFirstEntry();
	Map.Entry<K,V> pollLastEntry()
	V putFirst(K k, V v) 
	V putLast(K k, V v) 
	SequencedSet<K> sequencedKeySet()
	SequencedCollection<V> sequencedValues()
	SequencedSet<Map.Entry<K, V>> sequencedEntrySet()
