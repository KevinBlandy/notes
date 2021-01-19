#include <stdio.h>
#include <stdlib.h>
#include <time.h>

char * const FILE_NAME = "demo.txt";
char OPERATOR[] = "+-*/";

int main(int argc, char **argv) {

	srand((unsigned int)time(NULL));

	FILE *file = fopen(FILE_NAME,"w");

	for(int x = 0 ;x < 10 ;x++ ){

		int v1 = rand() % 100;
		int v2 = rand() % 100;

		char template[100];
		sprintf(template,"%d%c%d=\n",v1,OPERATOR[rand() %  4],v2);

		fputs(template,file);
	}

	fclose(file);

	file = fopen(FILE_NAME,"r+");

	char buf[100];

	char *line = fgets(buf,sizeof(buf) - 1,file);

	while(line){

		line = fgets(buf,sizeof(buf) - 1,file);

		int v1;
		int v2;
		char operator;

		sscanf(buf,"%d%c%d=\n",&v1,&operator,&v2);

		int result;
		if (operator == '+'){
			result = v1 + v2;
		}else if(operator == '-'){
			result = v1 - v2;
		}else if(operator == '*'){
			result = v1 * v2;
		}else if(operator == '/'){
			result = v1 / v2;
		}

		printf("%d %c %d = %d\n",v1,operator,v2,result);
	}

	return EXIT_SUCCESS;
}




