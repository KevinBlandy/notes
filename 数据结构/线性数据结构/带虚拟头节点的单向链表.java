
public class LinkedList<E> {
	// Node
	private class Node {
		public E e;
		public Node next;

		public Node(E e, Node next) {
			this.e = e;
			this.next = next;
		}

		public Node(E e) {
			this(e, null);
		}

		@Override
		public String toString() {
			return this.e.toString();
			// return "Node [e=" + e + ", next=" + next + "]";
		}
	}

	// 虚拟头节点
	private Node dummyHead;

	private int size;

	public LinkedList() {
		this.dummyHead = new Node(null, null);
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void addFirst(E e) {
		this.add(0, e);
	}

	public void addLast(E e) {
		this.add(this.size, e);
	}

	public void add(int index, E e) {
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("index 必须大于等于0,小余等于" + this.size);
		}
		Node prev = dummyHead;
		for (int i = 0; i < index; i++) {
			prev = prev.next;
		}
		Node node = new Node(e);
		node.next = prev.next;
		// prev.next = new Node(e,prev.next);
		prev.next = node;
		this.size++;
	}

	public E get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index 必须大于等于0,小余" + this.size);
		}
		Node cur = this.dummyHead.next;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		return cur.e;
	}

	public E getFirst() {
		return this.get(0);
	}

	public E getLast() {
		return this.get(this.size - 1);
	}

	public void set(int index, E e) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index 必须大于等于0,小余" + this.size);
		}
		Node cur = this.dummyHead.next;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		cur.e = e;
	}

	public boolean contains(E e) {
		if (this.size > 0) {
			Node cur = this.dummyHead.next;
			while (cur != null) {
				if (cur.e.equals(e)) {
					return true;
				}
				cur = cur.next;
			}
		}
		return false;
	}

	public E removeFirst() {
		return this.remove(0);
	}

	public E removeLast() {
		return this.remove(this.size - 1);
	}

	public E remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index 必须大于等于0,小余" + this.size);
		}
		Node prev = dummyHead;
		for (int i = 0; i < index; i++) {
			prev = prev.next;
		}
		Node deleteNode = prev.next;
		prev.next = deleteNode.next;
		deleteNode.next = null;
		this.size--;
		return deleteNode.e;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		Node cur = this.dummyHead.next;
		while (cur != null) {
			stringBuilder.append(cur + "->");
			cur = cur.next;
		}
		stringBuilder.append("NULL");
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		for (int x = 0; x < 5; x++) {
			list.addFirst(x);
		}
		list.add(2, 666);
		list.remove(2);
		System.out.println(list);
	}
}
