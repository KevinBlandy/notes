public class HttpUtils {

	private static final Charset CHARSET = Charset.forName("UTF-8");        //encoding

	/**
	 * 执行GET请求
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static HttpMessage doGet(String url, Map<String,String> param, Header... headers) throws URISyntaxException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if(!GeneralUtils.isEmpty(param)){
				for(Map.Entry<String,String> entity :  param.entrySet()){
					builder.setParameter(entity.getKey(),entity.getValue());
				}
			}
			HttpGet httpGet = new HttpGet(builder.build());
            if(!GeneralUtils.isEmpty(headers)){
                httpGet.setHeaders(headers);
            }
			response = httpClient.execute(httpGet);
			return new HttpMessage(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(),CHARSET)) ;
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpClient != null){
				httpClient.close();
			}
		}
	}
	
	/**
	 * 执行POST请求
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static HttpMessage doPost(String url,Map<String,String> param, Header... headers) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if(!GeneralUtils.isEmpty(param)){
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				for(Map.Entry<String,String> entity :  param.entrySet()){
					parameters.add(new BasicNameValuePair(entity.getKey(),entity.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,CHARSET);
				httpPost.setEntity(formEntity);
			}
            if(!GeneralUtils.isEmpty(headers)){
                httpPost.setHeaders(headers);
            }
			response = httpClient.execute(httpPost);
			return new HttpMessage(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(),CHARSET)) ;
		} finally {
			if (response != null) {
				response.close();
			}
			if(httpClient != null){
				httpClient.close();
			}
		}
	}
	
	/**
	 * 执行POST请求,请求体为JSON字符串
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpMessage doPost(String url, String json, Header... headers) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			if(!GeneralUtils.isEmpty(json)){
				StringEntity entity = new StringEntity(json, CHARSET);
				entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpPost.setEntity(entity);
			}
			if(!GeneralUtils.isEmpty(headers)){
                httpPost.setHeaders(headers);
			}
			response = httpClient.execute(httpPost);
			return new HttpMessage(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(),CHARSET)) ;
		} finally {
			if (response != null) {
				response.close();
			}
			if(httpClient != null){
				httpClient.close();
			}
		}
	}
}
