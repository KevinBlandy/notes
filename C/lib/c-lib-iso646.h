-------------------------
iso646					 |
-------------------------
	# 该头文件提供了一些几个指令,可以代替判断关键字或者位运算关键字
	# 例如
		and,or,not 代替 &&, ||, !
	# 源码
		#ifndef _ISO646_H
		#define _ISO646_H

		#ifndef __cplusplus
		#define and	&&
		#define and_eq	&=
		#define bitand	&
		#define bitor	|
		#define compl	~
		#define not	!
		#define not_eq	!=
		#define or	||
		#define or_eq	|=
		#define xor	^
		#define xor_eq	^=
		#endif

		#endif
