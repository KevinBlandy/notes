#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

struct Node {
	void *key;
	void *value;
	struct Node * next;
};

struct LinkedList {
	int size;
	struct Node * head;
	bool (*equals)(void *, void *);
};

typedef struct LinkedList * map;

map newMap(bool (*)(void *, void *)); /** 创建一个新的map **/
extern bool contains(map, void *);	 /** 是否包含指定的key **/
void * get(map, void *); /** 根据key获取value **/
void add(map, void *, void *);/** 添加元素到map **/
void * set(map map, void *, void *);/** 修改指定的key **/
void * del(map, void *);/** 删除指定的节点 **/

static struct Node * getNode(map map, void *key) {
	struct Node * current = map->head;
	while (current != NULL) {
		if (map->equals(current->key, key)) {
			return current;
		}
		current = current->next;
	}
	return NULL;
}

static bool equals(void *v1, void *v2) {
	char *c1 = (char*) v1;
	char *c2 = (char*) v2;
	return strcmp(c1, c2) == 0;
}

map newMap(bool (*equals)(void *, void *)) {
	map newMap = (map) calloc(sizeof(struct LinkedList), 1);
	if (newMap != NULL) {
		newMap->size = 0;
		newMap->head = NULL;
		newMap->equals = equals;
	}
	return newMap;
}

bool contains(map map, void *key) {
	return getNode(map, key) != NULL;
}

void * get(map map, void *key) {
	struct Node * node = getNode(map, key);
	return node == NULL ? NULL : node->value;
}

void add(map map, void *key, void *value) {
	struct Node * node = getNode(map, key);
	if (node != NULL) {	//覆盖
		node->value = value;
	} else {	//新增
		node = (struct Node *) calloc(sizeof(struct Node), 1);
		node->key = key;
		node->value = value;

		node->next = map->head;
		map->head = node;
		map->size++;
	}
}

void * set(map map, void *key, void *value) {
	struct Node * node = getNode(map, key);
	if (node != NULL) {
		void * ret = node->value;
		node->value = value;
		return ret;
	}
	return NULL;
}

void * del(map map, void *key) {

	struct Node dummyHead = { NULL, NULL, map->head };

	struct Node * pre = &dummyHead;

	struct Node * delNode = NULL;

	//删除节点的父节点

	while (pre->next != NULL) {
		if (map->equals(pre->next->key, key)) {
			delNode = pre->next;
			pre->next = delNode->next;
			map->size--;
			map->head = dummyHead.next;
			break;
		}
		pre = pre->next;
	}
	return delNode == NULL ? NULL : delNode->value;	//遍历到了链表尾还是没找到要删除的节点
}

static void forEach(map map) {
	struct Node * node = map->head;
	while (node != NULL) {
		printf("%s = %s\n", (char *) node->key, (char *) node->value);
		node = node->next;
	}
}

int main(int argc, char **argv) {

	map map = newMap(&equals);

	char * keyArr[] = { "1", "2", "3", "4", "5", "6" };
	char * valueArr[] = { "a", "b", "c", "d", "e", "f" };

	for (int x = 0; x < 6; x++) {
		add(map, keyArr[x], valueArr[x]);
	}

	printf("size = %d\n", map->size);
	forEach(map);
	printf("==============\n");
	for (int x = 0; x < 6; x++) {
		char * value = (char *) del(map, keyArr[x]);
		printf("del %s\n", value);
	}

	forEach(map);

	printf("==============\n");

	printf("size = %d\n", map->size);
	return EXIT_SUCCESS;
}

