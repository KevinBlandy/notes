
---------------------------
Go app ��Ŀ¼�ṹ
---------------------------
	<��Ŀ��>
	����README		# ˵��
	����LICENSE		# �ַ�Э��
	����AUTHORS		# ����
	����<bin>
		����calc
	����<pkg>
		����<linux_amd64>
			����simplemath.a
	����<src>	
		����<calc>
			����calc.go
		����<simplemath>
			����add.go
			����add_test.go
			����sqrt.go
			����sqrt_test.go

	
	* pkg��bin�������ֶ������������ҪGotool�ڹ��������л��Զ�������ЩĿ¼


----------------------
��ƽ̨����
----------------------
	# ��ƽ̨����(�������)
		SET CGO_ENABLED=0
			* ����CGO
			* ������벻֧�� CGO ����Ҫ����

		SET GOOS=linux
			* Ŀ�����ϵͳ
			* ��
				windows
				linux
				darwin(unix/mac)

		SET GOARCH=amd64
			* Ŀ�괦�����ܹ�
			* ��
				amd64
				arm64
	
	
		* ֻ��Windowsƽ̨�£����ñ�����Ҫ SET ָ���������Ҫ
