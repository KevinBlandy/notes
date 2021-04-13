------------------------
json
------------------------
	# json库的封装，都是静态方法

------------------------
静态
------------------------
	public static final JsonCodec CODEC = load().codec()
		* 全局预定义的编解码器

	public static io.vertx.core.spi.JsonFactory load()
		* 从spi加载json的编码解码器

	public static String encode(Object obj) throws EncodeException
	public static Buffer encodeToBuffer(Object obj) throws EncodeException
	public static String encodePrettily(Object obj) throws EncodeException

	public static <T> T decodeValue(String str, Class<T> clazz) throws DecodeException
	public static Object decodeValue(String str) throws DecodeException
	public static Object decodeValue(Buffer buf) throws DecodeException
	public static <T> T decodeValue(Buffer buf, Class<T> clazz) throws DecodeException
		* 编码Json数据为自定义对象
