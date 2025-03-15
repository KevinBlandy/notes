package net.csedu.test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MaiTest {
	

	public static void main(String[] args) throws Throwable {
		
		int maxSize = 10;
		
		List<Integer> queue = new ArrayList<>();
		
		Thread producer = new Thread(() -> {
			for (int i = 1; i > 0; i ++) {
				synchronized (queue) {
					while (queue.size() >= maxSize) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Produce ---> " + i);
					
					queue.addFirst(i);
					queue.notifyAll();
					
					// 限制生产速度
//					try {
//						Thread.sleep(Duration.ofMillis(100));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}
		});
		Thread consumer =  new Thread(() -> {
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					var item = queue.removeLast();	
					queue.notifyAll();
					System.out.println("Consumer <--- " + item);
					
					// 限制消费速度
					try {
						Thread.sleep(Duration.ofMillis(500));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		producer.start();
		consumer.start();
		
		System.in.read();
	}
}
