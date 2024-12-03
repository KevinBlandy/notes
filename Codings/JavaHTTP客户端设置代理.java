import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyTests {

	public static void main(String[] args) throws Exception {

		var httpClient = HttpClient.newBuilder()
			.proxy(new ProxySelector() {
				@Override
				public List<Proxy> select(URI uri) {
					return List.of(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10808)));
				}
				@Override
				public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
					log.error("connectFailed");
				}
			})
		.build();
		
		var content = httpClient
					.send(HttpRequest.newBuilder(new URI("https://abc.com?from=SOCKS"))
					.GET()
					.build(), BodyHandlers.ofString());
		
		System.out.println(content);
	}
}