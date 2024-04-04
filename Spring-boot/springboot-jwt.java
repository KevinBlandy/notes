---------------------
jwt
---------------------
	# Json Web Token
		https://jwt.io/
		https://github.com/jwtk/jjwt
	
	# JWT������������
		Header ͷ��(������������Ƶ�Ԫ����, ���Ұ���ǩ����/������㷨������)
			{ 
				"alg": "HS256",
				"typ": "JWT"
			} 

			* ͷ������������Ϣ, token���ͺͲ��õļ����㷨
			* ���ܵ��㷨ͨ��ʹ��: HMAC, SHA256, MD5
			* ���м��������õ�ͷ
				cty
				kid
			
			* ���������˽ӿ�:PublicClaims ��

		Payload ����(������, ��Ϣ����)

			{
			  "sub": "1234567890",			// ��׼����
			  "name": "John Doe",			// ˽�е�����
			  "admin": true					// ˽������
			}
			
			* Payload ����Ҳ��һ�� JSON ����, �������ʵ����Ҫ���ݵ�����
			* JWT �涨��7���ٷ��ֶ�, ���������˽ӿ�:PublicClaims ��
					iss (issuer)ǩ����
					exp (expiration time)����ʱ��
					sub (subject)����					// �ȸ� Auth ������ֶη��û� ID
					aud (audience)����
					nbf (Not Before)��Чʱ��
					iat (Issued At)ǩ��ʱ��
					jti (JWT ID)���

			* ��������������ֶ���˽���ֶ�
			* JWT Ĭ���ǲ����ܵ�, �κ��˶����Զ���, ���Բ�Ҫ��������Ϣ�����������

			
		Signature ǩ��/ǩ֤
			* ���������Ҫbase64���ܺ��header��base64���ܺ��payloadʹ��,
			* ������ɵ��ַ���, Ȼ��ͨ��header�������ļ��ܷ�ʽ���м���secret��ϼ���, Ȼ��͹�����jwt�ĵ�������
			* ��Կsecret�Ǳ����ڷ���˵�, ����˻���������Կ��������token�ͽ�����֤, ������Ҫ������
	
	# Header
		Authorization: Bearer <token>

---------------------
jwt - springboot
---------------------
	# Maven
		<!-- https://mvnrepository.com/artifact/com.auth0/java-jwt -->
		<dependency>
			<groupId>com.auth0</groupId>
			<artifactId>java-jwt</artifactId>
			<version>3.10.1</version>
		</dependency>
	
	# ǩ��Token
		// ���һ����ݺ��ʱ���
		long expiresTimestamp = LocalDateTime.now().plusWeeks(1).toEpochSecond(ZoneOffset.UTC);
		
		// ʹ��˽Կ���������㷨
		Algorithm  algorithm = Algorithm.HMAC256("HellWorld");
		
		String token = JWT.create()
			.withHeader(Map.ofEntries(Map.entry("alg", "HS256"), Map.entry("typ", "JWT")))
			
			.withClaim("name", "KevinBlandy")				// ���һ�����߶���Զ�����Ϣ
			.withClaim("id", 1000L)
			
			.withIssuer("springboot��������")				// ǩ����
			.withExpiresAt(new Date(expiresTimestamp))		// ����ʱ��
			.withSubject("") 								// ����
			.withAudience("1", "2", "n") 					// ����
			.withNotBefore(new Date()) 						// ��Чʱ��
			.withIssuedAt(new Date()) 						// ǩ��ʱ��
			.withJWTId("123456") 							// ���
			.sign(algorithm);
	
	# Token У��
		
		// DecodedJWT ������ header/body ��һϵ�е���Ϣ
		DecodedJWT decodedJWT = JWT.require(algorithm)
						.build().verify(token);
		
		// ��׼��Ϣ
		String issuser = decodedJWT.getIssuer();
		Date expiresAt = decodedJWT.getExpiresAt();
		String subject = decodedJWT.getSubject();
		List<String> audiences = decodedJWT.getAudience();
		Date notBefore = decodedJWT.getNotBefore();
		Date issuedAt = decodedJWT.getIssuedAt();
		String id = decodedJWT.getId();
		
		// ˽����Ϣ
		String name = decodedJWT.getClaim("name").asString();
		long id = decodedJWT.getClaim("id").asLong();
		
		// ���е�˽����Ϣ
		Map<String, Claim> claims = decodedJWT.getClaims();
	

		// ��У��ǩ���������, ����token
		DecodedJWT decodedJWT = JWT.decode(String token)

	#  ���token�Ѿ�����, ������У��ʧ��, ���׳��쳣
			JWTVerificationException
				|-AlgorithmMismatchException
				|-InvalidClaimException
				|-JWTCreationException
				|-JWTDecodeException
				|-SignatureGenerationException
				|-SignatureVerificationException
				|-TokenExpiredException


---------------------
jwt - RSA
---------------------
	# RSA
		* ˽Կֻ������ǩ��JWT����������У������
		* �ڶ�����Կ������Կ(public key)����Ӧ�÷�����ʹ����У��JWT��
		* ��Կ��������У��JWT��������������JWTǩ����
		* ��Կһ�㲻��Ҫ���ܱ��ܣ���Ϊ����ڿ��õ��ˣ�Ҳ�޷�ʹ������α��ǩ����

		* �������ǣ����԰ѹ�Կ�ַ������ˣ����˿�������У��Token�Ƿ���ϵͳ��˽Կǩ��


	# Demo
		import java.security.KeyPair;
		import java.security.KeyPairGenerator;
		import java.security.NoSuchAlgorithmException;
		import java.security.interfaces.RSAPrivateKey;
		import java.security.interfaces.RSAPublicKey;
		import java.util.Date;
		import java.util.HashMap;
		import java.util.Map;

		import com.auth0.jwt.JWT;
		import com.auth0.jwt.algorithms.Algorithm;
		import com.auth0.jwt.interfaces.DecodedJWT;

		public class JWTMain {
			public static void main(String[] args) throws Exception {
				
				// ����˽Կ�͹�Կ
				KeyPair keyPair = initKey();
				RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
				RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

				// ʹ�ù�Կ/˽Կ���� Algorithm
				Algorithm algorithm = Algorithm.RSA512(rsaPublicKey, rsaPrivateKey);
				
				Map<String, Object> jwtHeader = new HashMap<>();
				jwtHeader.put("alg", "RS512");  // ָ��RSA512
				jwtHeader.put("typ", "JWT");

				// ����Token
				String token = JWT.create().withHeader(jwtHeader)
						.withClaim("id", 1000L)
						.withIssuer("springboot��������") // ǩ����
						.withNotBefore(new Date()) // ��Чʱ��
						.withIssuedAt(new Date()) // ǩ��ʱ��
						.withJWTId("123456") // ���
						.sign(algorithm);
				
				System.out.println(token);
				
				// У��Token
				DecodedJWT decodedJWT = JWT.require(algorithm)
						.build().verify(token);
				
				System.out.println(decodedJWT);
				
			}

			// ��ʼ����Կ����Կ
			public static KeyPair initKey() throws NoSuchAlgorithmException {
				KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // RSA ����
				keyPairGenerator.initialize(2048); // RSA��Կ����
				KeyPair keyPair = keyPairGenerator.generateKeyPair();
				return keyPair;
			}
		}
