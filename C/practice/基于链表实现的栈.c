#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

struct Node {
	void *value;
	struct Node * next;
};

struct LinkedList {
	int size;
	struct Node * head;
};

typedef struct LinkedList * stack;

int push(stack stack, void *value) {
	if (stack == NULL) {
		return 1;
	}
	struct Node * node = (struct Node *) calloc(sizeof(struct Node), 1);
	node->value = value;
	if (stack->head == NULL) {
		stack->head = node;
		node->next = NULL;
	} else {
		node->next = stack->head;
		stack->head = node;
	}
	stack->size++;
	return 0;
}
void * pop(stack stack) {
	if (stack == NULL || stack->head == NULL) {
		return NULL;
	}
	struct Node * head = stack->head;
	stack->head = head->next;
	void *value = head->value;
	free(head);
	stack->size--;
	return value;
}

int main(int argc, char **argv) {
	struct LinkedList list = { 0, NULL };
	stack stack = &list;
	int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	for (int x = 0; x < 10; x++) {
		push(stack, &arr[x]);
	}
	printf("size = %d\n", stack->size);
	for (int x = 0; x < 10; x++) {
		int *value = (int *) pop(stack);
		printf("pop = %d size = %d\n", *value, stack->size);
	}
	return EXIT_SUCCESS;
}

