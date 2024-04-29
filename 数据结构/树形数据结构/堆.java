-------------------------
��						 |
-------------------------
	# �����,��һ����ȫ������
		* ����㶼���ӽ��ҪС,���ǳ�Ϊ��С��
		* ����㶼���ӽ��Ҫ��,���ǳ�Ϊ����

		* �ܽ���˵���߼������ϣ�������ȫ�����������ӽڵ�����ض�˳�򣬷�Ϊ���Ѻ���С�ѣ����Ѹ������ģ���С�Ѹ�����С�ģ���ʹ�������������洢��

	# һ���ѿ�������������ʾ

	# d ��� d-ary heap
	
	# ������
	
	# �����

	# �Შ������

-------------------------
����					 |
-------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * ����
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
	 * ������ȫ�������������ʾ��,һ����������ʾ��Ԫ�صĸ��׽ڵ������
	 * 
	 * @param index
	 * @return
	 */
	private int parent(int index) {
		if (index == 0) {
			throw new IllegalArgumentException("0 û�и����ڵ�");
		}
		return (index - 1) / 2;
	}

	/**
	 * ������ȫ�������������ʾ��,һ����������ʾ��Ԫ�ص����ӽڵ������
	 * 
	 * @param index
	 * @return
	 */
	private int leftChild(int index) {
		return index * 2 + 1;
	}

	/**
	 * ������ȫ�������������ʾ��,һ����������ʾ��Ԫ�ص��Һ��ӽڵ������
	 * 
	 * @param index
	 * @return
	 */
	private int rightChild(int index) {
		return index * 2 + 2;
	}

	/**
	 * ���������Ԫ��,�ϸ������ʵ�λ��
	 * 
	 * @param e
	 */
	public void add(E e) {

		// ��ӵ�����
		this.data.add(e);
		// ά��������
		siftUp(this.size() - 1);
	}

	public E findMax() {
		if (this.empty()) {
			throw new IllegalArgumentException("�յĶ�");
		}
		return this.data.get(0);
	}

	/**
	 * ��ȡ����Ԫ��
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
	 * ȡ�����Ԫ�غ�,����һ���µ�Ԫ�� 
	 * ʵ��1����extractMax(),��add������ O(logn)�Ĳ��� 
	 * ʵ��2������ֱ�ӽ��Ѷ�Ԫ���滻�Ժ�Sift
	 * Down��һ��O(logn)�Ĳ���
	 * 
	 * @param e
	 * @return
	 */
	public E replace(E e) {
		E ret = this.findMax();
		this.data.set(0, e); // �ѶѶ�Ԫ���滻Ϊ�µ�Ԫ��
		// siftDown
		this.siftDown(0);
		return ret;
	}

	/**
	 * ��������������Ϊ��(���)����״�����ҷŽ�data
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

	// �¸�
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

	// Ԫ���ϸ�
	private void siftUp(int k) {
		while (k > 0 && this.data.get(this.parent(k)).compareTo(this.data.get(k)) < 0) {
			this.swap(k, this.parent(k));
			k = this.parent(k);
		}
	}

	/**
	 * ����[]�����Ǳ��Ԫ��
	 * 
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		if (i < 0 || i >= this.size() || j < 0 || j >= this.size()) {
			throw new IllegalArgumentException("�±겻�Ϸ�");
		}
		E e = this.data.get(i);
		this.data.set(i, this.data.get(j));
		this.data.set(j, e);
	}

	/**
	 * ��������
	 * 
	 * @param action
	 */
	public void forEach(Consumer<? super E> action) {
		this.data.forEach(action);
	}
}

-------------------------
��С��					 |
-------------------------