
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class PasswordEncoder {

	public static final int MAX_KEY_SIZE = 64;

	/**
	 * 随机盐对密码进行随机Hash
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		Objects.requireNonNull(password);
		byte[] key = new byte[MAX_KEY_SIZE];
		new SecureRandom().nextBytes(key);
		return encode(key, password);
	}

	/**
	 * 盐+密码进行hash，把盐放在结果头部
	 * 
	 * @param key
	 * @param password
	 * @return
	 */
	private static String encode(byte[] key, String password) {
		try {

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(key);
			messageDigest.update(password.getBytes(StandardCharsets.UTF_8));

			byte[] sha256 = messageDigest.digest();

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(key.length + sha256.length);
			byteArrayOutputStream.write(key);
			byteArrayOutputStream.write(sha256);

			return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

		} catch (NoSuchAlgorithmException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 校验密码是否符合
	 * 
	 * @param password
	 * @param cipher
	 * @return
	 */
	public static boolean match(String password, String cipher) {
		byte[] result = Base64.getDecoder().decode(cipher);
		byte[] key = new byte[MAX_KEY_SIZE];
		System.arraycopy(result, 0, key, 0, key.length);
		return encode(key, password).equals(cipher);
	}
}
