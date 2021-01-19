------------------------
Node					|
------------------------
	# 表示目标服务节点

------------------------
Node-属性				|
------------------------
	private String ip;
	private int port;

------------------------
Node-方法				|
------------------------
	# 构造方法
		public Node(String ip, int port){
			super();
			if (StringUtils.isBlank(ip) || "0:0:0:0:0:0:0:0".equals(ip)){
				ip = "0.0.0.0";
			}
			this.setIp(ip);
			this.setPort(port);
		}
	
	# 普通方法
		* 就是getter&setter
	
