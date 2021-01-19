
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * 
 * 从keystore导出证书，私钥，公钥
 * 
 */
public class ExportCert {

	// 导出证书 base64格式
	public static void exportCert(KeyStore keystore, String alias, String exportFile) throws Exception {
		Certificate cert = keystore.getCertificate(alias);
		Encoder encoder = Base64.getEncoder();
		String encoded = new String(encoder.encode(cert.getEncoded()));
		FileWriter fw = new FileWriter(exportFile);
		fw.write("-----BEGIN CERTIFICATE-----\r\n"); // 非必须
		fw.write(encoded);
		fw.write("\r\n-----END CERTIFICATE-----"); // 非必须
		fw.close();
	}

	// 得到KeyPair
	public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		Key key = keystore.getKey(alias, password);
		if (key instanceof PrivateKey) {
			Certificate cert = keystore.getCertificate(alias);
			PublicKey publicKey = cert.getPublicKey();
			return new KeyPair(publicKey, (PrivateKey) key);
		}
		return null;
	}

	// 导出私钥
	public static void exportPrivateKey(PrivateKey privateKey, String exportFile) throws Exception {
		Encoder encoder = Base64.getEncoder();
		String encoded = new String(encoder.encode(privateKey.getEncoded()));
		FileWriter fw = new FileWriter(exportFile);
		fw.write("—–BEGIN PRIVATE KEY—–\r\n"); // 非必须
		fw.write(encoded);
		fw.write("\r\n—–END PRIVATE KEY—–"); // 非必须
		fw.close();
	}

	// 导出公钥
	public static void exportPublicKey(PublicKey publicKey, String exportFile) throws Exception {
		Encoder encoder = Base64.getEncoder();
		String encoded = new String(encoder.encode(publicKey.getEncoded()));
		FileWriter fw = new FileWriter(exportFile);
		fw.write("—–BEGIN PUBLIC KEY—–\r\n"); // 非必须
		fw.write(encoded);
		fw.write("\r\n—–END PUBLIC KEY—–"); // 非必须
		fw.close();
	}

	public static void main(String args[]) throws Exception {

		// keysotre文件和密码
		String keysotreFile = "C:\\Users\\Administrator\\Desktop\\ssl\\server\\server.keystore";
		String password = "123456";
		
		// 加载keystore文件
		KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream(new File(keysotreFile)), password.toCharArray());

		// 证书的别名
		String alias = "server";
		String exportCertFile = "C:\\Users\\Administrator\\Desktop\\ssl\\server.cer";
		String exportPrivateFile = "C:\\Users\\Administrator\\Desktop\\ssl\\serverPrivateKey.txt";
		String exportPublicFile = "C:\\Users\\Administrator\\Desktop\\ssl\\serverPublicKey.txt";

		// 导出证书到指定目录
		ExportCert.exportCert(keystore, alias, exportCertFile);

		// 导出公钥和私钥到指定目录
		KeyPair keyPair = ExportCert.getKeyPair(keystore, alias, password.toCharArray());
		ExportCert.exportPrivateKey(keyPair.getPrivate(), exportPrivateFile);
		ExportCert.exportPublicKey(keyPair.getPublic(), exportPublicFile);
	}
}