--------------------------
swagger-ApiInfo
--------------------------
	# api信息的配置
	# 可以通过 ApiInfoBuilder 来快速构建
	# 配置
		ApiInfo apiInfo =  new ApiInfoBuilder()
			.version("0.0.0.1")											// 版本号
			.contact(new Contact("KevinBlandy", "https://springboot.io", "kevinblandy.cn@gmail.com"))  // 联系人
			.description("用开测试的") // 描述
			.extensions(null) // 添加多个扩展
			.license("许可证") 	// api许可证
			.licenseUrl("https://springboot.io?license") // api许可证地址
			.termsOfServiceUrl("https://springboot.io/service") //  服务条款地址
			.title("swagger 接口文档") //  标题
			.build();
	
	# 静态属性
		// 默认联系人
		public static final Contact DEFAULT_CONTACT = new Contact("", "", "");
		// 默认的实现类
		public static final ApiInfo DEFAULT = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos",
			DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
	
	# 扩展
		* 需要实现接口: VendorExtension
			public interface VendorExtension<T> {
				String getName();
				T getValue();
			}
			
		* 现有的实现
			ListVendorExtension
			ObjectVendorExtension
			StringVendorExtension

