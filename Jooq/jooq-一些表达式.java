-----------------------------
һЩ���ʽ
-----------------------------
	# �����ֻ������м�4λ
		Field<String> phone = concat(substring(ADMIN.ACCOUNT, 1, 3), inline("****"), substring(ADMIN.ACCOUNT, 8)).as("phone");
		Field<String> phone = overlay(ADMIN.ACCOUNT, "****", 4).as("phone");
