---------------
死锁
---------------
	
	# 死锁复现
		* 线程1
			start TRANSACTION;
			SELECT * FROM `user` WHERE id = 1 for update;
			SELECT * FROM `user` WHERE id = 2 for update;
			COMMIT;
		
		* 线程2
	
			start TRANSACTION;
			SELECT * FROM `user` WHERE id = 2 for update;
			SELECT * FROM `user` WHERE id = 1 for update;
			COMMIT;

			// - Deadlock found when trying to get lock; try restarting transaction
		