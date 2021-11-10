---------------------
JobKey
---------------------
	# 描述一个Job的唯一标识类: public final class JobKey extends Key<JobKey>
		* 一个Job唯一标识由一个key和group组成
	
	# 构造方法
		public JobKey(String name) {
		public JobKey(String name, String group) {
	
	# 静态的工厂方法
		public static JobKey jobKey(String name) {
		public static JobKey jobKey(String name, String group) 
	
	

