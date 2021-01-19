void cat(char *file_name){
	printf("read file:%s ------------\n",file_name);
	FILE *file = fopen(file_name,"r");
	char ch = fgetc(file);
	while(!feof(file)){
		printf("%c",ch);
		ch = fgetc(file);
	}
	fclose(file);
	free(file);
}

int main(int argc,char **argv) {
	char *file_name = argv[1];
	cat(file_name);
	return EXIT_SUCCESS;
}
