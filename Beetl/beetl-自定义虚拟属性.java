---------------------------
自定义虚拟属性				|
---------------------------
	# 可以为特定的类注册一些虚拟属性
	# 也可以为一些类注册虚拟属性


---------------------------
为特定类注册一个属性		|
---------------------------
	# 通过 GroupTemplate 的 registerVirtualAttributeClass 方法来注册
		GroupTemplate grouptemplate = new GroupTemplate(resourceLoader, configuration);
		grouptemplate.registerVirtualAttributeClass(UserEntity.class, new VirtualClassAttribute() {
            public Object eval(Object o, String attributeName, Context ctx) {
                UserEntity userEntity = (UserEntity) o;	 //当前对象
                if(attributeName.equals("test")){		//判断模版传递的虚拟属性名
                    return "test";
                }
                return "不是test";
            }
        });
	# eval 方法详解
		Object o, 
			* 当前对象
		String attributeName, 
			* 虚拟属性名称
		Context ctx
			* 模板上下文


---------------------------
为一些类注册虚拟属性		|
---------------------------
	# 通过 GroupTemplate 的 registerVirtualAttributeEval 方法来注册
		grouptemplate.registerVirtualAttributeEval(new VirtualAttributeEval() {
			//判断是否要应用虚拟属性到此类
			public boolean isSupport(Class c, String attributeName) {
				return false;
			}

			public Object eval(Object o, String attributeName, Context ctx) {
				return null;
			}
		});
	
	# 这个类跟上面的类一个德行,只是说多了个"是否要应用的"判断方法
