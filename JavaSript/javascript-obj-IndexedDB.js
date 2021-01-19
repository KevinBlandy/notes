

------------------------
indexedDB				|
------------------------
	# 创建db
		window.indexedDB.open(dbName,version);
	
	# 创建index
		let objectStore = db.createObjectStore(objectStoreName,options);
	
	# 创建事务
		let transaction = db.transaction(objectStoreName,enum);
		let objectStore = transaction.objectStore(objectStoreName);
	
	#  CRUD
		objectStore.add(item);
		objectStore.get(id);
		objectStore.put(row);
		objectStore.delete(id);
	
	# 遍历
		objectStore.openCursor();
	
	# range
		...

------------------------
indexedDB-demo			|
------------------------
	//db连接
	let db = null;
	//db名称
	let dbName = 'database';
	//index名称
	let objectStoreName = 'users';

	//打开链接
	let opener = window.indexedDB.open(dbName,1);
	opener.onsuccess = function(event){
		db = event.target.result;
	}

	//在第一次创建db或者是版本号有修改的时候会执行
	opener.onupgradeneeded = function(event){
		let db = event.target.result;
		
		//创建数据库存储对象
		let objectStore = db.createObjectStore(objectStoreName,{
			keyPath:'id',
			autoIncrement:true
		});
		
		//定义存储对象的数据项
		objectStore.createIndex('id','id',{
			//唯一约束
			unique:true
		});
		objectStore.createIndex('name','name');
		objectStore.createIndex('age','age');
	}

	function create(db,item){
		//创建事务
		let transaction = db.transaction(objectStoreName,'readwrite');//readonly readwrite versionchange
		//通过事务,打开存储对象
		let objectStore = transaction.objectStore(objectStoreName);
		//插入记录
		objectStore.add(item);
	}

	function update(db,id){
		let transaction = db.transaction(objectStoreName,'readwrite');
		let objectStore = transaction.objectStore(objectStoreName);
		//根据id检索记录,返回游标
		let cursor = objectStore.get(id);
		cursor.onsuccess = function(event){
			//成功检索结果集,如果检索失败,返回 undefined
			let row = event.target.result;
			//修改记录值
			row.name = 'new name';
			//使put更新记录
			objectStore.put(row);
		}
	}

	function remove(db,id){
		let transaction = db.transaction(objectStoreName,'readwrite');
		let objectStore = transaction.objectStore(objectStoreName);
		//通过id删除记录
		objectStore.delete(id);
	}

	function foreach(db){
		let transaction = db.transaction(objectStoreName,'readwrite');
		let objectStore = transaction.objectStore(objectStoreName);
		let cursor = objectStore.openCursor();
		cursor.onsuccess = function(event){
			let cursor = event.target.result;
			if(cursor){
				let value = cursor.value;
				//遍历到的值
				console.log(value);
				cursor.continue();//继续遍历
			}else{
				//遍历完毕
			}
		}
	}	


	function rangeEach(db,start,end){
		let transaction = db.transaction(objectStoreName,'readwrite');
		let objectStore = transaction.objectStore(objectStoreName);
		/*
			bound(start,end,lowerOpen,upperOpen);
				id范围内,后面俩bool参数可以设置是否包含开始和结束(默认都为true)
			only(id);
				仅仅是指定的id
			lowerBound(id);			
				小余参数的id
			upperBound(id);			
				大于参数的id
		*/
		//创建范api,表示仅仅检索id start - end 之间的数据
		let range = IDBKeyRange.bound(start,end);
		//判断range检索的范围,是否包含了id = 5的记录,返回 bool
		let include = range.includes(5);
		//通过范围api创建游标
		let cursor = objectStore.openCursor(range);
		cursor.onsuccess = function(event){
			let cursor = event.target.result;
			if(cursor){
				let value = cursor.value;
				//遍历到的值
				console.log(value);
				cursor.continue();//继续遍历
			}else{
				//遍历完毕
			}
		}
	}