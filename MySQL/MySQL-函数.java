------------------------
MYSQL-����				|
------------------------

------------------------
MYSQL-ϵͳ�Դ�����		|
------------------------	
	# MYSQL�����еĺ��������з���ֵ��,��ô����˵������ select ���õ�
	# MYSQL�ַ����Ĳ�����λ,�������ǲ����ַ���Ϊ��λ��.

	ifnull(����,���ֵ);
		* selct ifnull(phone,'δ֪');,���phone�ֶ�Ϊ��,��ô����Ϊδ֪
	/** ͳ����� **/
	rand()
		* ����һ��0-1֮��������
	floor()
		* ����ȡ��,����ֱ�Ӹɵ�С����.��������
	ceil()
		* ����ȡ��
	count();
		* ����ĳ���ֶε�������Ч����,������null
		* �������ʹ�����ֲ���
			1,*		:����ͳ�Ƽ�¼
			2,�ֶ�	:�����ֶ�,�����null,�򲻲���ͳ��
		* ���������ֵ.��ʵ��*���!������ȫ��ͬ
	max(�ֶ�);
		* ͳ�Ʒ������,���ֵ
	min(�ֶ�);
		* ��Сֵ
	avg(�ֶ�);
		* ��Сֵ
	sum(�ֶ�);
		* ĳ���ֶ�ֵ�ĺ�
		* null,һ�ɵ���0�����м���
		* ���varchar�����ֵ��ַ���,��ô���������.����Ƿ����ֵ��ַ���.��ô����0������
		
	/** ʱ����� **/
	curdate();
		* ��ȡ��ǰ����,��ʽ:2016-06-12
	now();
		* ��ȡ��ǰʱ��,��ʽ:2016-06-12 22:53:30
	unix_timestamp()
		* ����unixʱ���
	
	DATE_FORMAT()
		* ��ʽ��ʱ��
			-- ָ�����ڿ�ʼ
			SELECT DATE_FORMAT('2023-05-13','%Y-%m-%d %H:%i:%s.%f') 		-- 2023-05-13 00:00:00.000000
			-- ָ�����ڽ���
			SELECT DATE_FORMAT('2023-05-13','%Y-%m-%d 23:59:59.999999999')	-- 2023-05-13 23:59:59.999999999

		* ��ʽ
			%Y	�꣬4 λ
			%m	�£���ֵ(00-12)
			%d	�µ��죬��ֵ(00-31)
			%H	Сʱ (00-23)
			%i	���ӣ���ֵ(00-59)
			%s	��(00-59)
			%f	΢��


	/** �ַ������ **/
	concat('1','2');
		* �ַ���ƴ��
		* select concat(name,job) from ����;[�������ֶε��ַ���ƴ�Ӻ��ѯ����]
		* select ..from ..where name like concat('%',concat('kevin','%'));	//mybatisģ����ѯ
	substring(str,x,y);
		* �ַ�����ȡ.��ָ���ַ�����ָ���±꿪ʼ,ȡ���ٳ���
		* 'ע��:'�ú������±��Ǵ�1��ʼ.('��Ϊ0���� false,����������ǲ����õ�')
	char_length(str);
		* ��ȡ��ָ���ַ����ĳ���,'�ַ�����'
	length(str1,str2);
		* ��ȡ��ָ���ַ����ų���,'�ֽڳ���'
		* �ַ�����Ӱ�쵽���.
	instr(str);
		* �ж��ַ���(str2)�Ƿ���ĳ���ַ���(str1)�д���
		* �������,�򷵻�����ڵ�λ��(�±��1��ʼ)
		* ���������,�򷵻�0
	lpad(str,len,in);
		* '�����',���ַ�������ָ������䷽ʽ,��䵽ָ���ĳ���
		* ����,�ַ���̫����.��Ҫ���쵽��λ,�����������ɶ����
	insert(str,start,len,newStr);
		* �滻,�ҵ�'Ŀ��λ��''ָ������'���ַ���,�滻Ϊ'Ŀ���ַ���'
	strcmp(stra,strb);
		* �Ƚ����ַ�����С..�����ֵ�����.A > B
		* ���a > b ���� 1,��֮���� -1 ��ȷ��� 0
	
	find_in_set(str,list);
		* ��� str ������ list ��,���� true
			SELECT find_in_set(123,'123,456,788') as result; // 1

	//�������
	md5("�����ܵ��ַ�");
		* MD5���ܺ���,������.ƽʱ�õ�
	password("�����ܵ��ַ�");
		* ר�Ź�MYSQL�õ�
	sha1("�����ܵ��ַ�");
		* ���Ҳ��һ�����ܺ���������������Ŀ
	

	//IP ת��
	INET_ATON()
		* ��IP�ַ���ת��Ϊint
	
	INET_NTOA()
		* ��INTת��Ϊ�ַ���ip
	
		* ipת���������������ڿ��Է�����ٵļ���
			SELECT * FROM `table` WHERE `ip` BETWEEN INET_ATON('192.168.0.1') AND INET_NTOA('192.168.0.255');

	
------------------------
MYSQL-�Զ��庯��		|
------------------------
	# ������Ҫ��
		1,������
		2,�����б�
		3,����ֵ
		4,������(������)
	# ϵͳ�������Զ��庯��������һëһ����
	# ����������ָ�����ݿ��,ֻ���ں������ڵ����ݿ��²���ʹ�øú���
	# ��Ҫ����һ������:log_bin_trust_function_creators = 1
	# �﷨
		create function [������] ([�β��б�]) returns [Ҫ���ص���������] 
		begin
			//������
			return [����ֵ]		//һ��Ҫ�����涨�����������һ��
		end
	# ������ص�����������VARCHAR,�򻹿�������ַ���
		 create function [������] ([�β��б�]) returns [Ҫ���ص���������] CHARSET utf8
------------------------
MYSQL-��������			|
------------------------
	# ������Ϊ����:����ʱ��Ĳ���(�β�),ʹ��ʱ��Ĳ���(ʵ��)
	# �β�:
		* Ҫ�����ָ����������
		create function demo([�β�����] [�ֶ�����]) returns [��������]...
	# �ں������ڲ��������,���ʹ����@,��ô�ں�������Ҳ���Է���,�����ȫ�ֱ���

------------------------
MYSQL-������			|
------------------------
	# MYSQL�е���������JS�е���������һëһ����
	# ȫ�ֱ���,�������κεط�ʹ��
	# �ֲ�����,ֻ���ں����ڲ�ʹ��
	# ȫ�ֱ���
		* ʹ�� set �ؼ���,ʹ�� @ ���ű�ʶ
	# �ֲ�����
		* ʹ�� declare �ؼ�������,û�� @ ����
		* ���еľֲ�����������,����'�ں����忪ʼ֮ǰ'
		* declare [������] [����] default [Ĭ��ֵ];
		* �޸ľֲ�����ҲҪʹ�õ�set�ؼ���
		* set [������] = [ֵ];
	
------------------------
MYSQL-ɾ������			|
------------------------
	# �����ǲ����޸ĵ�,ֻ����ɾ��,������
	# ϵͳ�������ܱ�ɾ��
	# drop function [������];


------------------------
MYSQL-�鿴����			|
------------------------
	# �鿴���к���
		show function status;
		* ����Ҳ���ԼӸ� like ʵ��ģ��ƥ��
		* ����ʾ�����е��Զ��庯��,�����ǲ��ڱ����ݿ���.Ҳ�ܿ�,���ǲ�����
	# �鿴�������
		show create function [������];
	

------------------------
MYSQL-�����ĳ���		|
------------------------
	# ��ȡ 0 - 9 �������
		select floor(rand() * 10);

	# ƴ���ַ���,һ������ģ����ѯ
		concat('%',concat('kevinBlandy','%'));
		* һ������ģ����ѯ


------------------------
MYSQL-�����ĳ���		|
------------------------
CREATE FUNCTION `getChildList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sChildList VARCHAR(1000);
      DECLARE sChildTemp VARCHAR(1000);
      SET sChildTemp =cast(rootId as CHAR);
      WHILE sChildTemp is not null DO
        IF (sChildList is not null) THEN
          SET sChildList = concat(sChildList,',',sChildTemp);
    ELSE
      SET sChildList = concat(sChildTemp);
    END IF;
        SELECT group_concat(id) INTO sChildTemp FROM user_role where FIND_IN_SET(parentid,sChildTemp)>0;
      END WHILE;
      RETURN sChildList;
    END;

/*��ȡ�ӽڵ�*/
/*����: 1��select getChildList(0) id; 2��select * 5From user_role where FIND_IN_SET(id, getChildList(2));*/
 
 
CREATE FUNCTION `getParentList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sParentList varchar(1000);
      DECLARE sParentTemp varchar(1000);
      SET sParentTemp =cast(rootId as CHAR);
      WHILE sParentTemp is not null DO
    IF (sParentList is not null) THEN
         SET sParentList = concat(sParentTemp,',',sParentList);
    ELSE
     SET sParentList = concat(sParentTemp);
    END IF;
        SELECT group_concat(parentid) INTO sParentTemp FROM user_role where FIND_IN_SET(id,sParentTemp)>0;
      END WHILE;
      RETURN sParentList;
    END;

/*��ȡ���ڵ�*/
/*����: 1��select getParentList(6) id; 2��select * From user_role where FIND_IN_SET(id, getParentList(2));*/

CREATE FUNCTION `getChildList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sChildList VARCHAR(1000);
      DECLARE sChildTemp VARCHAR(1000);
      SET sChildTemp =cast(rootId as CHAR);
      WHILE sChildTemp is not null DO
        IF (sChildList is not null) THEN
          SET sChildList = concat(sChildList,',',sChildTemp);
    ELSE
      SET sChildList = concat(sChildTemp);
    END IF;
        SELECT group_concat(reply_id) INTO sChildTemp FROM jw_dynamic_reply where FIND_IN_SET(parent_id,sChildTemp)>0;
      END WHILE;
      RETURN sChildList;
    END;