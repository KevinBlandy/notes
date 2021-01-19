#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_NAME_SIZE 10

struct User {
	int id;
	char *name;
	int height;
};

extern int compareTo(const void *, const void *);

int main(int argc, char **argv) {

	printf("多少人有\n");

	int size;
	scanf("%d", &size);

	//TODO 计算分组数组长度

	struct User * users1[size];
	struct User * users2[size];
	struct User * users3[size];

	for (int x = 0; x < size; x++) {

		struct User * user = (struct User *) calloc(sizeof(struct User), 1);
		user->id = x;

		printf("请输入第[%d]个人的名字和身高(空格隔开)\n", x + 1);
		user->name = (char *) calloc(sizeof(char), MAX_NAME_SIZE);
		fscanf(stdin, "%s %d", user->name, &user->height);

		if (x % 3 == 0) {
			users1[x] = user;
		} else if (x % 3 == 1) {
			users2[x] = user;
		} else if (x % 3 == 2) {
			users3[x] = user;
		}
	}

	printf("开始排序\n");
	qsort(users1, size, sizeof(struct User *), &compareTo);
	qsort(users2, size, sizeof(struct User *), &compareTo);
	qsort(users3, size, sizeof(struct User *), &compareTo);

	return EXIT_SUCCESS;
}

int compareTo(const void *v1, const void *v2) {
	struct User **user1 = (struct User **) v1;
	struct User **user2 = (struct User **) v2;
	int height1 = (*user1)->height;
	int height2 = (*user2)->height;
	return height1 > height2;
}

