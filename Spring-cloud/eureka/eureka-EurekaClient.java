----------------------------------
EurekaCient-获取服务提供者的IP端口|
----------------------------------

	@Autowired
	private EurekaClient discoverClient
	/**
		获取指定名称的服务提供者的IP地址和端口
	*/
	public String serviceUrl(){
		InstanceInfo instance = discoverClient.getNextServerFromEureka("SERVICE-USER",false);
		return instance.getHomePageUrl();
	}