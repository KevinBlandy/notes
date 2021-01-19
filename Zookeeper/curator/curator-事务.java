
---------------------
 事务				 |
 --------------------
 
	// 第一个事务操作
	CuratorOp createOp = client.transactionOp().create().forPath("/a/path", "some data".getBytes());
	// 第二个事务操作
	CuratorOp setDataOp = client.transactionOp().setData().forPath("/another/path", "other data".getBytes());
	// 第三个事务操作
	CuratorOp deleteOp = client.transactionOp().delete().forPath("/yet/another/path");

	//把多个操作集合为一个事务操作,并且执行,要么全部成功,要么全部失败,返回每个操作的执行结果
	Collection<CuratorTransactionResult> results = client.transaction().forOperations(createOp, setDataOp,deleteOp);
	
	for (CuratorTransactionResult result : results) {
		System.out.println(result.getForPath() + " - " + result.getType());
	}