--------------------
映射				|
--------------------
	# 其实就是 Map
	# 可以使用链表或者二分搜索树实现
		class Node<K,V>{
			K key;
			V value;
			Node next;
		}
		class Node<K,V>{
			K key;
			V value;
			Node left;
			Node right;
		}
	
	# 基本的方法
		add(k,v);
		v remove(k);
		boolean contains(k);
		v get(k);
		void set(k,v);
		int size();
		boolean isEmpty();

	
--------------------
基于链表实现的Map	|
--------------------
import java.util.function.BiConsumer;

public class LinkedMap<K, V> {
	private class Node {
		private K key;
		private V value;
		private Node next;

		public Node(K key, V value, LinkedMap<K, V>.Node next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public Node() {
			this(null, null, null);
		}

		@Override
		public String toString() {
			return "Node [key=" + key + ", value=" + value + ", next=" + next + "]";
		}
	}

	private int size;

	private Node dummyHead;

	public LinkedMap() {
		this.dummyHead = new Node();
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean empty() {
		return this.size == 0;
	}

	private Node getNode(K key) {
		Node node = this.dummyHead.next;
		while (node != null) {
			if (node.key.equals(key)) {
				return node;
			}
			node = node.next;
		}
		return null;
	}

	public boolean contains(K key) {
		return this.getNode(key) != null;
	}

	public V get(K key) {
		Node node = this.getNode(key);
		return node == null ? null : node.value;
	}

	public void add(K key, V value) {
		Node node = this.getNode(key);
		if (node != null) {
			// 覆盖
			node.value = value;
		} else {
			// 直接插入到头节点
			this.dummyHead.next = new Node(key, value, this.dummyHead.next);
			this.size++;
		}
	}

	public void set(K key, V value) {
		Node node = this.getNode(key);
		if (node != null) {
			node.value = value;
		}
		throw new IllegalArgumentException("key \"" + key + "\"not found");
	}

	public V remove(K key) {
		Node pre = this.dummyHead;
		while (pre.next != null) {
			if (pre.next.key.equals(key)) {// 获取到要删除节点的上一个节点
				break;
			}
			pre = pre.next;
		}
		if (pre.next != null) {
			Node delNode = pre.next;
			pre.next = delNode.next;
			this.size--;
			return delNode.value;
		}
		// 遍历到最后也没找到要删除的节点
		throw new IllegalArgumentException("key \"" + key + "\"not found");
	}

	public void forEach(BiConsumer<K, V> consumer) {
		Node node = this.dummyHead;
		while (node.next != null) {
			consumer.accept(node.next.key, node.next.value);
			node = node.next;
		}
	}
}


------------------------
基于二分搜索树实现的Map	|
------------------------


import java.util.function.BiConsumer;

public class BinarySearchTreeMap<K extends Comparable<K>, V> {
	private class Node {
		K key;
		V value;
		private Node left;
		private Node right;

		public Node(K key, V value, Node left, Node right) {
			super();
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}

		public Node(K key, V value) {
			this(key, value, null, null);
		}
	}

	private Node root;

	private int size;

	public int size() {
		return this.size;
	}

	public boolean empty() {
		return this.size == 0;
	}

	private Node getNode(Node node, K key) {
		if (node == null) {
			return node;
		}
		int result = node.key.compareTo(key);
		if (result > 0) {
			return this.getNode(node.left, key);
		} else if (result < 0) {
			return this.getNode(node.right, key);
		} else {
			return node;
		}
	}

	public boolean contains(K key) {
		return this.getNode(this.root, key) != null;
	}

	public V get(K key) {
		Node node = this.getNode(this.root, key);
		return node == null ? null : node.value;
	}

	public V set(K key, V value) {
		Node node = this.getNode(this.root, key);
		if (node == null) {
			throw new IllegalArgumentException("not found key");
		}
		V retVallue = node.value;
		node.value = value;
		return retVallue;
	}

	private Node add(Node node, K key, V value) {
		if (node == null) {
			this.size++;
			return new Node(key, value);
		}
		int result = node.key.compareTo(key);

		if (result > 0) {
			node.left = this.add(node.left, key, value);
		} else if (result < 0) {
			node.right = this.add(node.right, key, value);
		} else {
			node.value = value; // 覆盖
		}
		return node;
	}

	public void add(K key, V value) {
		this.root = this.add(this.root, key, value);
	}

	protected Node minNode(Node node) {
		if (node.left == null) {
			return node;
		}
		return this.minNode(node.left);
	}

	public Node removeMin(Node node) {
		if (node.left == null) {
			Node rightNode = node.right;
			node.right = null;
			this.size--;
			return rightNode;
		}
		node.left = removeMin(node.left);
		return node;
	}

	protected Node remove(Node node, K key) {
		if (node == null) {
			return node;
		}
		int result = node.key.compareTo(key);
		if (result > 0) {// left
			node.left = remove(node.left, key);
			return node;
		} else if (result < 0) { // right
			node.right = remove(node.right, key);
			return node;
		} else {
			// 删除节点左子树为null
			if (node.left == null) {
				Node rightNode = node.right;
				node.right = null;
				this.size--;
				return rightNode;
			}
			// 删除节点右子树为null
			if (node.right == null) {
				Node leftNode = node.left;
				node.left = null;
				this.size--;
				return leftNode;
			}
			// 左右节点都不为空

			// 获取到右边最小的值的节点
			Node successor = minNode(node.right);
			successor.right = removeMin(node);
			successor.left = node.left;
			node.left = node.right = null;
			return successor;
		}
	}

	public V remove(K key) {
		Node node = this.getNode(this.root, key);
		if (node != null) {
			this.root = remove(this.root, key);
			return node.value;
		}
		throw new IllegalArgumentException("not found key");
	}

	private void forEach(Node node, BiConsumer<K, V> biConsumer) {
		if (node == null) {
			return;
		}
		this.forEach(node.left, biConsumer);
		biConsumer.accept(node.key, node.value);
		this.forEach(node.right, biConsumer);
	}

	public void forEach(BiConsumer<K, V> biConsumer) {
		this.forEach(this.root, biConsumer);
	}
}
