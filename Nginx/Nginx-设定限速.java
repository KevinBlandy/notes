-----------------------------
Nginx-设定限速				 |
-----------------------------
	lication / {
		limit_rate_after 10m;
		limit_rate 100k;
	}	
	if($http_user_agent ~ Google|Yahoo|baidu){
		limit_rate 20k;
	}
