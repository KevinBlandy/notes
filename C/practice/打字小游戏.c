#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <time.h>

#define RANDOM_SIZE 40

int main() {

	char chars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'z', 'y', 'z' };

	int len = sizeof(chars) / sizeof(chars[0]);

	srand((signed int) time(NULL));

	char randomChars[RANDOM_SIZE];

	for (int x = 0; x < RANDOM_SIZE; x++) {
		int randomNumber = rand() % len;
		randomChars[x] = chars[randomNumber];
	}

	for (int x = 0; x < RANDOM_SIZE; x++) {
		printf("%c", randomChars[x]);
	}
	printf("\n");

	for (int x = 0; x < RANDOM_SIZE; x++) {
		char ch = _getch();
		if (ch == randomChars[x]) {
			printf("%c", ch);
		} else {
			printf("-");
		}
	}
	return EXIT_SUCCESS;
}

