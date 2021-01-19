#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {

	FILE *file = fopen("Demo.c", "r");

	fseek(file, 0L, SEEK_END);

	long size = ftell(file);

	for (long x = 1L; x <= size; x++) {

		fseek(file, -x, SEEK_END);

		printf("%c", fgetc(file));
	}

	return EXIT_SUCCESS;
}
