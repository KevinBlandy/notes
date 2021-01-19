#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct User {
	unsigned int id;
	char *name;
	size_t name_size;
};

void write() {

	char name[] = "KevinBlandy";

	size_t size = strlen(name);

	struct User user = { 1, NULL };

	user.name = (char *) malloc(size);
	user.name_size = size;

	strcpy(user.name, name);

	FILE *file = fopen("user.data","wb");
	fwrite(&user,sizeof(struct User),1,file);
	fflush(file);

	fwrite(user.name,user.name_size,1,file);
	fflush(file);
	fclose(file);

	printf("wirte:id=%d,name=%s,name_size=%d",user.id,user.name,user.name_size);
	free(user.name);
}

void read(){

	struct User user;

	FILE *file = fopen("user.data","rb");

	fread(&user,sizeof(struct User),1,file);

	char *p = malloc(user.name_size + 1);
	fread(p,user.name_size,1,file);

	user.name = p;

	printf("read:id=%d,name=%s,name_size=%d",user.id,user.name,user.name_size);
	free(user.name);
}

int main(int argc, char **argv) {
	//write();
	read();
	return EXIT_SUCCESS;
}

