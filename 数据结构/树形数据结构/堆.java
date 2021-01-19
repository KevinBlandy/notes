-------------------------
堆						 |
-------------------------
	# 二叉堆,是一颗完全二叉树
		* 父结点都比子结点要小,我们称为最小堆
		* 父结点都比子结点要大,我们称为最大堆

	# 一个堆可以用数组来表示

	# d 叉堆 d-ary heap
	
	# 索引堆
	
	# 二项堆

	# 裴波那契堆




-------------------------
最大堆					 |
-------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * 最大堆
 * 
 * @author KevinBlandy
 *
 */
public class MaxHeap<E extends Comparable<E>> {

	private List<E> data;

	public MaxHeap(E[] arr) {
		this.heapify(arr);
	}

	public MaxHeap() {
		this.data = new ArrayList<>();
	}

	public int size() {
		return this.data.size();
	}

	public boolean empty() {
		return this.data.isEmpty();
	}

	/**
	 * 返回完全二叉树的数组表示中,一个索引所表示的元素的父亲节点的索引
	 * 
	 * @param index
	 * @return
	 */
	private int parent(int index) {
		if (index == 0) {
			throw new IllegalArgumentException("0 没有父级节点");
		}
		return (index - 1) / 2;
	}

	/**
	 * 返回完全二叉树的数组表示中,一个索引所表示的元素的左孩子节点的索引
	 * 
	 * @param index
	 * @return
	 */
	private int leftChild(int index) {
		return index * 2 + 1;
	}

	/**
	 * 返回完全二叉树的数组表示中,一个索引所表示的元素的右孩子节点的索引
	 * 
	 * @param index
	 * @return
	 */
	private int rightChild(int index) {
		return index * 2 + 2;
	}

	/**
	 * 往堆中添加元素,上浮到合适的位置
	 * 
	 * @param e
	 */
	public void add(E e) {

		// 添加到容器
		this.data.add(e);
		// 维护堆性质
		siftUp(this.size() - 1);
	}

	public E findMax() {
		if (this.empty()) {
			throw new IllegalArgumentException("空的堆");
		}
		return this.data.get(0);
	}

	/**
	 * 获取最大的元素
	 * 
	 * @return
	 */
	public E extractMax() {
		E e = this.findMax();
		this.swap(0, this.size() - 1);
		this.data.remove(this.size() - 1);
		this.siftDown(0);
		return e;
	}

	/**
	 * 取出最大元素后,放入一个新的元素 
	 * 实现1：先extractMax(),再add。两次 O(logn)的操作 
	 * 实现2：可以直接将堆顶元素替换以后Sift
	 * Down。一次O(logn)的操作
	 * 
	 * @param e
	 * @return
	 */
	public E replace(E e) {
		E ret = this.findMax();
		this.data.set(0, e); // 把堆顶元素替换为新的元素
		// siftDown
		this.siftDown(0);
		return ret;
	}

	/**
	 * 把任意数组整理为堆(最大)的形状，并且放进data
	 */
	public void heapify(E[] arr) {
		// data = new ArrayList<>(Arrays.asList(arr));
		this.data = new ArrayList<>();
		for (E e : arr) {
			this.data.add(e);
		}
		for (int i = this.parent(this.data.size() - 1); i >= 0; i--) {
			this.siftDown(i);
		}
	}

	// 下浮
	private void siftDown(int k) {
		while (this.leftChild(k) < this.size()) {
			int j = leftChild(k);
			if ((j + 1) < this.size() && (this.data.get(j + 1).compareTo(this.data.get(j)) > 0)) {
				j = this.rightChild(k);
			}
			if (this.data.get(k).compareTo(this.data.get(j)) >= 0) {
				break;
			}

			this.swap(k, j);
			k = j;
		}
	}

	// 元素上浮
	private void siftUp(int k) {
		while (k > 0 && this.data.get(this.parent(k)).compareTo(this.data.get(k)) < 0) {
			this.swap(k, this.parent(k));
			k = this.parent(k);
		}
	}

	/**
	 * 交换[]两个角标的元素
	 * 
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		if (i < 0 || i >= this.size() || j < 0 || j >= this.size()) {
			throw new IllegalArgumentException("下标不合法");
		}
		E e = this.data.get(i);
		this.data.set(i, this.data.get(j));
		this.data.set(j, e);
	}

	/**
	 * 遍历数组
	 * 
	 * @param action
	 */
	public void forEach(Consumer<? super E> action) {
		this.data.forEach(action);
	}
}

-------------------------
最小堆					 |
-------------------------