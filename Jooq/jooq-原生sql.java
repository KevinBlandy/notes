--------------------------
原生SQL查询
--------------------------
	# 使用 fetch 执行原生SQL
		DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
		@NotNull Result<Record> results = dslContext.fetch("SELECT * FROM `admin` WHERE id = ?", 1);
		for (Record record : results){
			Row key = record.fieldsRow();
			for (int i = 0; i < key.size(); i ++){
				String name = key.field(i).getName();
				Object val = record.get(key.field(i));
				System.out.println(name + "=" + val);
			}
		}

		//  SELECT * FROM `admin` WHERE id = 1
