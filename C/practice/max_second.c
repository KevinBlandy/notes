//获取倒数第二大的数据

int max_second(int *p,size_t len){

	int max = 0;
	int second = 0;

	for(int x = 0 ;x < len; x++){
		int v = *(p + x);
		if(v > max){
			second = max;
			//第二大的值,为上一次的最大值
			max = v;
		}else{
			if(v > second){
				second = v;
			}
		}
	}
	return second;
}