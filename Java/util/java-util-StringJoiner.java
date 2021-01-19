------------------------
StringJoiner			|
------------------------
	# JDK9的新类库
	# 几行代码看明白
		StringJoiner stringJoiner = new StringJoiner(",","[","]");
		
		for(int x = 0 ;x < 10 ; x++) {
			stringJoiner.add(x + "");
		}
		
		System.out.println(stringJoiner.toString()); // [0,1,2,3,4,5,6,7,8,9]