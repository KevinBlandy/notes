--------------------------
UriUtils				  |
--------------------------
	# Uri相关的工具类

	String encodeScheme(String scheme, String encoding) 
	String encodeScheme(String scheme, Charset charset) 

	String encodeAuthority(String authority, String encoding)
	String encodeAuthority(String authority, Charset charset)
	
	String encodeUserInfo(String userInfo, String encoding)
	String encodeUserInfo(String userInfo, Charset charset)

	String encodeHost(String host, String encoding)
	String encodeHost(String host, Charset charset)

	String encodePort(String port, String encoding)
	String encodePort(String port, Charset charset)

	String encodePath(String path, String encoding)
	String encodePath(String path, Charset charset)

	String encodePathSegment(String segment, String encoding)
	String encodePathSegment(String segment, Charset charset)

	String encodeQuery(String query, String encoding)
	String encodeQuery(String query, Charset charset)

	String encodeQueryParam(String queryParam, String encoding)
	String encodeQueryParam(String queryParam, Charset charset)

	String encodeFragment(String fragment, String encoding)
	String encodeFragment(String fragment, Charset charset)

	String encode(String source, String encoding)
	String encode(String source, Charset charset)

	Map<String, String> encodeUriVariables(Map<String, ?> uriVariables)
	Object[] encodeUriVariables(Object... uriVariables)

	String decode(String source, String encoding)
	String decode(String source, Charset charset)

	String extractFileExtension(String path)

