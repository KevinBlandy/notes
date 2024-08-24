package chatty.common.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hash {
	
	public static String sha256Hex(String content) {
		return HexFormat.of().formatHex(sha256(content.getBytes()));
	}
	
	public static byte[] sha256(byte[] content) {
		Objects.requireNonNull(content);
		try {
			return MessageDigest.getInstance("SHA256").digest(content);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] hmacSHA256(byte[] key, byte[] content) {
		try {
			 Mac mac = Mac.getInstance("HmacSHA256");
	         mac.init(new SecretKeySpec(key, "HmacSHA256"));
	         return mac.doFinal(content);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
}


