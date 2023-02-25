------------------------
Stream	- ����
------------------------
	# �ο�
		* https://redis.io/docs/data-types/streams/
		* https://redis.io/docs/data-types/streams-tutorial/
		* https://redis.io/commands/?group=stream

------------------------
Stream	- �����ܽ�		|
------------------------
	XADD 
		* XADD key ID field string [field string ...]
		* ���һ����Ϣ��stream, ���������Я���� MAXLEN ����, ��ôstream�Ƕ�����
		
	XDEL
		* XDEL key ID [ID ...]
		* ��streamɾ��һ�����߶�����Ϣ
	
	XTRIM
		* XTRIM key MAXLEN [~] count
		* ��stream�е���Ϣ�ü���ָ��������, ��XADD�е� MAXLEN ��������һ��
		
	XLEN
		* XLEN key
		* ��ȡstream�е���Ϣ����
	
	XRANGE
		* XRANGE key start end [COUNT count]
		* ��stream�и���ָ����Χ������ȡһ�η�Χ�ڵ���Ϣ
		
	XREVRANGE
		* XREVRANGE key end start [COUNT count]
		* �� XRANGE һ��, �����Ƿ����ȡ
		
	XREAD
		* XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] ID [ID ...]
		* ��stream�и���ָ��id��λ��, ����/�������Ķ�ȡһ��/������Ϣ
		
	XGROUP CREATE key groupname <id | $> [MKSTREAM] [ENTRIESREAD entries_read]
		* ����/ɾ��������
	XGROUP CREATECONSUMER key groupname consumername
	XGROUP DELCONSUMER key groupname consumername
	XGROUP DESTROY key groupname
	XGROUP SETID key groupname <id | $> [ENTRIESREAD entries_read]
		
	XREADGROUP
		* XREADGROUP GROUP group consumer [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] ID [ID ...]
		* ��������, ��stream�и���group��consumer��ȡ����δ�����ѵ���Ϣ
		
	XPENDING key group [[IDLE min-idle-time] start end count [consumer]]
		* * ��stream�ж�ȡһ��group,consumer�Ѿ���ȡ���ǻ�δ֪ͨ���ѳɹ�����Ϣ����
		
	XACK
		* XACK key group ID [ID ...]
		* ֪ͨһ�����߶�����Ϣ�Ѿ����ɹ�������, ֪ͨ������� XPENDING ��ɾ����Ϣ, �����stream��ɾ����Ϣ

	XCLAIM
		* XCLAIM key group consumer min-idle-time ID [ID ...] [IDLE ms] [TIME ms-unix-time] [RETRYCOUNT count] [force] [justid]
		* ��һ��/������Ϣ, ��һ��consumer��XPENDING�б�, ת�Ƶ���һ��consumer��XPENDING�б�
	
	XAUTOCLAIM key group consumer min-idle-time start [COUNT count] [JUSTID]

		
	XINFO
		* XINFO [CONSUMERS key groupname] [GROUPS key] [STREAM key] [HELP]
		
	
	XINFO CONSUMERS key groupname
	XINFO GROUPS key
	XINFO STREAM key [FULL [COUNT count]]
	
------------------------
Stream					|
------------------------
	# redis5��������, ������һ��MQϵͳ, Stream��Redis��������������ӵ�

	# ������ݵ�stream
		XADD [stream] MAXLEN [maxLen] [id] [key] [value] [key] [value]
			stream
				* ָ��stream����
			
			MAXLEN [maxLen]
				* ��ѡ�Ĳ���
				* stream�����洢����Ŀ, �Զ�����ɵ���Ŀ
					XADD mystream MAXLEN 2 * value 1
				* ����ʹ��һ���м����(~), ��ʾ���ٱ���n����Ŀ����(000����1010����1030)
				* ͨ��ʹ���������, �����Ƴ������ڵ��ʱ���ִ������, ��ʹ���������Ч
					XADD mystream MAXLEN ~ 1000 * value 1

			id
				* ָ����Ϣid, �����Ҫϵͳ����, ����ʹ��: * (������)
				* ������Զ���id, �����������, ��СIDΪ: 0-1, ����������ܵ��ڻ�С��ǰһ��ID��ID
			
			key, value
				* Stream��Ŀ���Ǽ򵥵��ַ���, ������һ��������ֵ����ɵ�
				* �����ж��key,value
			
			* demo
				XADD mystream * sensor-id 1234 temperature 19.8
				// ����Ϊmystream��Stream�������һ����Ŀsensor-id: 123, temperature: 19.8��ʹ�����Զ����ɵ���ĿID��Ҳ��������ص�ֵ
			
			* ���������Ϣ��id
				1574131352064-0

				* id��2�������, һ�����ǵ�ǰ��������ʱ���, һ���־�������(��ͬʱ���������)������id
				* ����ID��������Ŀ��ʱ�����, ��˿��Ժ����׵ذ�ʱ�䷶Χ���в�ѯ


	# ��ȡstream�е���Ŀ����
		XLEN [stream]


	# ��ѯ����
		 XRANGE [stream] [start] [end] COUNT [count]
			start, end
				* ��ʼʱ����ͽ���ʱ���
					XRANGE mystream 1518951480106 1518951480107
				* ��������ķ���:-,+ �ֱ��ʾ���ܵ���СID�����ID
				* ���û��ָ��seq����, ��start������Ĭ��:0, ��end������Ĭ��: �������
			
			COUNT [count]
				* ���ص�����, �Ǳ������
					XRANGE mystream - + COUNT 1
			
			* ���ص�ÿ����Ŀ����������Ԫ�ص�����: ID�ͼ�ֵ���б�
				1) 1) "1574131352064-0" [0][0]
				   2) 1) "sensor-id"	[0][1]
					  2) "1234"
					  3) "temperature"
					  4) "19.8"
				2) 1) "1574132679725-0"	[1][0]
				   2) 1) "sensor-id"	[1][1]
					  2) "12345"
					  3) "temperature"
					  4) "19.81"
			
			* ͨ��ѡ�����һ����¼��id, ���е���
				XRANGE mystream 1574132679725-0 + COUNT 2
			
			* XRANGE�Ĳ��Ҹ��Ӷ���O(log(N)), ���O(M)����M��Ԫ��, ���������count��Сʱ, ���ж���ʱ�临�Ӷ�
			* ����ζ��ÿһ�������ٶȶ��ܿ�, ����XRANGEҲ����ʵ�ϵ������������Ҳ���ҪXSCAN����
	
	# �����ѯ����
		XREVRANGE [stream] [end] [start] COUNT [count]

		* ��XRANGE��ͬ, �������෴��˳�򷵻�Ԫ��
		* ���XREVRANGE��ʵ����;�Ǽ��һ��Stream�е����һ����ʲô
			XREVRANGE [stream] + - COUNT 1
	
	# ɾ������Ԫ��
		XDEL [stream] ID [ID ...]
			ID [ID ...]
				* Ҫɾ����id, �����Ƕ��
			
			* �����ɾ�������������˱�־λ����Ӱ����Ϣ�ܳ���
			* ��û��ɾ��Pending�е���Ϣ
	
	# �޼�stream������
		XTRIM [stream] MAXLEN [maxLen]
			MAXLEN [maxLen]
				* ɾ��ָ��stream�е�����, ֻ���µ� maxLen ��
		

	# ��������
		XREAD COUNT [count] BLOCK [milliseconds] STREAMS [stream...] ID [id...]

			COUNT [count]
				* �Ǳ���Ĳ���
			
			BLOCK [milliseconds]
				* �Ǳ���Ĳ���
				* �����Ӹò���, ��ʾ��ǰ�ͻ�����������
				* �����Ǻ�����, ���Ϊ0, ��ʾ����ʱ
					
			STREAMS [stream...]
				* ָ��stream, �����ж��, �ո�ָ�
				* ע��, ����ж�� stream, �����Ҫ�ж�� id, һһ��Ӧ, ��ʾͬʱ�Ӳ�ͬ��Stream�ж�ȡ��ͬkey������
			
			ID [id...]
				* �Ǳ���Ĳ���
				* ָ�����ѵĴ��ڸ�id(��벿�ּ���)����Ϣ, �����ж��
					XREAD COUNT 2 STREAMS mystream 1574131352064-0
				
				* ע��, ����ж�� id, �� STREAMS ����ָ�����stream, һһ��Ӧ, ��ʾͬʱ�Ӳ�ͬ��Stream�ж�ȡ��ͬkey������
					STREAMS mystream otherstream 0 0
				
				* id������ֵ: $ , ��������ID��˼��XREADӦ��ʹ�����Ѿ��洢�����ID��Ϊ���һ��ID, �Ա����ǽ����մ����ǿ�ʼ����ʱ���Ժ������Ϣ
				* ����ĳ�̶ֳ���������Unix����tail -f(��������)
					XREAD BLOCK 0 STREAMS mystream $
				* ���ڷ�����ģʽ��˵$�������������, ��Ϊ��ȡ����Ϣ����Ϊ��
				* ����ģʽ��, �������IDָ��Ϊ$, ��ôCOUNT������������, ֻ��������, �ͻ���������, ��������
					- ������ģʽ�����ָ����ID����$, ������0������stream�������ݿɶ�, ��ʱ����ģʽ���Լ򵥵���Ϊ�˻��ɷ�����ģʽ, COUNT����������

				* ����һ���Լ���N��stream��n��id, ֻҪ����һ�����µ�����, �ͻ᷵��
					XREAD BLOCK 0 STREAMS mystream foo $ $
					
				* �ڼ�Ⱥ������, Ҫע�����е�stream��key������ͬһ��slot�Ϸ��򱨴�
	
				
	# ���������
		XGROUP [CREATE key groupname id-or-$] [SETID key id-or-$] [DESTROY key groupname] [DELCONSUMER key groupname consumername]
			[stream]
				* ָ��stream������
			
			[group]
				* ָ��group������
			
			[id]
				* ��ʾ�Ӵ���������Ϣ��ʼ����
				* �������Ϊ��0, ��ʾ��ͷ��ʼ����, ����Ϊ $ , ֻ�������������鴴���Ժ����Ϣ
			
			[CREATE]
				* ����һ��������(Ŀǰ����������, stream�������)
					XGROUP CREATE mystream mygroup $
				
			[SETID]
				* �޸������������ID(�޸���Ϣ���鴴����ʱ��ָ����ID��ʼλ��)
					XGROUP SETID mystream mygroup $
			
			[DESTROY] 
				* ɾ��������
					XGROUP DESTROY mystream mygroup
			
			[DELCONSUMER]
				* ɾ��������
					XGROUP DELCONSUMER mystream mygroup consumer
			
			* ��������ҪԤ�ȴ���, ����stream key �Ǳ�����ڵ�
			* ������Զ�һ��stream key �ظ�����ͬ����key, ���׳��쳣
	
	
	# ���������ʽ��������
		XREADGROUP GROUP [group] [consumer] COUNT [count] BLOCK [milliseconds] STREAMS [stream...] ID [id...]
			GROUP [group]
				* ָ�������������
			
			consumer
				* ָ����������, ��ǰ�����ߵ�����
				* ����������ʹ�õ�ʱ���Զ�����, ����ҪԤ�ȴ���
			
			ID [id...]
				* ���ĸ�id����Ϣ��ʼ����
				* �������е�id, ��һ��������� >
				* ��������IDֻ���������������������Ч, ����˼��: ��ĿǰΪֹ��δ���ݸ����������ߵ���Ϣ
				* ���id����Ϊ�� > ��ʾ, �������ѵ�ǰ��������, ����������δ���ѵ���Ϣ, ͨ���������id, �ﵽ: ͬһ����������, ֻ��һ�����������������Ϣ
				* ��������ID��ʵ���� last_delivered_id

				* ��ID���������ַ�>ʱ, XREADGROUP�����Ǵ���Ϣ�����ж�ȡ��Ϣ, ���Ǵ�consumer��pending��Ϣ�б��ж�ȡ��ʷ��Ϣ
				* һ�㽫������Ϊ0-0����ʾ��ȡ���е�pending��Ϣ�Լ���last_delivered_id֮�������Ϣ��
				* һ����������ǣ�����������߷��������ݣ����������߶Ͽ������ӣ�û�д������ݣ�����˵�����Ѿ������ѣ�����û�յ�ACK���ͻ���������ʹ��������������¹���������

				* ������ʹ��: $ û������


			* �� XREAD һ��, ����������: GROUP [group], consumer
			* һ����Ϣ, һ����ֻ�ܱ�ͬһ����������һ������������
			
			* XREADGROUP��Ȼ�Ǹ���ȡ����, ��������һ��д����,
			* ��Ϊ�ڶ�ȡ��ʱ��, XREADGROUP�ڲ���Ѷ�ȡ������Ϣ��ӵ������ߵ�pending��Ϣ����, ���һ��޸������߷����е�last_delivered_id�����ݽṹ
			* ��������һ��д����, ����ڿ����˶�д����Ļ�����, �������ֻ����master�ڵ��Ͻ��в���
			
			* XREADGROUP֧�ֶ��streams�Ķ�ȡ, ������һ��ǰ������: ���е�streams��Ԥ�ȴ�����ͬ���������߷���

			* ����ʾ��
				XREADGROUP GROUP my_group  my_consumer2  BLOCK 0 STREAMS my_stream >
				* stream=my_stream, group=my_group, consumer=my_consumer2
		
	# �������е�������, ȷ����Ϣ����
		 XACK [stream] [group] ID [ID ...]
			[stream]
				* ָ��stream
			
			[group]
				* ָ��group
			
			ID [ID ...]
				* ָ�����id, ��ʾ�Ѿ�����
			
			* demo
				XACK mystream mygroup 1542355396362-0

	# ��ȡָ����������, ��ȷ�ϵ���Ϣ
		XPENDING [stream] [group] [start] [end] [count] [consumer]
			[stream] [group]
				* ָ��stream��������,
				
			[start] [end] [count]
				* ָ����ʼ������ʾ����, �Ǳ������
			
			[consumer]
				* ָ��������, �Ǳ������
				* �����ָ��, �򷵻ظ��������pending����
		
		* ֻ��stream��group����ʱ, ��ʾ��ȡ���������߷���ĸ�Ҫ��Ϣ
			1) (integer) 4				// �����pending��Ϣ���ܸ���
			2) "1542355421051-0"		// pending��Ϣ��������ʼ����ϢID�ͽ�������ϢID
			3) "1542355444220-0"
			4) 1) 1) "consumer_1"		// ��������ÿ�������ߵ�pending��Ϣ��������Ϣ�ĸ���
				  2) "1"
		
		* ����start end count����ʱ, ��ʱ����ʾ�����߷���pending��Ϣ������
			1) 1) "1542355421051-0"		// ��Ϣid
			   2) "consumer_1"			// ����������
			   3) (integer) 4109624		// Ϣ�Դӱ������߻�ȡ�����ڹ�ȥ��ʱ��(����) - idle time
			   4) (integer) 7			// ��Ϣ����ȡ�Ĵ���	- delivery counter
	
	# �ı���Ϣ��������
		XCLAIM [stream] [group] [consumer] [min-idle-time] [ID ...] [IDLE ms] [TIME ms-unix-time] [RETRYCOUNT count] [force] [justid]
			[stream] [group] [consumer]
				* �ֱ�ָ��stream, ������, �Լ��������е�������(Ҳ������Ϣ������)
			
			[min-idle-time]
			
			
			[ID ...]
				* ָ��һ�����߶��id
			
			[IDLE ms]
				* ����ֵ, ��ʾ����ת�� idle time ���ڸ�ֵ�ļ�¼
				
			[TIME ms-unix-time]
			
			[RETRYCOUNT count]
			
			[force]
			
			[justid]
				* ִ�гɹ������������Ϣ��id, ��������Ϣ������
	
			
			* ��һ����Ϣ��ӵ��Ȩ����ת�Ƶ�ʱ��, ��Ѹ���Ϣ��idle time���ã�����������ԭ���:��ֹ����Ϣ���ظ�����
			* demo
				- �� consumer_2 �е���Ϣ 1542355436365-0 ����Ȩת�Ƹ� consumer_3, ���ҵ� idle time > 10 Hour ʱ��ת��
					XCLAIM mystream mygroup consumer_3 36000000 1542355436365-0
			
				- �� consumer_2 �е���Ϣ 1542355427899-0 ����Ȩת�Ƹ� consumer_1, ���ҵ� idle time > 1 Hourʱ��ת��
					XCLAIM mystream mygroup consumer_1 3600000 1542355427899-0
	
	
------------------------
Stream - ������			|
------------------------
	# �ص�
		1, ��ͬ�������ߣ�ͨ��������ID���֣�����Ӱ�죬�໥���룬���Ǳ˴˿��������Ե���ʷ������Ϣ
		2, ��ͬ�������ߣ�ͨ��������ID���֣�ֻ�ܿ���������Ϣ���е�һ������Ϣ��Ҳ����˵һ��������ֻ����������Ϣ���е�һ���Ӽ���������Щ�Ӽ�����������غϵ���Ϣ
		2, �������������ѵ���Ϣ�Ӽ��Ľ�������������Ϣ���м���
	
	# ������pending��Ϣ
		* ��consumer��ȡ����Ϣ֮��, stream�ͻ��������Ϣ���뵽pending��Ϣ�б�
		* ���������Ϣ��consumer�ɹ�����, ��Ҫͨ��ACK���Ƹ���stream�ɹ����Ѳ��Ҵ�consumer��pending��Ϣ�б���ɾ��, ��Ȼһ����Ϣ�ͻ�ռ��2�����ڴ���
			- stream�б���һ��, consumer��pending��Ϣ�б��б���һ��
	
	# �������pending��Ϣ
		* ����������pending��Ϣ�ļ���
	
	# [idle time] �� [delivery counter]
		* idle time
			- ��ʾ�����߻�ȡ��������Ϣ, ��ACK֮ǰ, �����˶��
			- ������Ϣ���ɹ�ת�ƺ�, �����Իᱻ����
			
		* delivery counter
			- ÿʹ��XREADGROUP��ȡ������Ϣһ��, �����Ϣ��delivery counter�ͻ�����1
			- ��ζ�ȡ, ��ûACK, ��������һ������
	
	# ���Ŷ���
		* XPENDING�����ʱ��᷵����Ϣ��һ������: delivery counter
		* �����Ϣ���Ǹ���Ϣ�������߻�ȡ���Ĵ���
		* ��˿���ͨ������Ϣ����һЩ����:������һ����Ϣ��delivery counter����ĳ����ֵ��ʱ��, ��˵��������Ϣ�������ٱ�����ɹ���
		* ���ʱ����԰�������Ϣ�Ӷ�����ɾ��, Ȼ�������Ա����ϵͳ����һ���澯��־, �������νdead letter�Ĵ������


------------------------
Stream	- ���еı���	|
------------------------
	# XRANGE ����
		1, ʹ�� XRANGE key - + COUNT count ��ͼ��ȡcount����Ϣ
		2, ������ַ��ص���Ϣ����ret < count����ô˵�����е���Ϣ�Ѿ�����ȡ������������ת5
		3, ���ret==count����ô˵�������л���Ԫ�أ���¼���ص���Ϣ�����һ����Ϣ��IDΪlast_id��Ȼ�����������ʹ������ XRANGE key last_id + COUNT count
		4, �ظ�����2��3
		5, ��������
	
	# XREAD ����
		1, ʹ�� XREAD COUNT count STREAMS key 0 ��ͼ��ȡcount����Ϣ
		2, ������ַ��ص���Ϣ����ret<count����ô˵�����е���Ϣ�Ѿ�����ȡ��ϣ���������
		3, ������ַ��ص���Ϣ����ret==count����ô˵�������л���Ԫ�أ���¼���ص���Ϣ�����һ����Ϣ��IDΪlast_id��Ȼ�����������ʹ������ XREAD COUNT count STREAMS key last_id
		4, �ظ�����2��3
		5, ��������

------------------------
Stream	- ���			|
------------------------

	# �������
		XINFO [CONSUMERS key groupname] [GROUPS key] [STREAM key] [HELP]
		
	#  �鿴stream����Ϣ
		XINFO STREAM [stream]
			 1) "length"
			 2) (integer) 12				// �ܹ��洢����Ϣ����
			 3) "radix-tree-keys"			// �洢��Ϣ��field����
			 4) (integer) 1					
			 5) "radix-tree-nodes"			// ��Ϣ��ռ���˼���radix-tree�ڵ�
			 6) (integer) 2					
			 7) "groups"					// �м���������
			 8) (integer) 1		
			 9) "last-generated-id"			// ���һ����Ϣid
			10) "1574135225847-0"
			11) "first-entry"				// ��һ����Ϣ
			12) 1) "1574131352064-0"
				2) 1) "sensor-id"
				   2) "1234"
				   3) "temperature"
				   4) "19.8"
			13) "last-entry"				// ���һ����Ϣ
			14) 1) "1574135225847-0"
				2) 1) "name"
				   2) "KevinBlandy"
				   3) "age"
				   4) "23"574135225847-0"
		
	#  �鿴stream���������Ϣ
		XINFO GROUPS [stream]
			1) 1) "name"				// ��������
			   2) "group-1"
			   3) "consumers"			// �ж��ٸ�������
			   4) (integer) 1
			   5) "pending"				// �����߷��鱾��pending��Ϣ����,
						* �����Ϣ�б���Ǹ÷�����������consumer��pending��Ϣ�б���
						
			   6) (integer) 6
			   7) "last-delivered-id"  // group������ѵ�������Ϣ��ID
						* ��ͬ���������������ѵ���Ϣ���ظ���, ��Ҫԭ�����last_delivered_id�Ĵ���
						* �����ߴӶ����л�ȡ��δ�����ѵ���Ϣ��ʱ��, �����last_delivered_id���λ�ÿ�ʼ(������)
						* ���ֻҪlast_delivered_id���λ����Ϣ��ֵ���ű仯, ��ͬ�������߻�ȡ������Ϣ�Ͳ����ظ�


			   8) "1574135225847-0"
		
	# �鿴stream�������������ߵ���Ϣ
		XINFO CONSUMERS [stream] [group]
			1) 1) "name"					// ����������
			   2) "consumer-1"
			   3) "pending"					// consumer��pending��Ϣ�б���
			   4) (integer) 6			
			   5) "idle"					// pending��Ϣ��������С��idle time
			   6) (integer) 2916753
		

	# ����
		XINFO HELP
			1) XINFO <subcommand> arg arg ... arg. Subcommands are:
			2) CONSUMERS <key> <groupname>  -- Show consumer groups of group <groupname>.
			3) GROUPS <key>                 -- Show the stream consumer groups.
			4) STREAM <key>                 -- Show information about the stream.
			5) HELP                         -- Print this help.

------------------------
Stream	- ����			|
------------------------
	# �洢���
		* stream�е���Ϣ��ʹ��radix tree����֯��, Ϊ�˽�ʡ�ڴ�, streamʹ����"��ڵ�"�ĸ���
		* ����stream������ɵ���Ϣ���б���, Ȼ�����һ��radix tree�Ľڵ���, ��һ����Ϣ��һ��macro node����ʾ
		* �����stream���ڲ�, ������һ����Ϣ����һ���ڵ��ʾ
		* �෴�ģ�����һ����ڵ�����������Ϣ, ��ô�����ڵ��ܹ���Ŷ�������Ϣ��, ������redis�������ļ��н�������

		stream-node-max-bytes 4096
			* һ���ڵ����洢��������
			
		stream-node-max-entries 100
			* һ���ڵ����洢��������Ϣ
	

