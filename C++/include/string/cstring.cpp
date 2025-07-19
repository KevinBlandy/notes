--------------------------
cstring
--------------------------
	# cstring
		* 该头文件最初作为 <string.h> 出现在 C 标准库中，用于 C 风格空结尾字节字符串。
		* https://en.cppreference.com/w/cpp/header/cstring.html

		* 尽量少用，而是用 string

--------------------------
cstring
--------------------------

	namespace std {
	  using size_t = /* see description */;                  // freestanding
	 
	  void* memcpy(void* s1, const void* s2, size_t n);      // freestanding
	  void* memmove(void* s1, const void* s2, size_t n);     // freestanding
	  char* strcpy(char* s1, const char* s2);                // freestanding
		* 把 s2 拷贝给 s1。返回 s1。
	  	
	  char* strncpy(char* s1, const char* s2, size_t n);     // freestanding
	  char* strcat(char* s1, const char* s2);                // freestanding
	  	* 拼接两个字符串，返回拼接后的字符串。

	  char* strncat(char* s1, const char* s2, size_t n);     // freestanding
	  int memcmp(const void* s1, const void* s2, size_t n);  // freestanding
	  int strcmp(const char* s1, const char* s2);            // freestanding
	  	* 比较两个字符串大小
		* s1 > s2 返回正数，反之返回小数，相等，返回 0。

	  int strcoll(const char* s1, const char* s2);
	  int strncmp(const char* s1, const char* s2, size_t n); // freestanding
	  size_t strxfrm(char* s1, const char* s2, size_t n);
	  const void* memchr(const void* s, int c, size_t n);    // freestanding
	  void* memchr(void* s, int c, size_t n);                // freestanding
	  const char* strchr(const char* s, int c);              // freestanding
	  char* strchr(char* s, int c);                          // freestanding
	  size_t strcspn(const char* s1, const char* s2);        // freestanding
	  const char* strpbrk(const char* s1, const char* s2);   // freestanding
	  char* strpbrk(char* s1, const char* s2);               // freestanding
	  const char* strrchr(const char* s, int c);             // freestanding
	  char* strrchr(char* s, int c);                         // freestanding
	  size_t strspn(const char* s1, const char* s2);         // freestanding
	  const char* strstr(const char* s1, const char* s2);    // freestanding
	  char* strstr(char* s1, const char* s2);                // freestanding
	  char* strtok(char* s1, const char* s2);                // freestanding
	  void* memset(void* s, int c, size_t n);                // freestanding
	  char* strerror(int errnum);
	  size_t strlen(const char* s);                          // freestanding
	  	
		* 返回字符串长度，不包含末尾的空字符
	}
	 
	#define NULL /* see description */                       // freestanding
