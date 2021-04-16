-----------------------
Pump
-----------------------
	# Ë®±Ã½Ó¿Ú£¿£º interface Pump

	static <T> Pump pump(ReadStream<T> rs, WriteStream<T> ws)
	static <T> Pump pump(ReadStream<T> rs, WriteStream<T> ws, int writeQueueMaxSize)

	Pump setWriteQueueMaxSize(int maxSize)
	Pump start()
	Pump stop()
	int numberPumped()