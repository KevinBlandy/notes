#include <stdlib.h>
#include <string.h>
#include <stdio.h>

struct Node {
	void *value;
	struct Node * next;
};

struct LinkedList {
	int size;
	struct Node * head;
	struct Node * tail;
};

typedef struct LinkedList * queue;

extern queue newQueue(); 	/** 实例化一个队列 **/
int equeue(queue, void *); /**添加一个元素到首**/
void * dequeue(queue); 		/**从队列尾获取一个元素**/

queue newQueue() {
	queue ret = (queue) calloc(sizeof(struct LinkedList), 1);
	ret->size = 0;
	ret->head = NULL;
	ret->tail = NULL;
	return ret;
}

int equeue(queue queue, void *value) {
	if (queue == NULL || value == NULL) {
		return 1;
	}
	struct Node * node = calloc(sizeof(struct Node), 1);
	node->value = value;
	node->next = NULL;
	if (queue->head == NULL) {
		queue->tail = node;
		queue->head = node;
	} else {
		queue->tail->next = node;
		queue->tail = queue->tail->next;
	}
	queue->size++;
	return 0;
}

void * dequeue(queue queue) {
	if (queue == NULL || queue->head == NULL) {
		return NULL;
	}
	struct Node * node = queue->head;
	queue->head = node->next;
	if (queue->head == NULL) {
		queue->tail = NULL;
	}
	void * ret = node->value;
	free(node);
	queue->size--;
	return ret;
}

static void consumer(queue queue, void (*accept)(int index, void *)) {
	if (queue == NULL || queue->head == NULL || accept == NULL) {
		return;
	}
	struct Node * node = queue->head;
	int index = 0;
	while (node != NULL) {
		accept(index++, node->value);
		node = node->next;
	}
}

static void console(int index, void *value) {
	int * p = (int *) value;
	printf("%d	%d\n", index, *p);
}

int main(int argc, char **argv) {
	int arr[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	queue queue = newQueue();
	for (int x = 0; x < 10; x++) {
		equeue(queue, &arr[x]);
	}
	consumer(queue, &console);
	printf("size = %d\n", queue->size);
	for (int x = 0; x < 10; x++) {
		int * value = (int *)dequeue(queue);
		printf("dequeue=%d size=%d\n",*value,queue->size);
	}
	return EXIT_SUCCESS;
}

