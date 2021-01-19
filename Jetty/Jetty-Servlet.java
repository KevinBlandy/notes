1,依赖
	<dependencies>
		<dependency>
		    <groupId>javax.servlet</groupId>
		    <artifactId>javax.servlet-api</artifactId>
		    <version>4.0.0</version>
		</dependency>
		<dependency>
		    <groupId>org.eclipse.jetty</groupId>
		    <artifactId>jetty-server</artifactId>
		    <version>9.4.7.v20170914</version>
		</dependency>
		<dependency>
		    <groupId>org.eclipse.jetty</groupId>
		    <artifactId>jetty-servlet</artifactId>
		    <version>9.4.7.v20170914</version>
		</dependency>
	</dependencies>


2,代码
	import org.eclipse.jetty.server.Server;
	import org.eclipse.jetty.servlet.ServletContextHandler;
	import com.feather.web.servlet.HelloServlet;

	public class Application {
		public static void main(String[] args) {
			//创建服务器,监听端口
			Server server = new Server(8000);
			
			//创建 ServletContextHandler
			ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
			servletContextHandler.setContextPath("/");
			
			//添加Servlet.class 到 ServletContextHandler
			servletContextHandler.addServlet(HelloServlet.class, "/hello");
			
			//添加 ServletContextHandler 到 server
			server.setHandler(servletContextHandler);
			
			try {
				server.start();		//启动服务
				server.join();		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}