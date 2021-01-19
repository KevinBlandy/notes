----------------------------
优先队列					|
----------------------------
	# 普通队列:先进先出,后进后出
	# 优先队列
		* 出队顺序和入队顺序无关
		* 和优先级相关
	

	# 底层可以使用堆来实现


----------------------------
基于最大堆实现				|
----------------------------
/**
 * 
 * 优先队列
 * @author KevinBlandy
 *
 * @param <E>
 */
public class PriorityQueue<E extends Comparable<E>> {
	
	private MaxHeap<E> maxHeap;
	
	public PriorityQueue() {
		this.maxHeap = new MaxHeap<>();
	}
	
	public int size() {
		return this.maxHeap.size();
	}
	
	public boolean empty() {
		return this.maxHeap.empty();
	}
	
	/**
	 * 查看队首元素
	 */
	public E getFront() {
		return this.maxHeap.findMax();
	}
	
	/**
	 * 入队
	 * 由低层的最大堆自己维护堆性质
	 */
	public void enqueue(E e) {
		this.maxHeap.add(e);
	}
	
	/**
	 * 出队
	 * 由低层的最大堆维护自己的堆性质
	 */
	public E dequeue() {
		return this.maxHeap.extractMax();
	}
}