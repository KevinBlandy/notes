------------------------
二分搜索树				|
------------------------
	# 二分搜索树是二叉树
	# 二分搜索树的每个节点值 
		* 都要大于左子树的所有节点值
		* 都要小余右子树的所有节点值
		* 不能重复

	# 存储在树中的数据需要可排序
	
	# 二分搜索树添加元素的非递归写法,跟链表很像
		
	# 包含重复元素的树
		* 左子树小余等于节点,或者右子树大于等于节点
		* 每个节点设置一个:count属性,如果新增了一个重复元素,则设置 count++

------------------------
二分搜索树的实现		|
------------------------
	
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * 
 * 二分搜索树
 * <p>
 * 二分搜索树是二叉树
 * </p>
 * <p>
 * 二分搜索树的每个节点值 都要大于左子树的所有节点值 ,都要小余右子树的所有节点值
 * </p>
 * <p>
 * 存储在树中的数据需要可排序
 * </p>
 * 
 */
public class BinarySearchTree<E extends Comparable<E>> {

	private class Node {
		private E value;
		private Node left;
		private Node right;

		public Node(E value) {
			this(value, null, null);
		}

		public Node(E value, Node left, Node right) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
		}
	}

	private Node root;
	private int size;

	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void addRecursion(E value) {
		this.root = this.addRecursion(this.root, value);
	}

	// 递归添加
	private Node addRecursion(Node node, E value) {
		if (node == null) {
			this.size++;
			return new Node(value);
		}
		int result = value.compareTo(node.value);
		if (result < 0) {
			node.left = this.addRecursion(node.left, value);
		} else if (result > 0) {
			node.right = this.addRecursion(node.right, value);
		}
		return node;
	}

	public boolean contains(E value) {
		return this.contains(this.root, value);
	}

	// 递归检索判断是否存在
	private boolean contains(Node node, E value) {
		if (node == null) {
			return false;
		}
		int result = node.value.compareTo(value);
		if (result < 0) {
			return this.contains(node.left, value);
		} else if (result > 0) {
			return this.contains(node.right, value);
		}
		return true;
	}

	// 递归forEach
	public void forEach(Consumer<E> consumer) {
		this.forEach(this.root, consumer);
	}

	private void forEach(Node node, Consumer<E> consumer) {
		if (node != null) {
			this.forEach(node.left, consumer);
			/**
			 * 写中间就是中序遍历(遍历是有序的) 写前面就是前序遍历 写后面就是后序遍历
			 */
			consumer.accept(node.value);
			this.forEach(node.right, consumer);
		}
	}

	// 非递归的前序遍历,依赖栈结构(深度优先)
	public void forEach() {
		Stack<Node> stack = new Stack<>();
		stack.push(this.root);
		while (!stack.isEmpty()) {
			Node current = stack.pop(); // 栈顶元素
			System.out.println(current.value); // 处理元素
			if (current.right != null) {
				stack.push(current.right);
			}
			if (current.left != null) {
				stack.push(current.left);
			}
		}
	}

	// 非递归遍历,依赖队列,属于层序遍历(广度优先)
	public void forEach1() {
		Queue<Node> queue = new LinkedList<>();
		queue.add(this.root);
		while (!queue.isEmpty()) {
			Node current = queue.remove(); // 队列尾元素
			System.out.println(current.value);// 处理元素
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
		}
	}

	private Node min(Node node) {
		if (node.left == null) {
			return node;
		}
		return this.min(node.left);
	}

	// 获取最小的值
	public E min() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("null map");
		}
		return this.min(this.root).value;
	}

	public Node max(Node node) {
		if (node.right == null) {
			return node;
		}
		return this.max(node.right);
	}

	// 获取最大的值
	public E max() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("null map");
		}
		return this.max(this.root).value;
	}

	// 删除最小值节点,并且返回结果根节点
	private Node removeMin(Node node) {
		if (node.left == null) {
			Node rightNode = node.right;
			node.right = null;
			this.size--;
			return rightNode;
		}
		node.left = this.removeMin(node.left);
		return node;
	}

	// 删除并返回最小值
	public E removeMin() {
		E ret = this.min();
		this.root = this.removeMin(this.root);
		return ret;
	}

	// 删除最大值节点,并且返回结果根节点
	private Node removeMax(Node node) {
		if (node.right == null) {
			Node leftNode = node.left;
			node.left = null;
			this.size--;
			return leftNode;
		}
		node.right = this.removeMax(node.right);
		return node;
	}

	// 删除并返回最大值
	public E removeMax() {
		E ret = this.max();
		this.root = this.removeMax(this.root);
		return ret;
	}

	// 删除指定的树下的指定节点，返回删除节点后的根
	private Node remove(Node node, E value) {
		if (node == null) {
			return node;
		}
		int result = value.compareTo(node.value);
		if (result < 0) {
			node.left = this.remove(node.left, value);
			return node;
		} else if (result > 0) {
			node.right = this.remove(node.right, value);
			return node;
		} else {
			if (node.left == null) {
				Node rightNode = node.right;
				node.right = null;
				this.size--;
				return rightNode;
			}
			if (node.right == null) {
				Node leftNode = node.left;
				node.left = null;
				this.size--;
				return leftNode;
			}

			Node rightMin = min(node.right);
			rightMin.right = removeMin(node.right);
			rightMin.left = node.left;
			
			node.left = node.right = null;

			return rightMin;
		}
	}

	// 删除任意节点
	public void remove(E e) {
		this.root = this.remove(this.root, e);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		this.generateBinarySearchTree(this.root, 0, stringBuilder);
		return stringBuilder.toString();
	}

	// 生成节点描述字符串
	private void generateBinarySearchTree(Node node, int depth, StringBuilder stringBuilder) {
		if (node == null) {
			stringBuilder.append(this.generateDepthString(depth) + "null\n");
			return;
		}
		stringBuilder.append(generateDepthString(depth) + node.value + "\n");
		generateBinarySearchTree(node.left, depth + 1, stringBuilder);
		generateBinarySearchTree(node.right, depth + 1, stringBuilder);
	}

	// 生成层次字符串
	private String generateDepthString(int depth) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			stringBuilder.append("--");
		}
		return stringBuilder.toString();
	}
}
