package io.javaweb.datastructure;

import java.util.Arrays;
import java.util.function.Consumer;

public class Array<E> {

	private E[] arr = null;

	private int size;

	public Array() {
		this(10);
	}

	@SuppressWarnings("unchecked")
	public Array(int capacity) {
		this.arr = (E[]) new Object[capacity];
		this.size = 0;
	}

	public int getCapacity() {
		return this.arr.length;
	}

	public E get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index 不能大于" + (this.size - 1) + ",不能小于0");
		}
		return this.arr[index];
	}

	public boolean contains(E item) {
		for (E e : this.arr) {
			if (e.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public int indexOf(E item) {
		for (int i = 0; i < this.size; i++) {
			if (this.arr[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public void set(int index, E item) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index 不能大于" + (this.size - 1) + ",不能小于0");
		}
		this.arr[index] = item;
	}

	public E remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index 不能大于" + (this.size - 1) + ",不能小于0");
		}
		E e = this.arr[index];
		for (int i = index + 1; i < size; i++) {
			this.arr[i - 1] = this.arr[i];
		}
		this.size--;
		return e;
	}

	public E removeFirst() {
		return this.remove(0);
	}

	public E removeLast() {
		return this.remove(this.size - 1);
	}

	public void removeElement(E item) {
		int index = this.indexOf(item);
		if (index != -1) {
			this.remove(index);
		}
	}

	public int size() {
		return this.size;
	}

	public void addFirst(E item) {
		this.add(0, item);
	}

	public void addLast(E item) {
		this.add(this.size, item);
	}

	//添加元素到指定的位置
	public void add(int index, E item) {
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("index 不能大于" + (this.size - 1) + ",不能小于0");
		}
		if (this.size == this.arr.length) {
			//throw new IllegalArgumentException("数组满了");
			// 扩容
			this.resize(this.arr.length * 2);
		}
		for (int i = size - 1; i >= index; i--) {
			this.arr[i + 1] = this.arr[i];
		}

		this.arr[index] = item;
		this.size++;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void consumer(Consumer<E> consumer) {
		if (this.size > 0) {
			for (E e : this.arr) {
				consumer.accept(e);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Array: size = %d , capacity = %d\n", size, this.arr.length));
		stringBuilder.append('[');
		for (int i = 0; i < size; i++) {
			stringBuilder.append(this.arr[i]);
			if (i != size - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(']');
		return stringBuilder.toString();
	}

	//执行扩容
	private void resize(int newCapacity) {
		this.arr = Arrays.copyOf(this.arr, newCapacity);
//		@SuppressWarnings("unchecked")
//		E[] newArr = (E[]) new Object[newCapacity];
//		for (int i = 0; i < size; i++) {
//			newArr[i] = this.arr[i];
//		}
//		this.arr = newArr;
	}
}
