------------------------
sqlite					|
------------------------
	# Ƕ��ʽ���ݿ�
	# ����
		https://www.sqlite.org/index.html

	
	# �ĵ�
		* ��������
			https://www.sqlite.org/datatype3.html
		
		* SQL�﷨
			https://www.sqlite.org/lang.html
		
		* ���ú���
			https://www.sqlite.org/lang_corefunc.html
		
		* Date �� Time ��صĺ���
			https://www.sqlite.org/lang_datefunc.html

------------------------
sqlite-window��װ		|
------------------------
	# �����ļ�,����ѹ��һ���ļ�����
		sqlite-tools-win32-x86-3240000.zip
		sqlite-dll-win64-x64-3240000.zip
	
	# ����Ŀ¼�ļ�
		sqlite3_analyzer.exe
		sqlite3.exe
		sqldiff.exe
		sqlite3.dll
		sqlite3.def

	# ���Կ������Ŀ¼����������

------------------------
sqlite-Linux��װ		|
------------------------

------------------------
schematab ��
------------------------
	# ÿ�����ݿⶼ������һ��: sqlite_schema ��
		* Ҳ��������Ϊ��sqlite_master/sqlite_temp_schema/sqlite_temp_master

		CREATE TABLE sqlite_schema(
		  type text,
		  name text,
		  tbl_name text,
		  rootpage integer,
		  sql text
		);
		
		* type
			���� 'table'(��ͨ��/�����), 'index', 'view', �� 'trigger' ������ȡ���ڶ���Ķ������͡�
		
		* name
			���������
		
		* tbl_name
			* �����������ı����ͼ�����ơ�
			* ����һ�������ͼ��tbl_name����name�еĸ���
			* ����һ��������˵��tbl_name�Ǳ������ı�����ơ�
			* ����һ����������tbl_name�д洢�˵��´����������ı����ͼ�����ơ�
		
		* sql
			�����ö����SQL�ı�