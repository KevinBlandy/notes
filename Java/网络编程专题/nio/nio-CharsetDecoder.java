----------------------------
CharsetDecoder				|
----------------------------
	# ×Ö·û±àÂëÆ÷
	

	# ¶Ô ByteBuffer ±àÂë

		ByteBuffer attachment = ByteBuffer.allocate(1024);
		attachment.flip();
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
		decoder.decode(attachment,charBuffer,false);
		charBuffer.flip();
		String data = new String(charBuffer.array(),0, charBuffer.limit());