#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

struct Node {
	void *value;
	void *left;
	void *right;
};

struct BinarySearchTree {
	struct Node * root;
	int size;
};

typedef struct BinarySearchTree * tree;

extern tree newTree();
extern void add(tree, void *, int (*)(const void *, const void *));
extern bool contains(tree, void *, int (*)(const void *, const void *));
extern void forEach(tree, void (*)(const void *));

tree newTree() {tree tree = (struct BinarySearchTree *) calloc(sizeof(struct BinarySearchTree), 1);
	tree->size = 0;
	tree->root = NULL;
	return tree;
}

static struct Node * newNode(void *value) {
	struct Node * node = (struct Node *) calloc(sizeof(struct Node), 1);
	node->value = value;
	node->left = NULL;
	node->right = NULL;
	return node;
}

static int addNode(struct Node * node, void * value,
		int (*compareTo)(const void *, const void *)) {
	int result = compareTo(value, node->value);
	if (result < 0) {
		if (node->left == NULL) {
			node->left = newNode(value);
			return 1;
		}
		return addNode(node->left, value, compareTo);
	} else if (result > 0) {
		if (node->right == NULL) {
			node->right = newNode(value);
			return 1;
		}
		return addNode(node->right, value, compareTo);
	}
	return 0;
}

void add(tree tree, void * value, int (*compareTo)(const void *, const void *)) {
	if (tree->root == NULL) {
		tree->root = newNode(value);
		tree->size++;
	} else {
		tree->size += addNode(tree->root, value, compareTo);
	}
}

static bool containsNode(struct Node * node, void * value,int (*compareTo)(const void *, const void *)) {
	if (node == NULL) {
		return false;
	}
	int result = compareTo(value, node->value);
	if (result < 0) {
		return containsNode(node->left, value, compareTo);
	} else if (result > 0) {
		return containsNode(node->right, value, compareTo);
	}
	return true;
}

bool contains(tree tree, void * value,
		int (*compareTo)(const void *, const void *)) {
	return containsNode(tree->root, value, compareTo);
}

static void forEachNode(struct Node * node, void (*consumer)(const void *)) {
	if (node == NULL) {
		return;
	}
	forEachNode(node->left, consumer);
	consumer(node->value);
	forEachNode(node->right, consumer);
}

void forEach(tree tree, void (*consumer)(const void *)) {
	forEachNode(tree->root, consumer);
}

static int compareTo(const void * v1, const void * v2) {
	int i1 = *((int *) v1);
	int i2 = *((int *) v2);
	if (i1 < i2) {
		return -1;
	} else if (i1 > i2) {
		return 1;
	}
	return 0;
}

static void consumer(const void *v) {
	printf("%d\n", *((int *) v));
}

int main(int argc, char **argv) {
	tree tree = newTree();

	int arr[] = { 9, 4, 7, 8, 5, 1, 6, 3, 2, 0 };

	for (int x = 0; x < 10; x++) {
		add(tree, &arr[x], &compareTo);
	}
	for (int x = 0; x < 10; x++) {
		add(tree, &arr[x], &compareTo);
	}

	printf("size = %d\n", tree->size);
	int x = -1;
	printf("%d constains = %d\n", x, contains(tree, &x, &compareTo));
	forEach(tree, &consumer);
	return EXIT_SUCCESS;
}
