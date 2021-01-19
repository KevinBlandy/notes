----------------------------
ByteBufHolder				|
----------------------------
	# 继承了 ReferenceCounted
	# 抽象方法
		ByteBuf content();

		ByteBufHolder copy();

		ByteBufHolder duplicate();

		ByteBufHolder retainedDuplicate();

		@Override
		ByteBufHolder retain();

		@Override
		ByteBufHolder retain(int increment);

		@Override
		ByteBufHolder touch();

		@Override
		ByteBufHolder touch(Object hint);
	