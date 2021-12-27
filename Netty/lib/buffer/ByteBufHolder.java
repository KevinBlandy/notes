------------------------------
ByteBufHolder
------------------------------
	# 封装了一个发送/接受的buffer接口: public interface ByteBufHolder extends ReferenceCounted

	ByteBuf content();
		* 获取content

	ByteBufHolder copy();
		* 复制一个当前的 ByteBufHolder

	ByteBufHolder duplicate();
	ByteBufHolder retainedDuplicate();
	ByteBufHolder replace(ByteBuf content);
	@Override
    ByteBufHolder retain();
    @Override
    ByteBufHolder retain(int increment);
    @Override
    ByteBufHolder touch();
    @Override
    ByteBufHolder touch(Object hint);
