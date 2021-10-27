----------------------------
�첽����					|
----------------------------
	# jdk�� Future
		* ֻ��ͨ���ֶ��ķ�ʽ���ִ�н��,���һ�����

	# ChannelFuture
		* ͨ���������ķ�ʽ,�Իص��ķ�ʽ����ȡִ�н��,����Ҫ�ֶ����,��������
		* ChannelFutureListener �Ļص����� operationComplete(F future) ����IO�߳�ȥִ�е�,���Բ�Ҫ������ִ�к�ʱ�Ĳ���,����ʹ���̳߳�ȥִ��

		
	# ChannelPromise
		* �̳��� ChannelFuture ,Promise
		* �����ɿ�����ȥ�����Ƿ�ɹ�,����ʧ��
		* ����
			DefaultPromise<String> promise = new DefaultPromise<String>(GlobalEventExecutor.INSTANCE);

--------------------------------
ChannelFuture					|
--------------------------------
	# ����
		Channel channel();
			* ���ع�����channel

		@Override
		ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener);
			* ���һ������

		@Override
		ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);
			* ��Ӷ������

		@Override
		ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener);
			* �Ƴ�һ������

		@Override
		ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);
			* �Ƴ��������

		ChannelFuture sync()
			* ͬ��,�߳�����,ֱ���������
		
		Throwable cause()
			* �����쳣��Ϣ
		
		boolean isSuccess();
			* �Ƿ�����ɹ�
		
		 boolean isVoid();
	
	# ChannelFutureListener 
		* �ռ̳��˽ӿ�:GenericFutureListener
		* Ԥ������N��ʵ��
			CLOSE
				* ��ɺ�ر�����
			CLOSE_ON_FAILURE
				* ����׳����쳣,�ر�����
			FIRE_EXCEPTION_ON_FAILURE

--------------------------------
ChannelPromise					|
--------------------------------
	# �̳��� ChannelFuture ,Promise
	# ��������һ������:��д

		@Override
	    ChannelPromise setSuccess(Void result);

		@Override
		ChannelPromise setFailure(Throwable cause);

		ChannelPromise setSuccess();

		boolean trySuccess();

		ChannelPromise unvoid();