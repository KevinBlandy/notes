-------------------
Redis-����			|
-------------------
	# redis��Transaction(����)
	# redis������
		* redis���������ϵ�����ݿ⻹����һ��������
		* ��������,����ִ�е�����ᰴ���Ⱥ�˳����뵽һ��������
		* �����exec(�ύ����)����ԭ���Ե��ύִ��
	discard
	multi
		# �������ʼ,��ִ����һ�������.�ɹ�����ӵ��˶���,����:QUEUED
	exec
		# ����������
	unwatch
	watch
-------------------
Redis-��������		|
-------------------
	publish channle message
		# ����Ϣ(message)���͸�ָ����Ƶ��(channle)
		# ����:���յ�message��Ϣ�Ķ���������
	subscribe channel1,channle2...
		# ����һ�����߶��Ƶ��,��ô�ᴦ��һ���̵߳ȴ�״̬.���Լ�ʱ�Ļ�ȡ������Ƶ�����͵���Ϣ
		# �յ������ݶ��ĸ�ʽ
			(1)"message"		//�̶�������ʽ,���Ǵ�������һ��Ƶ��������Ϣ
			(2)Ƶ������
			(3)��Ϣ�ڴ�

	UNSUBSCRIBE [channel channel ...]
		# �˶�Ƶ������д channel �����Ļ��������˶�����Ƶ��

	redis-cli.exe -h localhost -p6379 subscribe channle
		# windows������redsi�ͻ��˵�ʱ��,�Ͷ���һ��ָ��������Ƶ��
		# ��д  -h �� -p ����.��ôĬ�ϵľ������ӱ���
	./redis-cli -h localhost -p6379 subscribe channle
		# linux������redis�ͻ��˵�ʱ��,�Ͷ���һ��ָ��������Ƶ��
		# ͬ��
	
	* ���˶���Ƶ��֮�⣬�ͻ��˻�����ͨ������ģʽ��pattern����������Ϣ��ÿ����������ĳ��Ƶ��������Ϣ��ʱ�򣬲���Ƶ���Ķ����߻��յ���Ϣ����Ƶ��ƥ�������ģʽ�Ķ�����Ҳ���յ���Ϣ��



-------------------
Redis-CAS����		|
-------------------
	# watch ���Լ���һ��key, �ڸ�Key�������ݱ仯��ʱ��, �Զ��ع���ǰ������
		* ����ͨ����ǰ�����Ƿ�ع�, ���ж�CAS�Ƿ���³ɹ�

import io.lettuce.core.RedisClient;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.CountDownLatch;

public class CasUpdate {

    private static final RedisClient redisClient;

    public static void main(String[] args) throws Exception{

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();
        redisCommands.hset("user", "name", "KevinBlandy");
        redisCommands.hset("user", "balance", "0");
        redisCommands.hset("user", "version", "0");

		// 100���߳�ִ�� + 1����
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for(int x = 0 ; x < 100 ;x ++){
            new Thread(() -> {
                try {
                    casUpdate();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.await();
    }

    public static void notSafeUpdate ()throws Exception{
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();
    }

    public static void casUpdate() throws Exception {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();

        while (true){

            String watchResult = redisCommands.watch("user");
            if (!watchResult.equals("OK")){
                throw new RuntimeException();
            }
            String multiResult =  redisCommands.multi();
            if (!multiResult.equals("OK")){
                throw new RuntimeException();
            }


            // �����ݽ������� +1 ����
            redisCommands.hincrby("user", "balance", 1);
            redisCommands.hincrby("user", "version", 1);

            TransactionResult transactionResult = redisCommands.exec();
            System.out.println(transactionResult);
            if (transactionResult.wasDiscarded()){
                // ����ʧ��, ��������
                continue;
            }
            break;
        }
    }

    static {
        redisClient = RedisClient.create("redis://localhost:6379/0");
    }
}
