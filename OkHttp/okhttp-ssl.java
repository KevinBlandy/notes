
--------------------
okhttp官方的demo代码|
--------------------
	TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
	trustManagerFactory.init((KeyStore) null);  //使用默认的证书

	TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

	if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
		throw new IllegalStateException("Unexpected trust managers:" + Arrays.toString(trustManagers));
	}

	X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

	SSLContext sslContext = SSLContext.getInstance("TLS");
	sslContext.init(null, new TrustManager[] { trustManager }, null);

	SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

	OkHttpClient client = new OkHttpClient.Builder()
				   .sslSocketFactory(sslSocketFactory, trustManager)
				   .build();


--------------------
okhttp自己实践的	|
--------------------
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

public class HttpClient {

    // TLS/SSLv3
    private static final String PROTOCOL = "TLS";		//SSLv3

    // JKS/PKCS12
    private static final String KEY_KEYSTORE_TYPE = "JKS";

    private static final String SUN_X_509 = "SunX509";

    private static SSLContext getSslContext(KeyManager[] keyManagers, TrustManager[] trustManagers) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException, KeyManagementException, IOException {
        SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        return sslContext;
    }

    private static KeyManager[] getKeyManagers(InputStream keystore, String password)throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,IOException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(SUN_X_509);
        KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
        keyStore.load(keystore, password.toCharArray());
        keyManagerFactory.init(keyStore, password.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        return keyManagers;
    }

    private static TrustManager[] getTrustManagers(InputStream keystore, String password)throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(SUN_X_509);
        KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
        keyStore.load(keystore, password.toCharArray());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }

    public static void main(String[] args)throws Exception {

        // 客户端证书的路径
        String keystorePath = "C:\\Users\\Administrator\\Desktop\\key\\client\\client.keystore";

        // keystore的密码
        String keystorePassword = "123456";

        KeyManager[] keyManagers = getKeyManagers(Files.newInputStream(Paths.get(keystorePath)),keystorePassword);
        TrustManager[] trustManagers = getTrustManagers(Files.newInputStream(Paths.get(keystorePath)),keystorePassword);
        SSLContext sslContext = getSslContext(keyManagers,trustManagers);

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                .hostnameVerifier((host,sslSession) -> {
                    // 校验证书域名
                    return true;
                })
                .build();

        Request request = new Request.Builder()
                .url("https://localhost:443")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }
}
