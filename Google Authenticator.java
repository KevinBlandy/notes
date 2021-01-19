-----------------------------
Google Authenticator
-----------------------------
	# Google的二步验证
		* 30秒会刷新出一个新的动态口令

	# Github
		https://github.com/google/google-authenticator

	# 动态口令生成口令的算法, 主要分为两种
		HOTP
		TOTP(国内主要使用TOTP, 因为时间同步并不是太难的事)
			*   Time-Based One-Time Password Algorithm
		
	
	# 二维码内容格样式:
		otpauth://totp/{issuer}:{account}?secret={secret}&issuer={issuer}
			issuer		提供服务的组织/企业(如果前后都有，那么要保持一致)
			account		账户
			secret		密钥
	
		otpauth://totp/springboot:747692844@qq.com?secret=vwdjsowyebnidjjiw5uz3ygwkqjyiha3&issuer=springboot

-----------------------------
Java接入
-----------------------------
	# Mave
		<!-- https://mvnrepository.com/artifact/commons-codec/commons-codec -->
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.14</version>
		</dependency>
		<dependency>
			<groupId>com.google.zxing</groupId>
			<artifactId>core</artifactId>
			<version>3.4.0</version>
		</dependency>
		<dependency>
			<groupId>com.google.zxing</groupId>
			<artifactId>javase</artifactId>
			<version>3.4.0</version>
		</dependency>
	
	# GoogleAuthenticatorUtils
		import java.io.IOException;
		import java.io.OutputStream;
		import java.io.UnsupportedEncodingException;
		import java.net.URLEncoder;
		import java.security.SecureRandom;
		import java.time.LocalDateTime;
		import java.time.format.DateTimeFormatter;

		import org.apache.commons.codec.binary.Base32;
		import org.apache.commons.codec.binary.Hex;

		import com.google.zxing.BarcodeFormat;
		import com.google.zxing.MultiFormatWriter;
		import com.google.zxing.WriterException;
		import com.google.zxing.client.j2se.MatrixToImageWriter;
		import com.google.zxing.common.BitMatrix;

		public class GoogleAuthenticatorUtils {
			
			/**
			 * 生成密钥
			 * @return
			 */
			public static String getRandomSecretKey() {
				SecureRandom random = new SecureRandom();
				byte[] bytes = new byte[20];
				random.nextBytes(bytes);
				Base32 base32 = new Base32();
				return base32.encodeToString(bytes).toLowerCase();
			}
			
			
			/**
			 * 根据key计算出动态口令
			 * @param secretKey
			 * @return
			 */
			public static String getTOTPCode(String secretKey) {
				Base32 base32 = new Base32();
				byte[] bytes = base32.decode(secretKey);
				String hexKey = Hex.encodeHexString(bytes);
				long time = (System.currentTimeMillis() / 1000) / 30;
				String hexTime = Long.toHexString(time);
				return TOTP.generateTOTP(hexKey, hexTime, "6");
			}
			
			/**
			 * 生成条形码
			 * @param secretKey
			 * @param account
			 * @param issuer
			 * @return
			 */
			public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
				try {
					return "otpauth://totp/"
						+ URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
						+ "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
						+ "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException(e);
				}
			}
			
			
			/**
			 * 生成二维码
			 * @param barCode
			 * @param outputStream
			 * @param height
			 * @param width
			 * @throws WriterException
			 * @throws IOException
			 */
			
			public static void createQRCode(String barCode, OutputStream  outputStream, int height, int width) throws WriterException, IOException {
				BitMatrix matrix = new MultiFormatWriter().encode(barCode, BarcodeFormat.QR_CODE, width, height);
				MatrixToImageWriter.writeToStream(matrix, "png", outputStream);
			}
				
			public static void main(String[] args) throws WriterException, IOException {
				String secretKey = getRandomSecretKey();
				System.out.println(secretKey);
				String uri = getGoogleAuthenticatorBarCode(secretKey, "747692844@qq.com", "KevinBlandy");
				System.out.println(uri);
				String lastCode = null;
				while (true) {
					String code = getTOTPCode(secretKey);
					if (!code.equals(lastCode)) {
						 System.out.println(code + " " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
					}
					lastCode = code;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {};
				}
			}
		}
	
	# TOTP
		import java.lang.reflect.UndeclaredThrowableException;
		import java.security.GeneralSecurityException;
		import java.text.DateFormat;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import javax.crypto.Mac;
		import javax.crypto.spec.SecretKeySpec;
		import java.math.BigInteger;
		import java.util.TimeZone;


		/**
		 * This is an example implementation of the OATH
		 * TOTP algorithm.
		 * Visit www.openauthentication.org for more information.
		 *
		 * @author Johan Rydell, PortWise, Inc.
		 */

		public class TOTP {

			private TOTP() {}

			/**
			 * This method uses the JCE to provide the crypto algorithm.
			 * HMAC computes a Hashed Message Authentication Code with the
			 * crypto hash algorithm as a parameter.
			 *
			 * @param crypto: the crypto algorithm (HmacSHA1, HmacSHA256,
			 *                             HmacSHA512)
			 * @param keyBytes: the bytes to use for the HMAC key
			 * @param text: the message or text to be authenticated
			 */

			private static byte[] hmac_sha(String crypto, byte[] keyBytes,
					byte[] text){
				try {
					Mac hmac;
					hmac = Mac.getInstance(crypto);
					SecretKeySpec macKey =
						new SecretKeySpec(keyBytes, "RAW");
					hmac.init(macKey);
					return hmac.doFinal(text);
				} catch (GeneralSecurityException gse) {
					throw new UndeclaredThrowableException(gse);
				}
			}


			/**
			 * This method converts a HEX string to Byte[]
			 *
			 * @param hex: the HEX string
			 *
			 * @return: a byte array
			 */

			private static byte[] hexStr2Bytes(String hex){
				// Adding one byte to get the right conversion
				// Values starting with "0" can be converted
				byte[] bArray = new BigInteger("10" + hex,16).toByteArray();

				// Copy all the REAL bytes, not the "first"
				byte[] ret = new byte[bArray.length - 1];
				for (int i = 0; i < ret.length; i++)
					ret[i] = bArray[i+1];
				return ret;
			}

			private static final int[] DIGITS_POWER
			// 0 1  2   3    4     5      6       7        8
			= {1,10,100,1000,10000,100000,1000000,10000000,100000000 };


			/**
			 * This method generates a TOTP value for the given
			 * set of parameters.
			 *
			 * @param key: the shared secret, HEX encoded
			 * @param time: a value that reflects a time
			 * @param returnDigits: number of digits to return
			 *
			 * @return: a numeric String in base 10 that includes
			 *              {@link truncationDigits} digits
			 */

			public static String generateTOTP(String key,
					String time,
					String returnDigits){
				return generateTOTP(key, time, returnDigits, "HmacSHA1");
			}


			/**
			 * This method generates a TOTP value for the given
			 * set of parameters.
			 *
			 * @param key: the shared secret, HEX encoded
			 * @param time: a value that reflects a time
			 * @param returnDigits: number of digits to return
			 *
			 * @return: a numeric String in base 10 that includes
			 *              {@link truncationDigits} digits
			 */

			public static String generateTOTP256(String key,
					String time,
					String returnDigits){
				return generateTOTP(key, time, returnDigits, "HmacSHA256");
			}


			/**
			 * This method generates a TOTP value for the given
			 * set of parameters.
			 *
			 * @param key: the shared secret, HEX encoded
			 * @param time: a value that reflects a time
			 * @param returnDigits: number of digits to return
			 *
			 * @return: a numeric String in base 10 that includes
			 *              {@link truncationDigits} digits
			 */

			public static String generateTOTP512(String key,
					String time,
					String returnDigits){
				return generateTOTP(key, time, returnDigits, "HmacSHA512");
			}


			/**
			 * This method generates a TOTP value for the given
			 * set of parameters.
			 *
			 * @param key: the shared secret, HEX encoded
			 * @param time: a value that reflects a time
			 * @param returnDigits: number of digits to return
			 * @param crypto: the crypto function to use
			 *
			 * @return: a numeric String in base 10 that includes
			 *              {@link truncationDigits} digits
			 */

			public static String generateTOTP(String key,
					String time,
					String returnDigits,
					String crypto){
				int codeDigits = Integer.decode(returnDigits).intValue();
				String result = null;

				// Using the counter
				// First 8 bytes are for the movingFactor
				// Compliant with base RFC 4226 (HOTP)
				while (time.length() < 16 )
					time = "0" + time;

				// Get the HEX in a Byte[]
				byte[] msg = hexStr2Bytes(time);
				byte[] k = hexStr2Bytes(key);
				byte[] hash = hmac_sha(crypto, k, msg);

				// put selected bytes into result int
				int offset = hash[hash.length - 1] & 0xf;

				int binary =
					((hash[offset] & 0x7f) << 24) |
					((hash[offset + 1] & 0xff) << 16) |
					((hash[offset + 2] & 0xff) << 8) |
					(hash[offset + 3] & 0xff);

				int otp = binary % DIGITS_POWER[codeDigits];

				result = Integer.toString(otp);
				while (result.length() < codeDigits) {
					result = "0" + result;
				}
				return result;
			}

			public static void main(String[] args) {
				// Seed for HMAC-SHA1 - 20 bytes
				String seed = "3132333435363738393031323334353637383930";
				// Seed for HMAC-SHA256 - 32 bytes
				String seed32 = "3132333435363738393031323334353637383930" +
				"313233343536373839303132";
				// Seed for HMAC-SHA512 - 64 bytes
				String seed64 = "3132333435363738393031323334353637383930" +
				"3132333435363738393031323334353637383930" +
				"3132333435363738393031323334353637383930" +
				"31323334";
				long T0 = 0;
				long X = 30;
				long testTime[] = {59L, 1111111109L, 1111111111L,
						1234567890L, 2000000000L, 20000000000L};

				String steps = "0";
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("UTC"));

				try {
					System.out.println(
							"+---------------+-----------------------+" +
					"------------------+--------+--------+");
					System.out.println(
							"|  Time(sec)    |   Time (UTC format)   " +
					"| Value of T(Hex)  |  TOTP  | Mode   |");
					System.out.println(
							"+---------------+-----------------------+" +
					"------------------+--------+--------+");

					for (int i=0; i<testTime.length; i++) {
						long T = (testTime[i] - T0)/X;
						steps = Long.toHexString(T).toUpperCase();
						while (steps.length() < 16) steps = "0" + steps;
						String fmtTime = String.format("%1$-11s", testTime[i]);
						String utcTime = df.format(new Date(testTime[i]*1000));
						System.out.print("|  " + fmtTime + "  |  " + utcTime +
								"  | " + steps + " |");
						System.out.println(generateTOTP(seed, steps, "8",
						"HmacSHA1") + "| SHA1   |");
						System.out.print("|  " + fmtTime + "  |  " + utcTime +
								"  | " + steps + " |");
						System.out.println(generateTOTP(seed32, steps, "8",
						"HmacSHA256") + "| SHA256 |");
						System.out.print("|  " + fmtTime + "  |  " + utcTime +
								"  | " + steps + " |");
						System.out.println(generateTOTP(seed64, steps, "8",
						"HmacSHA512") + "| SHA512 |");

						System.out.println(
								"+---------------+-----------------------+" +
						"------------------+--------+--------+");
					}
				}catch (final Exception e){
					System.out.println("Error : " + e);
				}
			}
		}

			
