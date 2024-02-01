package app.test;



import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ApplicationMainTest {
	
	public static void main(String[] args) throws Exception {

		// 生成 RSA 密钥对
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 公钥和私钥
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

		byte[] publicKeyBytes = rsaPublicKey.getEncoded();
		byte[] rsaPrivateKeyBytes = rsaPrivateKey.getEncoded();
		
		System.out.println("生成的公钥：" + Base64.getEncoder().encodeToString(publicKeyBytes));
		System.out.println("生成的私钥：" + Base64.getEncoder().encodeToString(rsaPrivateKeyBytes));
		
		
		/**
		 * 解析 RSA 公钥和私钥
		 */
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		// 公钥使用 X509EncodedKeySpec 格式
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		RSAPublicKey restoredPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		
		// 私钥使用 PKCS8EncodedKeySpec 格式
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKeyBytes);
		RSAPrivateKey restoredPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		
		byte[] restoredPublicKeyBytes = restoredPublicKey.getEncoded();
		byte[] restoredPrivateKeyBytes = restoredPrivateKey.getEncoded();
		
		System.out.println("读取的公钥：" + Base64.getEncoder().encodeToString(restoredPublicKeyBytes));
		System.out.println("读取的私钥：" + Base64.getEncoder().encodeToString(restoredPrivateKeyBytes));
	}
}