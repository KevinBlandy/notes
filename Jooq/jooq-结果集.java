--------------------------------------
�����
--------------------------------------
	# �������õķ���
		fetch();
			* ���� List
		fetchSet();
			* ���� Set

		fetchArray();
			* ��������

		fetchAny();
			* ��ȡ������0����¼�������¼�ж�����ֻ��ȡ��һ��

		fetchOne();
			* ��ȡ��������0��¼�������¼����һ���ᱨ��
		
		Optional<R> fetchOptional()
			* ���ؽ����װΪ Optional
			* ��ȡ��������0��¼�������¼����һ���ᱨ��
		
		fetchSingle()
			* ������ֻ��һ����¼��û�л��߳��������쳣
		
		fetchMap()
			* ���� Map
			* �Ա��ֶ�ֵΪkey������һ�� K:V ��Map����
		
		fetchGroups()
			* ����һ������Map
			* �Ա��ֶ�ֵΪKey������һ�� K:List<V> ��Map����
		

			

	
	# �쳣
		org.jooq.exception.TooManyRowsException
			* ��������ʱ�׳��쳣
		
		org.jooq.exception.NoDataFoundException
			* ������ʱ�׳��쳣


--------------------------------------
���õĽ������װ
--------------------------------------
	# fetch ϵ�з���������fetch/fechInto....����֧�� map() ���������԰� Record ���ת��Ϊ�Լ���װ�Ķ���
		.where(condition)
		.fetch(r -> {
			return r.into(ADMIN);
		})

	# Result ʹ�� into() ���� Pojo��class���԰�����ת��Ϊ����
		dslContext.selectFrom(Tables.ADMIN).where(Tables.ADMIN.ID.eq(UInteger.valueOf(1))).fetch().into(Admin.class);

	# ʹ�ö����Record ת��Ϊ���н���� Record ת��ΪPojo������� Record ����
		* ��� Record Ϊnull������ into ���ָ���쳣��
			dsl.selectFrom(Tables.POST).where(Tables.POST.ID.eq(1L)).fetchOne().into(Post.class); // Record Ϊnull����ָ���쳣
			
		* ����ʹ�����صĴ��� RecordMapper �ķ������
			dsl.fetchOne(i -> i.into(Post.class)); // ���Record Ϊnull���򷵻�null


	# ������ϼ���
		* ����ǽ�����ǰ�����ƽ�̵ģ���ô����ֱ�� into Ϊ���󣬼����ֶ�ͨ�� as �Ͷ���ƥ�伴��

			dslContext.select(adminTable.ID, adminRoleTable.ROLE_ID.as("roleId")).from(adminTable)
				.innerJoin(adminRoleTable).on(adminRoleTable.ADMIN_ID.eq(adminTable.ID))
				.fetch(record -> record.into(AdminDTO.class)) // AdminDTO �̳� Admin��������һ�� private roleId �ֶΣ�ƽ�̵ģ�����ֱ��into
				.stream()
				.forEach(i -> {
					System.out.println("adminId=" + i.getId() + ", roleId=" + i.getRoleId());
				});

		* ���������ݲ���ƽ�̵ģ����в�εģ���ô Record �� into ��������ָ��Ϊ Table����ô���صľ��� Table�� Record ʵ��
		* Ȼ������ TableRecord into ��ָ���Ķ���
		* ���ֶ���װ

			dslContext.select(adminTable.ID, adminRoleTable.ROLE_ID.as("roleId")).from(adminTable)
				.innerJoin(adminRoleTable).on(adminRoleTable.ADMIN_ID.eq(adminTable.ID))
				.fetch(record -> {
					// �Ȱ�record��װ��AdminRecord���ٰ�AdminRecord��װ�����ն���
					AdminDTO adminDTO = record.into(adminTable).into(AdminDTO.class);
					// �Ȱ�record��װ��AdminRoleRecord���ٶ�ȡ��Ҫ�����ݣ���װ�����ն���
					adminDTO.setRoleId(record.into(adminRoleTable).getRoleId().intValue());
					return adminDTO;
				})
				.stream()
				.forEach(i -> {
					System.out.println("adminId=" + i.getId() + ", roleId=" + i.getRoleId());
				});
		
		* �ٷ�һ���ȽϺ�����Demo
			// ���Ӳ�ѯ2����
			Record record = create.select()
								  .from(BOOK)
								  .join(AUTHOR).on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
								  .where(BOOK.ID.eq(1))
								  .fetchOne();

			// �ֱ��ȡ��ͬ���ֶε���ͬ�ı��¼����
			BookRecord book = record.into(BOOK);
			AuthorRecord author = record.into(AUTHOR);

			System.out.println("Title       : " + book.getTitle());
			System.out.println("Published in: " + book.getPublishedIn());
			System.out.println("Author      : " + author.getFirstName() + " " + author.getLastName();

--------------------------------------
�ӳ�
--------------------------------------
	# �ӳ�ץȡ
		// Obtain a Cursor reference:
		try (Cursor<BookRecord> cursor = create.selectFrom(BOOK).fetchSize(1).fetchLazy()) {

			// Cursor has similar methods as Iterator<R>
			while (cursor.hasNext()) {
				BookRecord book = cursor.fetchOne();
				
				Util.doThingsWithBook(book);
			}
		}

--------------------------------------
��ʽ��Ϊ������������
--------------------------------------
	# JSON
		String result = dslContext.selectFrom(ADMIN).fetch().formatJSON();
	
	# CSV
		String result = dslContext.selectFrom(ADMIN).fetch().formatCSV();
	
	# TEXT
	# XML
	# HTML

	