#include <string.h>

struct IntArray {
	int size;			//数据长度
	int capacity;		//容器长度
	int * arr;			//首元素指针
};

int init(struct IntArray *, int);		/** 初始化 **/
int addFirst(struct IntArray *,int);	/** 添加元素到开头 **/
int addLast(struct IntArray *,int);		/** 添加元素到末尾 **/
int add(struct IntArray *, int, int);	/** 添加元素到指定的位置 **/
int set(struct IntArray *, int, int);	/** 设置元素到指定的位置 **/
void forEach(struct IntArray *);		/** 遍历,打印元素 **/
int indexOf(struct IntArray *,int);		/** 获取指定元素第一次出现的下标 **/
int removeIndex(struct IntArray *, int);/** 根据下标删除元素 **/
void removeItem(struct IntArray *, int);/** 删除第一个指定值的元素 **/
int removeFirst(struct IntArray *);		/** 删除第一个元素 **/
int removeLast(struct IntArray *);		/** 删除最后一个元素 **/


int init(struct IntArray *arr, int capacity) {
	if(capacity <= 0){
		return -1;
	}
	arr->capacity = capacity;
	arr->size = 0;
	arr->arr = (int *) calloc(sizeof(int), arr->capacity);
	return 0;
}

int addFirst(struct IntArray *arr,int value){
	return add(arr, 0, value);
}

int addLast(struct IntArray *arr,int value){
	return add(arr, arr->size, value);
}

int add(struct IntArray *arr, int index, int value) {
	if (index < 0 || index > arr->size) {
		return -1;
	}
	if (arr->size == arr->capacity) {
		arr->capacity = arr->capacity * 2;
		realloc(arr->arr, arr->capacity);
	}

	for (int i = arr->size - 1; i >= index; i--) {
		arr->arr[i + 1] = arr->arr[i];
	}
	arr->arr[index] = value;
	arr->size++;
	return 0;
}

int set(struct IntArray *arr, int index, int value) {
	if (index < 0 || index >= arr->size) {
		return 1;
	}
	arr->arr[index] = value;
	return 0;
}

int indexOf(struct IntArray *arr,int value){
	for (int i = 0; i < arr->size; i++) {
		if(arr->arr[i] == value){
			return i;
		}
	}
	return -1;
}

int removeIndex(struct IntArray *arr, int index) {
	if (index < 0 || index >= arr->size) {
		return -1;
	}
	int value = arr->arr[index];
	for (int i = index + 1; i < arr->size; i++) {
		arr->arr[i - 1] = arr->arr[i];
	}
	arr->size--;
	return value;
}

int removeFirst(struct IntArray *arr){
	return removeIndex(arr, 0);
}
int removeLast(struct IntArray *arr){
	return removeIndex(arr, arr->size - 1);
}

void removeItem(struct IntArray *arr, int value) {
	int index = indexOf(arr, value);
	if(index != -1){
		removeIndex(arr, index);
	}
}

void forEach(struct IntArray *arr) {
	printf("size=%d capacity=%d [", arr->size, arr->capacity);
	for (int i = 0; i < arr->size; i++) {
		printf("%d", arr->arr[i]);
		if (i != (arr->size - 1)) {
			printf(",");
		}
	}
	printf("]\n");
}





