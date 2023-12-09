
import java.io.ByteArrayInputStream;
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
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAHelper {

	public static void main(String[] args) throws Exception {

		// 生成 RSA 密钥对
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 公钥和私钥
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

		// 原文
		String raw = "Hello SpringDoc";

		// 加密、解密 ----------------------------------
		// 公钥加密
		ByteArrayOutputStream cipherOut = new ByteArrayOutputStream();
		encode((Key) rsaPublicKey, new ByteArrayInputStream(raw.getBytes()), cipherOut);
		System.out.println("公钥加密 - 密文：" + Base64.getEncoder().encodeToString(cipherOut.toByteArray()));

		// 私钥解密
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();
		decode((Key) rsaPrivateKey, new ByteArrayInputStream(cipherOut.toByteArray()), rawOut);
		System.out.println("私钥解密 - 原文：" + new String(rawOut.toByteArray()));

		// 私钥加密
		cipherOut = new ByteArrayOutputStream();
		encode((Key) rsaPrivateKey, new ByteArrayInputStream(raw.getBytes()), cipherOut);
		System.out.println("私钥加密 - 密文：" + Base64.getEncoder().encodeToString(cipherOut.toByteArray()));

		// 公钥解密
		rawOut = new ByteArrayOutputStream();
		decode((Key) rsaPublicKey, new ByteArrayInputStream(cipherOut.toByteArray()), rawOut);
		System.out.println("公钥解密 - 原文：" + new String(rawOut.toByteArray()));

		// 签名 -------------------------------
		// 计算签名
		byte[] sign = sign(rsaPrivateKey, "SHA256withRSA", new ByteArrayInputStream(raw.getBytes()));
		System.out.println("私钥签名：" + Base64.getEncoder().encodeToString(sign));

		// 校验签名
		boolean validate = validate(rsaPublicKey, "SHA256withRSA", new ByteArrayInputStream(raw.getBytes()), sign);
		System.out.println("公钥校验：" + validate);
	}

	/**
	 * 加密
	 * 
	 * @param key KEY
	 * @param in  输入参数
	 * @param out 输出加密后的密文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static void encode(Key key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		// 最大的加密明文长度
		final int MAX_ENCRYPT_BLOCK = 245;

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] buffer = new byte[MAX_ENCRYPT_BLOCK];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(cipher.doFinal(buffer, 0, len));
		}
	}

	/**
	 * 解密
	 * 
	 * @param key KEY
	 * @param in  输入参数
	 * @param out 输出解密后的原文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static void decode(Key key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

		// 最大的加密明文长度
		final int MAX_DECRYPT_BLOCK = 256;

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] buffer = new byte[MAX_DECRYPT_BLOCK];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(cipher.doFinal(buffer, 0, len));
		}
	}

	/**
	 * 私钥签名
	 * 
	 * @param key       私钥
	 * @param algorithm 算法
	 * @param in        输入数据
	 * @return 签名
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws SignatureException
	 */
	public static byte[] sign(RSAPrivateKey key, String algorithm, InputStream in)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException, SignatureException {
		Signature signature = Signature.getInstance(algorithm);
		signature.initSign(key);
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			signature.update(buffer, 0, len);
		}
		return signature.sign();
	}

	/**
	 * 公钥验签
	 * 
	 * @param key       公钥
	 * @param algorithm 算法
	 * @param in        输入数据
	 * @param sign      签名
	 * @return 签名是否符合
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static boolean validate(RSAPublicKey key, String algorithm, InputStream in, byte[] sign)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature signature = Signature.getInstance(algorithm);
		signature.initVerify(key);
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			signature.update(buffer, 0, len);
		}
		return signature.verify(sign);
	}

	/**
	 * 从证书解析出公钥
	 * 
	 * @param pem
	 * @return
	 * @throws CertificateException
	 */
	public static PublicKey parsePublicKeyFromCertificate(InputStream pem) throws CertificateException {
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		// 获取证书链
		// Collection<? extends Certificate> certificates = certFactory.generateCertificates(pem);
		Certificate certificate = certFactory.generateCertificate(pem);
		return certificate.getPublicKey();
	}
}