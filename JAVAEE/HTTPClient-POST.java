---------------------
常规POST请求			 |
---------------------	
	 public static void main(String[] args) throws Exception {
        // 创建Httpclient对象			打开浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求			输入地址
        HttpPost httpPost = new HttpPost("http://www.oschina.net/");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
---------------------
带参POST请求			|
---------------------
	public static void main(String[] args) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.oschina.net/search");
        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("scope", "project"));
        parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"utf-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }


---------------------
带参JSON的POST请求		|
---------------------
	public HttpResult doPostJson(String url,String json) throws ClientProtocolException, IOException{
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        
        httpPost.setConfig(this.config);
        if(json != null){
			/**
				JSON的本质就是字符串,所以这里使用: StringEntity
			*/
			StringEntity entity = new StringEntity(json,"utf-8");
			//设置请求头JSON
			entity.setContentType("application/json");
        	httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            HttpResult result = new HttpResult(
            			response.getStatusLine().getStatusCode(),
            			EntityUtils.toString(response.getEntity(),"utf-8"));
            return result;
        } finally {
            if (response != null) {
                response.close();
            }
        }
	}