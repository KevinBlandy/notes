----------------------------------------
ssl双向认证								|
----------------------------------------
	# 一般Web应用都是采用SSL单向认证的
		* 原因很简单,用户数目广泛,且无需在通讯层对用户身份进行验证,一般都在应用逻辑层来保证用户的合法登入

	# 企业应用对接,可能会要求对客户端(相对而言)做身份验证
		* 这时就需要做SSL双向认证
		* SSL双向认证,要求客户端和服务器都要有自己的证书,并且互相导入了公钥

----------------------------------------
证书生成								|
----------------------------------------
	# 生成证书
		 keytool -genkey -alias [公钥别名] -validity [有效期(天)] -keystore [keystore文件] -keyalg [算法(RSA)]

	# 导出公钥
		keytool -export -alias [公钥别名] -file [公钥文件] -keystore [keystore文件] -storepass [keystore密码]

	# 导入公钥
		keytool -import -alias [公钥别名] -keystore [keystore文件] -file [公钥文件] -keypass [公钥密码] -storepass [keystore密码]
	
	# 删除导入的公钥
		keytool -delete -alias [公钥别名] -keystore [keystore文件]

	# 查看已经导入的公钥列表
		keytool -list -v -keystore [keystore文件] -storepass [keystore密码]

	# 演示
		1 服务端生成证书,并且导出公钥
			keytool -genkey -alias server -validity 100 -keystore server.keystore -keyalg RSA
			
			keytool -export -alias server -file server.cer -keystore server.keystore
		
		2 客户端生成证书
			keytool -genkey -alias client -validity 100 -keystore client.keystore -keyalg RSA
		
		3 客户端导入服务端公钥到证书
			keytool -import -trustcacerts -alias server -file server.cer -keystore client.keystore
		
		4 客户端导出公钥
			keytool -export -alias client -file client.cer -keystore client.keystore
		
		5 服务端导入客户端公钥到证书
			keytool -import -trustcacerts -alias client -file client.cer -keystore server.keystore
		
		* 客户端从证书删除已经导入的公钥
			keytool -delete -alias server -keystore client.keystore
			
		* 服务端从证书删除已经导入的公钥
			keytool -delete -alias client -keystore server.keystore
	

----------------------------------------
SSLContextFactory						|
----------------------------------------

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLContextFactory {

	// 协议
	private static final String PROTOCOL = "TLS";		//SSLv3
	
	// keystore 类型
	private static final String KEY_KEYSTORE_TYPE = "JKS";
	
	// 算法
	private static final String ALGORITHM = "SunX509";

	public static SSLContext getSslContext(String keystore, String password) throws NoSuchAlgorithmException,UnrecoverableKeyException, KeyStoreException, CertificateException, IOException, KeyManagementException {
		
		SSLContext sslContext = SSLContext.getInstance(PROTOCOL);

		KeyManager[] keyManagers = getKeyManagers(Files.newInputStream(Paths.get(keystore)), password);
		
		TrustManager[] trustManagers = getTrustManagers(Files.newInputStream(Paths.get(keystore)), password);

		sslContext.init(keyManagers, trustManagers, null);
		
		return sslContext;
	}

	private static KeyManager[] getKeyManagers(InputStream keystore, String password)throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,IOException {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(ALGORITHM);
		KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
		keyStore.load(keystore, password.toCharArray());
		keyManagerFactory.init(keyStore, password.toCharArray());
		KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
		return keyManagers;
	}

	private static TrustManager[] getTrustManagers(InputStream keystore, String password)throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(ALGORITHM);
		KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
		keyStore.load(keystore, password.toCharArray());
		trustManagerFactory.init(keyStore);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		return trustManagers;
	}
}



----------------------------------------
服务端									|
----------------------------------------
	SSLEngine engine = SSLContextFactory.getSslContext("server.keystore","123456").createSSLEngine();
	engine.setUseClientMode(false);
	engine.setNeedClientAuth(true);  // 需要验证客户端
	socketChannel.pipeline().addLast(new SslHandler(engine));

----------------------------------------
客户端									|
----------------------------------------
	SSLEngine engine = SSLContextFactory.getSslContext("client.keystore","123456").createSSLEngine();
	engine.setUseClientMode(true);
	socketChannel.pipeline().addLast(new SslHandler(engine));

----------------------------------------
监听握手是否完成						|
----------------------------------------
	# 监听通道激活事件,在里面获取到SslHandler的handshakeFuture,并且设置监听

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(new GenericFutureListener<Future<? super Channel>>() {
			@Override
			public void operationComplete(Future<? super Channel> future) throws Exception {
				if(!future.isSuccess()) {
					// 握手失败
					future.cause().printStackTrace();
				}
			}
		});
	}