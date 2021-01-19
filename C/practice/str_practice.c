#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void reverse(char *);
int count(char *,char *);
void trim(char *);

/**
 *
 * ×Ö·û´®Á·Ï°
 *
 */
int main(int argc, char **argv) {
	char buf[] = "		  		\n aaaa \n";
	trim(buf);
	printf("%s",buf);
	return EXIT_SUCCESS;
}

void trim(char *p){
	char *start = p;
	char *end = p + (strlen(p)) - 1;
	while((*start == ' ' ||  *start == '	' || *start == '\n') && *start != '\0'){
		start ++;
	}
	while((*end == ' ' ||  *end == '	' || *end == '\n') && end != start){
		end --;
	}
	*(end + 1) = '\0';
	strcpy(p,start);
}

int count(char *p,char *sub){
	int count = 0;
	char *temp = strstr(p,sub);
	while(temp != NULL){
		temp = strstr(temp + strlen(sub),sub);
		count ++;
	}
	return count;
}

void reverse(char *p){
	int start = 0;
	int end = strlen(p) - 1;
	while(start < end){
		p[start] = p[start] ^ p[end];
		p[end] = p[start] ^ p[end];
		p[start] = p[start] ^ p[end];
		start ++;
		end --;
	}
}
