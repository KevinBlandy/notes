-----------------------
枚举
-----------------------
	# 默认序列化/反序列化值使用 name() 属性
	
	# 可以通过 @SerializedName 标识在枚举实例上, 改变枚举的值为自定义的值
		enum Gender {
			@SerializedName("女孩")
			GIRL, 

			@SerializedName("男孩")
			BOY, 

			@SerializedName("未知")
			UNKNOWN
		}

		private Gnder gender = GIRL; // {"gender": "女孩"}

	# 也可以通过注册 TypeAdapter 来自定义枚举的序列化规则
		registerTypeHierarchyAdapter(Enum.class, new JsonSerializer<Enum<?>> () {
			@Override
			public JsonElement serialize(Enum<?> src, Type typeOfSrc, JsonSerializationContext context) {
				// 使用 ordinal 序列化成基本数据类型
				return new JsonPrimitive(src.ordinal());
			}
			
		})
		registerTypeHierarchyAdapter(Enum.class, new JsonDeserializer<Enum<?>> () {

			@Override
			public Enum<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				try {
					// 必须是基本数据类型
					if (json.isJsonPrimitive()) {
						
						JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
						
						// 反射读取所有得枚举实例
						Enum<?>[] enumConstants  = (Enum<?>[]) Class.forName(typeOfT.getTypeName()).getEnumConstants();
						
						if (jsonPrimitive.isNumber()) { // 数字
							return enumConstants[jsonPrimitive.getAsInt()];
						} else if (jsonPrimitive.isString()) { // 字符串
							String val = jsonPrimitive.getAsString();
							for (Enum<?> constant : enumConstants) {
								if (constant.name().equalsIgnoreCase(val)) {
									return constant;
								}
							}
						} 
					} 
				} catch (ClassNotFoundException | ArrayIndexOutOfBoundsException e) {
					
				}
				throw new IllegalArgumentException("bad param:" + json.getAsString());
			}
		})