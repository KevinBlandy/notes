//自己实现字符串的copy

void strcpy(const char *src,char *dst) {
	char c = *src;
	while(c != '\0'){
		*dst = c;
		dst ++;
		c = *(++ src);
	}
	*dst = '\0';
	return;
}