import java.io.Serializable
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets

class Message : Serializable {
    val header : ByteBuffer = ByteBuffer.allocate(4)
    private var content : ByteBuffer? = null
    constructor(){}
    fun getContent():ByteBuffer?{
        return this.content
    }
    fun setContent(content: ByteBuffer?) {
        this.content = content
    }
}

fun messageEncode (data:String):ByteBuffer {
    var byteBuffer = ByteBuffer.allocate(4 + data.length)
    byteBuffer.putInt(data.length)
    byteBuffer.put(data.toByteArray(StandardCharsets.UTF_8))
    byteBuffer.flip()
    return byteBuffer
}

fun main(){

    val selector = Selector.open()

    val serverSocketChannel = ServerSocketChannel.open()
    serverSocketChannel.bind(InetSocketAddress("0.0.0.0", 1024))
    serverSocketChannel.configureBlocking(false)
    serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true)

    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)

    while (selector.select() > 0){

        val selectionKeyIterator = selector.selectedKeys().iterator()

        while(selectionKeyIterator.hasNext()){

            val selectionKey = selectionKeyIterator.next()

           try{
               when {
                   selectionKey.isAcceptable -> {
                       val socketChannel = (selectionKey.channel() as ServerSocketChannel).accept()
                       socketChannel.configureBlocking(false)
                       socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true)

                       socketChannel.register(selector, SelectionKey.OP_READ or SelectionKey.OP_WRITE, Message())
                   }
                   selectionKey.isReadable -> {

                       val socketChannel = selectionKey.channel() as SocketChannel

                       var message = selectionKey.attachment() as Message;

                       if (message.getContent() === null){
                           var result = socketChannel.read(message.header)
                           if (result == -1){
                               println("连接断开:address=${socketChannel.remoteAddress}")
                               //TODO 广播退出，会话移除
                           }
                           if (!message.header.hasRemaining()){
                               var length = message.header.getInt(0)
                               if (length > Integer.MAX_VALUE){
                                   socketChannel.write(messageEncode("消息大小不能超过:${Integer.MAX_VALUE}"))
                                   socketChannel.close()
                                   // TODO 广播退出，会话移除
                               }
                               message.setContent(ByteBuffer.allocate(length))
                           }
                       }else{
                           var result = socketChannel.read(message.getContent())
                           if (result == -1){
                               println("连接断开:address=${socketChannel.remoteAddress}")
                               //TODO 广播退出，会话移除
                           }
                           if(!message.getContent()!!.hasRemaining()){
                               var content = String(message.getContent()!!.array(),StandardCharsets.UTF_8)
                               message.setContent(null)
                               message.header.clear()
                               // TODO 广播消息
                               println("收到消息:$content")
                           }
                       }
                   }
                   selectionKey.isWritable -> {

                   }
                   selectionKey.isConnectable -> {

                   }
               }
           }catch (e : Exception){
               e.printStackTrace()
           }
           finally {
               selectionKeyIterator.remove()
           }
        }
    }
}




import java.io.BufferedOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class Reading : Runnable {
    private var socket : Socket
    constructor(socket:Socket){
        this.socket = socket
    }
    override fun run() {
        var inputStream = this.socket.getInputStream()
    }
}

fun main() {
    var socket = Socket()
    socket.connect(InetSocketAddress("127.0.0.1", 1024))
    var bufferedOutputStream = BufferedOutputStream(socket.getOutputStream())
    var data = "卧槽。".toByteArray(StandardCharsets.UTF_8)
    var byteBuffer = ByteBuffer.allocate(4 + data.size)
    byteBuffer.putInt(data.size)
    byteBuffer.put(data)
    bufferedOutputStream.write(byteBuffer.array())
    bufferedOutputStream.flush()
}