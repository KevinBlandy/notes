--------------------------------
Hashing							|
--------------------------------
	# Guava提供的hash算法类库(https://docs.google.com/spreadsheets/d/1_q2EVcxA2HjcrlVMbaqXwMj31h9M5-Bqj_m8vITOwwk/edit#gid=0)
		+----------------------------------------------------------------------------------------------
		| Hash 算法							长度(Bit)	时间	推荐	备注 
		+----------------------------------------------------------------------------------------------
		| Hashing#adler32					32			1.00	No		仅限校验和（交易速度的可靠性）
		+----------------------------------------------------------------------------------------------
		| Hashing#crc32						32			1.52	No		
		+----------------------------------------------------------------------------------------------
		| Hashing#goodFastHash(32)			32			2.73	Yes		VM运行之间不稳定
		+----------------------------------------------------------------------------------------------
		| Hashing#murmur3_32				32			2.75	Yes		
		+----------------------------------------------------------------------------------------------
		| Hashing#goodFastHash(64)			64			5.25	Yes		VM运行之间不稳定
		+----------------------------------------------------------------------------------------------
		| Hashing#murmur3_128				128			5.26	Yes		
		+----------------------------------------------------------------------------------------------
		| Hashing#goodFastHash(128)			128			5.41	Yes		VM运行之间不稳定
		+----------------------------------------------------------------------------------------------
		| Hashing#md5						128			6.03	No		不具有加密安全性或抗冲突性
		+----------------------------------------------------------------------------------------------
		| Hashing#sha1						160			9.78	No		不加密安全
		+----------------------------------------------------------------------------------------------
		| Hashing#goodFastHash(256)			256			10.41	Yes		VM运行之间不稳定
		+----------------------------------------------------------------------------------------------
		| Hashing#sha256					256			17.58	No		可能是加密安全的
		+----------------------------------------------------------------------------------------------
		| Hashing#sha512					512			43.78	Yes		
		+----------------------------------------------------------------------------------------------
		| Hashing#goodFastHash(int bits)	N			n/a		Yes		不稳定,用户配置了N位HashCodes
		+----------------------------------------------------------------------------------------------
		


	# 它提供了N多的静态方法,用于创建不同的Hash算法(以及不同版本,位数)实例
		
		HashFunction crc32()

		HashFunction crc32c()

		HashFunction goodFastHash(int minimumBits)

		HashFunction farmHashFingerprint64()

		HashFunction hmacMd5(byte[] key)

		
		HashFunction murmur3_128()
		HashFunction murmur3_128(int seed)
		HashFunction murmur3_32()
		HashFunction murmur3_32(int seed)

		HashFunction sha256()
		HashFunction sha384()
		HashFunction sipHash24()

--------------------------------
HashFunction							|
--------------------------------
	# 它是一个接口,由不同的hash算法实现
	# 接口 方法
		Hasher newHasher();

		Hasher newHasher(int expectedInputSize);

		HashCode hashInt(int input);

		HashCode hashLong(long input);

		HashCode hashBytes(byte[] input);
			* 对字节数据进行hash计算

		HashCode hashBytes(byte[] input, int off, int len);

		HashCode hashBytes(ByteBuffer input);

		HashCode hashUnencodedChars(CharSequence input);

		HashCode hashString(CharSequence input, Charset charset);
			* 对字符串进行hash运算

		<T> HashCode hashObject(T instance, Funnel<? super T> funnel);

		int bits();
--------------------------------
Hashing							|
--------------------------------
	# 是hash计算结果的抽象接口
	# 静态方法
		HashCode fromInt(int hash)
	
	# 实例烦恼发
		int asInt();
			* 返回hash的int值
		
		long asLong();
			* 返回hash的long值

		long padToLong();
		byte[] asBytes();
		int bits();

--------------------------------
各种Hash算法的实现				|
--------------------------------
	# murmur3 128位hash算法
		HashFunction function = Hashing.murmur3_128();
		HashCode hascode = function.hashString("murmur3_128", StandardCharsets.UTF_8);
		System.out.println(hascode.asInt());