------------------------------
iter
------------------------------
	# iter 提供了与序列迭代相关的基本定义和操作。
	# 迭代器是一个函数，它将序列中的连续元素传递给一个回调函数（通常称为 yield）。该函数在序列结束或 yield 返回 false 时停止，表示提前停止迭代。本软件包定义了 Seq 和 Seq2（发音类似于 seek--sequence 的第一个音节），作为每个序列元素传递 1 或 2 个值给 yield 的迭代器的简称：


------------------------------
var
------------------------------

------------------------------
type
------------------------------
	type Seq[V any] func(yield func(V) bool)
	type Seq2[K, V any] func(yield func(K, V) bool)

------------------------------
func
------------------------------

	func Pull[V any](seq Seq[V]) (next func() (V, bool), stop func())
	func Pull2[K, V any](seq Seq2[K, V]) (next func() (K, V, bool), stop func())