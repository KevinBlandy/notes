import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;

import javax.websocket.Session;


import ch.qos.logback.core.OutputStreamAppender;
import io.springboot.log.web.socket.channel.LogChannel;
/**
	过时，勿用
**/
public class SocketOutputStreamAppender<E> extends OutputStreamAppender<E> {
	
	@Override
	public void start() {

		// 管道读取流
		PipedInputStream pipedInputStream = new PipedInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream, StandardCharsets.UTF_8));
		
		// 管道写入流
		PipedOutputStream pipedOutputStream = new PipedOutputStream();
		
		try {
			 // 写入读取管道链接
			pipedOutputStream.connect(pipedInputStream); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 设置输出流到Appender
		super.setOutputStream(pipedOutputStream);
		
		// 子线程开始从管道流读取数据
		Thread thread = new Thread(() -> {
			String line = null;
			while (true) {
				try {
					line = bufferedReader.readLine();
				} catch (IOException e) {
					// e.printStackTrace();
				}
				if (line != null) {
					for (Session channel : LogChannel.CHANNELS.values()) {
						if (channel.isOpen()) { 
							// 异步传输数据到客户端
							channel.getAsyncRemote().sendText(line);
						}
					}
				}
			}
		});
		
		thread.setDaemon(Boolean.TRUE);
		thread.setName("socket-log");
		thread.start();
		
		super.start();
	}
}
