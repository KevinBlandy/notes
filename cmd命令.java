
# ��ȡ�ļ���hashֵ
	certutil -hashfile [�ļ�] [hash�㷨]

	* hash�㷨������: md5,sha1,sha256

# ��������������������,maven��������ʧ�������� .lastupdate/_remote.repositories�ļ�
	for /r %i in (*.lastUpdated)do del %i
	for /r %i in (*.repositories)do del %i

	for /r %i in (*.lastUpdated)do del %i & for /r %i in (*.repositories)do del %i

	* ��Ҫ��maven�Ĳֿ�Ŀ¼ִ��

# ʹ�� pause ָ���ÿ���̨ "�밴���������������"

# �޸�cmd���ַ���
	CHCP
		* �鿴��ǰ�ı���Ĭ��ΪGBK(936)
	CHCP 65001
		* ���ñ���Ϊutf-8
		* ���óɹ����������������ʾ,��������һ��cmd�������������

# �鿴�˿�ռ�ý���

	netstat -aon|findstr "8081"


