----------------------------
责任链设计模式				|
----------------------------
	* 不多解释, HttpServetFilter

----------------------------
demo						|
----------------------------
//定义抽象拦截器
public abstract class Interceptor {
	//下一个拦截器
	protected Interceptor next;
	public Interceptor() {}
	public Interceptor(String name, Interceptor next) {
		super();
		this.next = next;
	}
	public Interceptor getNext() {
		return next;
	}
	public void setNext(Interceptor next) {
		this.next = next;
	}
	//执行拦截的方法
	public abstract void chain(HttpRequest request);
}

//请求体类型拦截器
public class ContentTypeInterceptor extends Interceptor{
	@Override
	public void chain(HttpRequest request) {
		if(request.getContentType().equals("application/json")) {
			//是json就下一个处理器进行处理
			next.chain(request);
		}else {
			System.out.println("ContentType必须是json");
		}
	}
}

//ip限制拦截器
public class RemoteIpInterceptor extends Interceptor{
	@Override
	public void chain(HttpRequest request) {
		if(request.getRemoteIp().equals("0.0.0.0")) {
			//ip是0.0.0.0就进行下一个处理器处理
			getNext().chain(request);
		}else {
			System.out.println("ip必须为 0.0.0.0");
		}
	}
}

//http request封装
public class HttpRequest {
	private String contentType;
	private String remoteIp;
	public HttpRequest() {}
	public HttpRequest(String contentType, String remoteIp) {
		super();
		this.contentType = contentType;
		this.remoteIp = remoteIp;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
}

public static void main(String[] args) {
	//创建第一个拦截器
	ContentTypeInterceptor contentTypeInterceptor = new ContentTypeInterceptor();
	//创建第二个拦截器
	contentTypeInterceptor.setNext(new RemoteIpInterceptor());
	contentTypeInterceptor.chain(new HttpRequest("application/json","120.0.0.1"));
}



----------------------------
python 实现					|
----------------------------


class ApplicationFilterChain(object):
    def __init__(self,filters):
        self.filters = filters
        self.position = 0
        self.size = len(filters)
        
    def doFilter(self,request,response):
        if self.position < self.size:
            position = self.position
            self.position += 1
            self.filters[position].doFilter(request,response,self)
            

class Filter(object):
    def __init__(self,name):
        self.name = name
    def doFilter(self,request,response,chain):
        print("%s 执行 %s %s"%(self.name,request,response ));
        if self.name != '5':
            chain.doFilter(request, response)

filters = [Filter("5"),Filter("7"),Filter("9"),Filter("2")]

chain = ApplicationFilterChain(filters)

chain.doFilter("request", "response")