import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * RSA
 * 
 * @author KevinBlandy
 *
 */
public class RSAUtils {

	// rsa算法
	public static final String RSA_ALGORITHM = "RSA";

	// 密钥长度
	private static final int KEY_SIZE = 2048;

	// 数字签名算法
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// 公钥key
	public static final String PUBLIC_KEY = "RSAPublicKey";

	// 私钥key
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	// 最大的加密明文长度
	public static final int MAX_ENCRYPT_BLOCK = 245;

	// 最大的解密密文长度
	public static final int MAX_DECRYPT_BLOCK = 256;

	// 初始化公钥和密钥
	public static Map<String, Key> initKey() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Key> keyMap = new HashMap<>();
		keyMap.put(PUBLIC_KEY, rsaPublicKey);
		keyMap.put(PRIVATE_KEY, rsaPrivateKey);
		return keyMap;
	}

	// 根据私钥获取cipher
	private static Cipher getCipherByPrivateKey(byte[] privateKey, int mode)throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(mode, _privateKey);
		return cipher;
	}

	// 根据公钥获取cipher
	private static Cipher getCipherByPublicKey(byte[] publicKey, int mode)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey _publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(mode, _publicKey);
		return cipher;
	}

	// 使用私钥加密数据
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey)
			throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	// 使用私钥解密数据
	public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey)throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	// 使用公钥加密数据
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey)throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	// 使用公钥解密数据
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	// 根据私钥生成数字签名
	public static byte[] signature(byte[] data, byte[] privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(_privateKey);
		signature.update(data);
		return signature.sign();
	}

	// 根据公寓校验数字签名
	public static boolean signatureVerify(byte[] data, byte[] publicKey, byte[] signature) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PublicKey _pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Signature _signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		_signature.initVerify(_pubKey);
		_signature.update(data);
		return _signature.verify(signature);
	}


	// 分段的加密解密
	
	private static byte[] segmentDoFinal(InputStream inputStream,Cipher cipher,int maxBlock) throws IOException, IllegalBlockSizeException, BadPaddingException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[maxBlock];
		int length;
		while ((length = inputStream.read(bytes)) != -1) {
			byte[] result = cipher.doFinal(bytes, 0, length);
			byteArrayOutputStream.write(result);
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	// 私钥加密
	public static byte[] encryptByPrivateKeySegment(InputStream inputStream, byte[] privateKey) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.ENCRYPT_MODE);
		return segmentDoFinal(inputStream, cipher, MAX_ENCRYPT_BLOCK);
	}

	// 私钥解密
	public static byte[] decryptByPrivateKeySegment(InputStream inputStream, byte[] privateKey) throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.DECRYPT_MODE);
		return segmentDoFinal(inputStream, cipher, MAX_DECRYPT_BLOCK);
	}
	
	// 公钥加密
	public static byte[] encryptByPublicKeySegment(InputStream inputStream, byte[] publicKey)throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,BadPaddingException, IllegalBlockSizeException, IOException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.ENCRYPT_MODE);
		return segmentDoFinal(inputStream, cipher, MAX_ENCRYPT_BLOCK);
	}

	// 公钥解密
	public static byte[] decryptByPublicKeySegment(InputStream inputStream, byte[] publicKey)throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException,IllegalBlockSizeException, NoSuchPaddingException, IOException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.DECRYPT_MODE);
		return segmentDoFinal(inputStream, cipher, MAX_DECRYPT_BLOCK);
	}
	
	private static void segmentDoFinal(InputStream inputStream,Cipher cipher,int maxBlock,OutputStream outputStream) throws IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] bytes = new byte[maxBlock];
		int length;
		while ((length = inputStream.read(bytes)) != -1) {
			byte[] result = cipher.doFinal(bytes, 0, length);
			outputStream.write(result);
		}
	}
	
	// 私钥加密
	public static void encryptByPrivateKeySegment(InputStream inputStream, byte[] privateKey,OutputStream outputStream) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.ENCRYPT_MODE);
		segmentDoFinal(inputStream, cipher, MAX_ENCRYPT_BLOCK,outputStream);
	}

	// 私钥解密
	public static void decryptByPrivateKeySegment(InputStream inputStream, byte[] privateKey,OutputStream outputStream) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = getCipherByPrivateKey(privateKey, Cipher.DECRYPT_MODE);
		segmentDoFinal(inputStream, cipher, MAX_DECRYPT_BLOCK,outputStream);
	}
	
	// 公钥加密
	public static void encryptByPublicKeySegment(InputStream inputStream, byte[] publicKey,OutputStream outputStream)throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,BadPaddingException, IllegalBlockSizeException, IOException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.ENCRYPT_MODE);
		segmentDoFinal(inputStream, cipher, MAX_ENCRYPT_BLOCK,outputStream);
	}

	// 公钥解密
	public static void decryptByPublicKeySegment(InputStream inputStream, byte[] publicKey,OutputStream outputStream)throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException,IllegalBlockSizeException, NoSuchPaddingException, IOException {
		Cipher cipher = getCipherByPublicKey(publicKey, Cipher.DECRYPT_MODE);
		segmentDoFinal(inputStream, cipher, MAX_DECRYPT_BLOCK,outputStream);
	}
}













