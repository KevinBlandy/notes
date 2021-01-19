---------------------------------------------
基于链表实现的带有尾指针的队列				|
---------------------------------------------
	# 出队列,时间复杂度减少了,不需要遍历,因为存在指针


public class LinkedListQueue<E> {
	
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
		}
	}

	private Node head;		//头指针
	private Node tail;		//尾指针
	private int size;

	public LinkedListQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void equeue(E e) {
		if (this.tail == null) {
			this.tail = new Node(e);
			this.head = this.tail;
		} else {
			this.tail.next = new Node(e);
			this.tail = this.tail.next;
		}
		this.size++;
	}

	public E dequeue() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("空的queue");
		}
		Node retNode = this.head;
		this.head = this.head.next;
		retNode.next = null;
		if (this.head == null) {
			this.tail = null;
		}
		this.size--;
		return retNode.e;
	}

	public E front() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("空的queue");
		}
		return this.head.e;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Queue front ");

		Node cur = head;
		while (cur != null) {
			stringBuffer.append(cur + " -> ");
			cur = cur.next;
		}

		stringBuffer.append("NULL tail");
		return stringBuffer.toString();
	}
}
