#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct List {
	void *value;
	struct List *next;
};

void listForEach(struct List **lists,size_t size){
	for(int x = 0 ;x  < size ; x++){
		if(x == (size - 1)){
			printf("value = %s ,next = NULL\n",lists[x] -> value);
		}else{
			printf("value = %s ,next = %s\n",lists[x] -> value,lists[x] -> next -> value);
		}
	}
}

int main(int argc,char **argv) {

	struct List *lists[10];

	for(int x = 0 ;x < 10 ;x++){

		//从堆内存获取构造空间
		struct List *list = (struct List *)malloc(sizeof(struct List));

		//设置value值
		char *value = (char *)malloc(4);
		sprintf(value,"[%d]",x);
		list -> value = value;

		//链表节点关系
		if (x > 0){
			lists[x - 1] -> next =  list;
		}
		lists[x] = list;
	}

	listForEach(lists,10);

	//内存释放
	for(int x = 0 ;x < 10 ;x++ ){
		free(lists[x] -> value);
		free(lists[x]);
	}
	return EXIT_SUCCESS;
}
