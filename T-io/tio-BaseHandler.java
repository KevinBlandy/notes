package com.kevin.tio.common;

import java.nio.ByteBuffer;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;
/**
 * 抽象Handler
 * 客户端服务端编/解码方式一样
 * @author	KevinBlandy
 * @version	1.0
 * @date	2017年5月2日 下午10:57:21
 */
public abstract class BaseHandler implements AioHandler<Object,DataPacket,Object>{
	
	/**
	 * 数据解码
	 */
	@Override
	public ByteBuffer encode(DataPacket packet, GroupContext<Object, DataPacket, Object> groupContext,ChannelContext<Object, DataPacket, Object> channelContext) {
		byte[] data = packet.getData();							//获取到数据
		int dataLength = data.length;							//获取数据长度	
		int length = DataPacket.DATA_LENGTH + dataLength;		//计算包体总长度
		ByteBuffer byteBuffer = ByteBuffer.allocate(length);	//创建Buffer
		byteBuffer.putInt(dataLength);							//写入包头信息(数据长度)
		byteBuffer.put(data);									//写入数据
		return byteBuffer;	
	}
	
	/**
	 * 数据编码
	 */
	@Override
	public DataPacket decode(ByteBuffer buffer, ChannelContext<Object, DataPacket, Object> channelContext)throws AioDecodeException {
	    //可读数据总长度
	    int remaining = buffer.remaining();
		if(remaining < DataPacket.DATA_LENGTH){
			//组包失败,返回null
			return null;
		}
		//获取消息体长度
		int dataLength = buffer.getInt();
		if(dataLength < 0){
			//数据格式不正确,抛出异常
			 throw new AioDecodeException("bodyLength [" + dataLength + "] is not right, remote:" + channelContext.getClientNode());
		}
		int length = DataPacket.DATA_LENGTH + dataLength;		//计算包体的总长度(消息头长度 + 消息体长度)
		if(remaining < length){
			//组包失败
			return null;
		}
		//组包成功
		DataPacket dataPacket = new DataPacket();
		if(dataLength > 0){
			//包体数据存在
			byte[] data = new byte[dataLength];
			buffer.get(data);
			dataPacket.setData(data);
		}
		return dataPacket;
	}

}
