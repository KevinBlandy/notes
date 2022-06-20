--------------------------
��ѯ
--------------------------
	# ����İ�
		* ֻ��󶨵������ֶ�
		* Ĭ�ϵ��кͶ����ֶ�ƥ�������: �ֶ�����Сд����������ƥ��
		* ����ͨ�� db.MapperFunc ���޸��������
		* ���Ӱ�쵽StructScan,SliceScan, MapScan ����
		* Ҳ����ͨ�� '`db' ע����ָ��������
			type Place struct {
				Country       string
				City          sql.NullString
				TelephoneCode int `db:"telcode"`
			}
			 
			rows, err := db.Queryx("SELECT * FROM place")
			for rows.Next() {
				var p Place
				err = rows.StructScan(&p)
			}
	
	
	# IN ��ѯ
		type User struct {
			Id   int
			Name string
		}

		// ������� IN �Ĳ�ѯ��Ĭ��ʹ�� ? ��Ϊռλ��
		query, args, err := sqlx.In("SELECT `id`, `name` FROM `user` WHERE `create_at` < ? and `name` in (?) and `id` IN (?) and `enabled` = ? ", time.Now(), []string{"JPA", "MYBATIS"}, []int{15, 16}, true)
		if err != nil {
			log.Fatalf(err.Error())
		}
		log.Println(query, args, err) 

		// ���°�Ϊ��ǰ���ݿ�֧�ֵ�ռλ���ͣ���Щ���ݿⲻ֧��?��
		query = db.Rebind(query)

		// ִ�м�������ȡ������ֵ
		var ret []User
		err = db.Select(&ret, query, args...)
		if err != nil {
			log.Fatalln(err.Error())
		}
		log.Println(ret)
	
	# Named ��ѯ
		* ���� name ��ѯΪ ? ��ѯ
			var params = map[string]interface{}{
				"createAt": time.Now(),
				"names":    []string{"JPA", "MYBATIS"},
				"ids":      []int{15, 16},
				"enabled":  true,
			}

			query, args, err := sqlx.Named("SELECT `id`, `name` FROM `user` WHERE `create_at` < :createAt and `name` in (:names) and `id` IN (:ids) and `enabled` = :enabled ", params)
			log.Println(query, args, err)
			// SELECT `id`, `name` FROM `user` WHERE `create_at` < ? and `name` in (?) and `id` IN (?) and `enabled` = ?  [2022-06-20 12:45:52.0977691 +0800 CST m=+0.008774201 [JPA MYBATIS] [15 16] true] <nil>
		
		* Ҳ���԰Ѳ������ɽṹ��
			var params = struct {
				CreateAt time.Time `db:"createAt"`
				Names    []string  `db:"names"`
				Ids      []int     `db:"ids"`
				Enabled  bool      `db:"enabled"`
			}{
				CreateAt: time.Now(),
				Names:    []string{"JPA", "MYBATIS"},
				Ids:      []int{15, 16},
				Enabled:  true,
			}
		
