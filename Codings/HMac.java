import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


/**
 * 
 * Hamc
 * 
 */
public class Hamc {

	public static byte[] sha512(byte[] content, byte[] secret) {
		try {
			Mac sha512Hmac = Mac.getInstance("HmacSHA512");
			SecretKeySpec keySpec = new SecretKeySpec(secret, "HmacSHA512");
			sha512Hmac.init(keySpec);
			return sha512Hmac.doFinal(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String sha512Hex(byte[] content, byte[] secret) {
		return Hex.encodeHexString(sha512(content, secret));
	}
}
