package io.springboot.utils;

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
import javax.crypto.spec.SecretKeySpec;
/**
 * 
 * @author KevinBlandy
 *
 */
public class EncryptionUtils {
	
	/*****************		非对称密钥算法	**********************/
	
	//rsa算法
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 密钥长度,DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数,在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    
    //数字签名算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA"; 
    
    //公钥key
    public static final String PUBLIC_KEY = "RSAPublicKey";

    //私钥key
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 生成公钥和密钥
     * @return
     * @throws Exception
     */
	public static Map<String, Key> initKey() throws NoSuchAlgorithmException {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Key> keyMap = new HashMap<String, Key>();
        keyMap.put(PUBLIC_KEY, rsaPublicKey);
        keyMap.put(PRIVATE_KEY, rsaPrivateKey);
        return keyMap;
    }
	
	/**
	 * 私钥加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] source, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		//取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		//实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		//生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		//数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(source);
    }
	
	/**
	 * 公钥加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] source, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		//实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		//密钥材料转换
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		//产生公钥
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		//数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(source);
    }
	
	
	/**
	 * 私钥解密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] source, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		//取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		//实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		//生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		//数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(source);
	}
	
    /**
     * 公钥解密
     * @param source
     * @param key
     * @return byte
     */
    public static byte[] decryptByPublicKey(byte[] source, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        //密钥材料转换
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(source);
    }
    
    /**
     * 使用私钥对信息生成数字签名
     * @param source
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws InvalidKeyException 
     * @throws SignatureException 
     */
    public static byte[] signature(byte[] source, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException  {   
		// 构造PKCS8EncodedKeySpec对象   
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);   
		// rsa加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);   
		// 生成私钥对象
		PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);   
		// 用私钥对信息生成数字签名   
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   
		signature.initSign(_privateKey);   
		signature.update(source);   
		return signature.sign();   
    }   
    
    /**
     * 校验数字签名
     * @param source
     * @param publicKey
     * @param sign
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws InvalidKeyException 
     * @throws SignatureException 
     * @throws Exception
     */
    public static boolean signatureVerify(byte[] source, byte[] publicKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{   
		// 构造X509EncodedKeySpec对象   
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);   
		// KEY_ALGORITHM 指定的加密算法   
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);   
		// 取公钥匙对象   
		PublicKey _pubKey = keyFactory.generatePublic(x509EncodedKeySpec);   
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   
		signature.initVerify(_pubKey);   
		signature.update(source);   
		// 验证签名是否正常   
		return signature.verify(sign);   
    }   
    
	/*****************		对称加密	**********************/
    //aes算法
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
	 * AES加密
	 * @param source
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encryptByAES(byte[] source,byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES"); 
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);  
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);  
		return cipher.doFinal(source);  
	}
	
	/**
	 * AES解密
	 * @param source
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptByAES(byte[] source,byte[] key) throws InvalidKeyException,  NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");  
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);  
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);  
		return cipher.doFinal(source);  
	}
}
