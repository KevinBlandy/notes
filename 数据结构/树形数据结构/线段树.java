--------------------
线段树				|
--------------------
	# 也叫做区间树(Segment Tree)

	# 线段树每一个节点,都是表示了一段区间

	# 线段树不一定是一颗满的二叉树,也不一定是一颗完全二叉树,线段树是平衡二叉树



--------------------
实现				|
--------------------

public class SegmentTree<E> {

	public static interface Merger<E> {
		E merge(E a, E b);
	}

	private E[] data;

	private E[] tree;

	private Merger<E> merger;

	@SuppressWarnings("unchecked")
	public SegmentTree(E[] arr, Merger<E> merger) {
		this.data = arr;
		this.tree = (E[]) new Object[4 * arr.length];
		this.merger = merger;
		this.bildeSegmentTree(0, 0, this.data.length - 1);
	}

	public E get(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IllegalArgumentException("非法索引");
		}
		return this.data[index];
	}

	/**
	 * 在treeIndex的位置创建表示区间的线段树 [l....r]
	 * 
	 * @param treeIndex
	 *            在完全二叉树数组表示中那个的下标
	 * @param l
	 *            左边开始的索引
	 * @param r
	 *            右边开始的索引
	 */
	private void bildeSegmentTree(int treeIndex, int l, int r) {
		if (l == r) {
			this.tree[treeIndex] = this.data[l];
			return;
		}

		int lefTreeIndex = this.leftChild(treeIndex);
		int rightTreeIndex = this.rightChild(treeIndex);

		int mid = l + (r - l) / 2;

		this.bildeSegmentTree(lefTreeIndex, l, mid);
		this.bildeSegmentTree(rightTreeIndex, mid + 1, r);

		this.tree[treeIndex] = this.merger.merge(this.tree[lefTreeIndex], this.tree[rightTreeIndex]);
	}

	private int size() {
		return this.data.length;
	}

	// 返回完全二叉树的数组表示中,一个索引所表示的元素的左孩子节点的索引
	private int leftChild(int index) {
		return 2 * index + 1;
	}

	// 返回完全二叉树的数组表示中,一个索引所表示的元素的右孩子节点的索引
	private int rightChild(int index) {
		return 2 * index + 2;
	}

	/**
	 * 返回区间[queryL,queryR]的值
	 * 
	 * @param queryL
	 * @param queryR
	 * @return
	 */
	public E query(int queryL, int queryR) {
		if (queryL < 0 || queryL >= this.data.length || queryR < 0 || queryR >= this.data.length || queryL > queryR) {
			throw new IllegalArgumentException("非法索引");
		}
		return this.query(0, 0, this.data.length - 1, queryL, queryR);
	}

	/**
	 * [l..r]范围内搜索区间[queryL..queryR]的值
	 * 
	 * @param treeIndex
	 * @param l
	 * @param r
	 * @param queryL
	 * @param queryR
	 * @return
	 */
	private E query(int treeIndex, int l, int r, int queryL, int queryR) {
		if (l == queryL && r == queryR) {
			return this.tree[treeIndex];
		}
		int mid = l + (r - l) / 2;

		int leftTreeIndex = this.leftChild(treeIndex);
		int rightTreeIndex = this.rightChild(treeIndex);

		if (queryL >= mid + 1) {
			return this.query(rightTreeIndex, mid + 1, r, queryL, queryR);
		} else if (queryR <= mid) {
			return this.query(leftTreeIndex, l, mid, queryL, queryR);
		}

		E leftResult = this.query(leftTreeIndex, l, mid, queryL, mid);
		E rightResult = this.query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
		return this.merger.merge(leftResult, rightResult);
	}

	// 在treeIndex为根的线段树中更新index的值为e
	private void set(int treeIndex, int l, int r, int index, E e) {
		if (l == r) {
			this.tree[treeIndex] = e;
			return;
		}

		int mid = l + (r - l) / 2;
		int leftTreeIndex = this.leftChild(treeIndex);
		int rightTreeIndex = this.rightChild(treeIndex);

		if (index >= mid + 1) {
			this.set(rightTreeIndex, mid + 1, r, index, e);
		} else {
			this.set(leftTreeIndex, l, mid, index, e);
		}
		
		this.tree[treeIndex] = this.merger.merge(this.tree[leftTreeIndex], this.tree[rightTreeIndex]);
	}

	/**
	 * 更新
	 * 
	 * @param index
	 * @param E
	 */
	public void set(int index, E e) {
		if (index < 0 || index >= this.data.length) {
			throw new IllegalArgumentException("非法索引");
		}
		this.data[index] = e;
		this.set(0, 0, this.data.length - 1, index, e);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int x = 0; x < this.tree.length; x++) {
			stringBuilder.append(this.tree[x]);
			if (x != (this.tree.length - 1)) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
