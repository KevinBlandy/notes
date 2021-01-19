#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TSIZE 45

struct film {
	char title[TSIZE];
	int rating;
	struct film * next;
};

char * s_gets(char *, int);

int main(int argc, char **argv) {

	struct film * head = NULL;
	struct film * prev = NULL;
	struct film * current = NULL;

	char input[TSIZE];

	puts("输入第一个电影的标题");

	while (s_gets(input, TSIZE) != NULL && input[0] != '\0') {
		current = (struct film *) calloc(1, sizeof(struct film));
		if (head == NULL) {
			head = current;
		} else {
			prev->next = current;
		}
		current->next = NULL;
		strcpy(current->title, input);
		puts("输入评级<0-10>");
		scanf("%d", &current->rating);
		while (getchar() != '\n') {
			continue;
		}
		puts("输入下一个电影信息（输入空行停止）");
		prev = current;
	}

	if (head == NULL) {
		printf("没有输入任何电影信息");
	} else {
		printf("输入的电影信息列表:\n");
	}

	current = head;
	while (current != NULL) {
		printf("电影:%s,评级:%d\n", current->title, current->rating);
		current = current->next;
	}

	current = head;
	while (current != NULL) {
		head = current->next;
		free(current);
		current = head;
	}
	puts("Bye!");
	return EXIT_SUCCESS;
}

char * s_gets(char *st, int n) {
	char * ret_val;
	char * find;
	ret_val = fgets(st, n, stdin);
	if (ret_val) {
		find = strchr(st, '\n');
		if (find) {
			*find = '\0';
		} else {
			while (getchar() != '\n') {
				continue;
			}
		}
	}
	return ret_val;
}

