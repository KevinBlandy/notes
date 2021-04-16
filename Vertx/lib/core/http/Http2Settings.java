------------------------
Http2Settings
------------------------
	# http2相关的设置类： class Http2Settings


------------------------
constructor
------------------------
	public Http2Settings()
	public Http2Settings(JsonObject json)
	public Http2Settings(Http2Settings other)

------------------------
this
------------------------
	public long getHeaderTableSize()
	public Http2Settings setHeaderTableSize(long headerTableSize)
	public boolean isPushEnabled()
	public Http2Settings setPushEnabled(boolean pushEnabled)

	public long getMaxConcurrentStreams()
	public Http2Settings setMaxConcurrentStreams(long maxConcurrentStreams)
		* 按照 HTTP/2 RFC建议推荐值为 100

	public int getInitialWindowSize()
	public Http2Settings setInitialWindowSize(int initialWindowSize)
	public int getMaxFrameSize()
	public Http2Settings setMaxFrameSize(int maxFrameSize)
	public long getMaxHeaderListSize()
	public Http2Settings setMaxHeaderListSize(long maxHeaderListSize)
	public Map<Integer, Long> getExtraSettings()
	public Http2Settings setExtraSettings(Map<Integer, Long> settings)
	public Long get(int id)
	public Http2Settings set(int id, long value)
	public boolean equals(Object o)
	public int hashCode()
	public String toString()
	public JsonObject toJson()

------------------------
static
------------------------
	public static final long DEFAULT_HEADER_TABLE_SIZE = 4096;
	public static final boolean DEFAULT_ENABLE_PUSH = true;
	public static final long DEFAULT_MAX_CONCURRENT_STREAMS = 0xFFFFFFFFL;
	public static final int DEFAULT_INITIAL_WINDOW_SIZE = 65535;
	public static final int DEFAULT_MAX_FRAME_SIZE = 16384;
	public static final int DEFAULT_MAX_HEADER_LIST_SIZE = Integer.MAX_VALUE;
	public static final Map<Integer, Long> DEFAULT_EXTRA_SETTINGS = null;