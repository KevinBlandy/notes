package main

import (
	"fmt"
	"sync"
)

type concurrentMap[K comparable, V any] struct {
	*sync.Map
}

func (c *concurrentMap[K, V]) Load(key K) (V, bool) {
	ret, ok := c.Map.Load(key)
	if ok {
		return ret.(V), ok
	}
	var val V
	return val, ok
}

func (c *concurrentMap[K, V]) Store(key K, val V) {
	c.Map.Store(key, val)
}

func (c *concurrentMap[K, V]) LoadOrStore(key K, val V) (V, bool) {
	actual, loaded := c.Map.LoadOrStore(key, val)
	if loaded {
		return actual.(V), loaded
	}
	var actualVal V
	return actualVal, loaded
}

func (c *concurrentMap[K, V]) LoadAndDelete(key K) (V, bool) {
	ret, ok := c.Map.LoadAndDelete(key)
	if ok {
		return ret.(V), ok
	}
	var val V
	return val, ok
}

func (c *concurrentMap[K, V]) Delete(key K) {
	c.Map.Delete(key)
}

func (c *concurrentMap[K, V]) Swap(key K, val V) (V, bool) {
	previous, loaded := c.Map.Swap(key, val)
	if loaded {
		return previous.(V), loaded
	}
	var actualVal V
	return actualVal, loaded
}

func (c *concurrentMap[K, V]) CompareAndSwap(key K, old, new V) bool {
	return c.Map.CompareAndSwap(key, old, new)
}

func (c *concurrentMap[K, V]) CompareAndDelete(key K, old V) bool {
	return c.Map.CompareAndDelete(key, old)
}

func (c *concurrentMap[K, V]) Range(f func(key K, value V) bool) {
	c.Map.Range(func(key, value any) bool {
		return f(key.(K), value.(V))
	})
}

func NewConcurrentMap[K comparable, V any]() *concurrentMap[K, V] {
	return &concurrentMap[K, V]{&sync.Map{}}
}

func main() {
	// 创建
	dict := NewConcurrentMap[string, any]()

	fmt.Println(dict.LoadOrStore("k1", "v1"))
	fmt.Println(dict.LoadOrStore("k1", "v2"))

	dict.Range(func(key string, value any) bool {
		fmt.Println(key, value)
		return true
	})

}
