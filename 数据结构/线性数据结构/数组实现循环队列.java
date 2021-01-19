
-----------------------
循环队列				|
-----------------------
	# 底层使用数组
	# 但是性能比较好,不会有数组的'位移'操作,通过两个指针交替位移来读写数据
	
	# 需要定义几个变量来控制队列
		size
			* 队列的最大容积

		front
			* 头指针，指向队列的第一个元素，初始值为 0
			* 队列的第一个元素：arr[front]

		tail
			* 队列的最后一个位置的前一个位置，初始值为 0
		
		
		* 判断队列是否存满
			(tail + 1) % size = front 
		
		* 判断队列是否为空
				tail == front

		* 获取队列中的数据量
			(tail + size - front) % size 
		

-----------------------
实现Demo				|
-----------------------
@SuppressWarnings("unchecked")
public class LoopQueue<E> {
	private E[] array;
	private int front; // 头指针
	private int tail; 	// 尾指针
	private int size;

	public LoopQueue(int capacity) {
		this.array = (E[]) new Object[capacity + 1];
		this.front = 0;
		this.tail = 0;
		this.size = 0;
	}

	public int capacity() {
		return this.array.length - 1;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.front == this.tail;
	}

	public void enqueue(E e) {
		if ((this.tail + 1) % this.array.length == this.front) {
			this.resize(this.capacity() * 2);
		}
		this.array[this.tail] = e;
		this.tail = (this.tail + 1) % this.array.length;
		this.size++;
	}

	public E dequeue() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("empty queue.");
		}
		E value = this.array[front];
		this.array[front] = null;
		this.front = (this.front + 1) % this.array.length;
		this.size--;
		if (this.size == this.capacity() / 4 && this.capacity() / 2 != 0) {
			this.resize(this.capacity() / 2);
		}
		return value;
	}

	public E front() {
		if (this.isEmpty()) {
			throw new IllegalArgumentException("empty queue.");
		}
		return this.array[this.front];
	}

	private void resize(int capacity) {
		E[] newArray = (E[]) new Object[capacity + 1];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = this.array[(i + this.front) % this.array.length];
		}
		this.array = newArray;
		this.front = 0;
		this.tail = this.size;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Queue: size = %d , capacity = %d\n", size, this.capacity()));
		stringBuilder.append("front [");
		for (int i = front; i != tail; i = (i + 1) % this.array.length) {
			stringBuilder.append(this.array[i]);
			if ((i + 1) % this.array.length != tail) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append("] tail");
		return stringBuilder.toString();
	}
}


