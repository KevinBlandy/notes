#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
	int x = 1000;
	int count = 0;
	while(x > 0){
		x /= 10;
		count ++;
	}
	printf("%d",count);
	return EXIT_SUCCESS;
}
