-----------
ָ��		|
-----------
	# ʹ�� FTL��ǩ������ ָ��
	# �����ǩû��Ƕ������(�ڿ�ʼ��ǩ�ͽ�����ǩ֮�������),��ô���Ժ��Թرձ�ǩ
	# ��������ָ��
		* �Զ���ָ��,ʹ��@
			<@my parameters ></@my>
		* ϵͳָ��,ʹ��#
			<#if parameters ></if>
	
	# FreeMarker�����FTL��ǩ�ж���Ŀհױ��
	



------------
assign		|
------------
	# ����һ���µı���, �����滻һ���Ѿ����ڵı���
	# ע����������������Ա�����/�滻 (Ҳ����˵�㲻�ܴ���/�滻 some_hash.subvar, ���� some_hash)
		<#assign seq = ["foo", "bar", "baz"]>
		<#assign x++>
		<#assign
			seq = ["foo", "bar", "baz"]
			x++
		>
	# ���ƿ�����˫����
	# �������
		
------------
compress	|
------------
	# �Ƴ�����Ŀհ��Ǻ����õ�,����׽��ָ���������ɵ�����,Ȼ����С���в���ϵĿհ����е�һ�������Ŀհ��ַ�
	# �������������а������з�����һ�οռ�,��ô��������ַ�Ҳ����һ�� ���з�, ��ͷ�ͽ�β�Ŀհ׷��ᱻ�Ƴ�
	# �е��ѹ�����ݵ���˼
	# ����
		single_line 
			* boolֵ,�����ֵΪ true,��������ж�һ���Ƴ�
	
		<#compress>
		  ...
		</#compress>

------------
escape		|
------------
	# ��Ը�ָ���ǩ��${}��������ݽ���ָ���ı���
	# ����һ�������д���Ʊ��ʽ�ĺܷ���ķ���
		* ������Ӱ�����ַ�����ʽ�Ĳ�ֵ(������ <#assign x = "Hello ${user}!">)
		* ����,��Ҳ����Ӱ����ֵ��ֵ (#{...})

	# Demo
		<#escape x as x?html>
			First name: ${firstName}
			Last name: ${lastName}
			Maiden name: ${maidenName}
		</#escape>

		* x ��ʾ${}���������
		* x?html ��������ݵĴ���,��ʾ��html����ȥ����,Ҳ���Ի����Լ�����ĺ���ɶ��
		* ����������x��Ϊmap��key
			<#escape x as skills[x]>....

		* ��ʵ����
			First name: ${firstName?html}
			Last name: ${lastName?html}
			Maiden name: ${maidenName?html}
		
	
	# �ر�ת��,������escape��Ƕ��#noescapeָ��
		<#escape x as x?html>
			First name: ${firstName}
			<#noescape>Message: ${message}</#noescape>
		</#escape>
		
		 * message ���ᱻת��
		
	# ת�����Ƕ�׶��
		<#escape x as x?html>
		  Customer Name: ${customerName}
		  Items to ship:
		  <#escape x as itemCodeToNameMap[x]>
			${itemCode1}
			${itemCode2}
			${itemCode3}
			${itemCode4}
		  </#escape>
		</#escape>

		* �ϴ������
		  Customer Name: ${customerName?html}
		  Items to ship:
			${itemCodeToNameMap[itemCode1]?html}
			${itemCodeToNameMap[itemCode2]?html}
			${itemCodeToNameMap[itemCode3]?html}
			${itemCodeToNameMap[itemCode4]?html}

		* Ƕ���ת��������ʹ�÷�ת��ָ��ʱ,������������һ�������㼶��ת��
		* ���,Ϊ�����������ת����������ȫ�ر�ת��,����Ҫʹ������Ƕ�׵ķ�ת��ָ��


------------
ftl			|
------------
	# ����ģ�������һЩ����
		<#ftl param1=value1 param2=value2 ... paramN=valueN>
		
		* �������ƹ̶�
		* ����ֵ�ǳ���,����ʹ�ñ���
	
	# ����
		encoding
			* ����
		
		strip_whitespace
			* �⽫����/�ر� �հװ���
			* �Ϸ���ֵ�ǲ���ֵ���� true �� false (Ϊ�����¼���,�ַ��� "yes","no", "true","false" Ҳ�ǿ��Ե�)
			* Ĭ��ֵ�� true��

	strip_text
		* ��������ʱ,��ģ�屻����ʱģ�������ж����ı����Ƴ�
		* �������Ӱ���,ָ��,���ֵ�е��ı�
		* �Ϸ�ֵ�ǲ���ֵ���� true �� false (Ϊ�����¼���,�ַ��� "yes","no", "true","false" Ҳ�ǿ��Ե�)
		*  Ĭ��ֵ(Ҳ���ǵ��㲻ʹ���������ʱ)�� false


	strict_syntax
		* ��Ὺ��/�ر�"�ϸ���﷨"
		* �Ϸ�ֵ�ǲ���ֵ���� true �� false (Ϊ�����¼���,�ַ��� "yes","no", "true","false" Ҳ�ǿ��Ե�)
		* Ĭ��ֵ(Ҳ���ǵ��㲻ʹ���������ʱ)�������ڳ���Ա�� FreeMarker ������, ���Ƕ��µ���Ŀ��Ӧ���� true��


	attributes
		* ���ǹ���ģ����������(��-ֵ��)�Ĺ�ϣ��, ���Ե�ֵ��������������(�ַ���,����,���е�)��
		* reeMarker ���᳢��ȥ������Եĺ���,�����ɷ�װ FreeMarker(����WebӦ�ÿ��)��Ӧ�ó��������
		* ���,��������Ե�����������������Ӧ��(WebӦ�ÿ��)������
		* ����ͨ������ Template ����� getCustomAttributeNames �� getCustomAttribute ���� (�� freemarker.core.Configurable �̳ж���)�������
		* �統ģ�屻����ʱ,���� Template �����ģ������, ���Կ���������ʱ�䱻��ȡ,��ģ�岻��Ҫ��ִ�С� �����ᵽ�ķ�������δ��װ������ֵ,Ҳ����˵, ʹ�� FreeMarker ����������,�� java.util.List��

------------
global		|
------------
	# ����ȫ�ֱ���,�����������ռ��ж��ɼ�
	# �﷨
		<#global name=value>
		<#global name1=value1 name2=value2 ... nameN=valueN>
	# ����������ư��������ַ�,������""����

--------------------
if, else, elseif	|
--------------------
	# ̫����,��˵
	# �жϾ���ʹ������
		<#if (x > 0)>
		</#if>

		* ��Ȼ�Ļ� ><�ᱻ����Ϊָ��Ľ�����
	
	# ���յĽ��������һ��bool����,��Ȼ�쳣

--------------------
import				|
--------------------
	# ���ڵ���һ����
		<#import "/libs/mylib.ftl" as my>
		<@my.foo/>
		${my.func("123")}
	

--------------------
include				|
--------------------
	# ����
	# �﷨
		<#include path>
		<#include path options>

		* path��ʾ·��
		* ptions һ������������ѡ��
			encoding		����
			parse			�Ƿ���ftl����,���Ϊfalse������ݵ������ַ���
			ignore_missing	�Ƿ�����쳣,���Ŀ��Ŀ���쳣,�򲻻����κ����
	
	# ���ػ�ģ�����
		
		*  ����ģ��ʹ�ñ��ػ� en_US ������, ��������Ӣ��,����������ģ��ʱ

			<#include "footer.ftl">
			
			//����ʵ���Ͼͻᰴ�����˳��Ѱ��ģ��

			footer_en_US.ftl,
			footer_en.ftl
			footer.ftl
		
		* ͨ������رձ��ؼ�������
			//���ñ��ػ�ģ�����
			configuration.setLocalizedLookup(false);
		
		*  Ҳ���Կ���������ҵĹ���
			configuration.setTemplateLookupStrategy(TemplateLookupStrategy);

--------------------
noparse				|
--------------------
	# ����Ը�ָ���м������ftl���ʽ,�������ֱ�ӵ����ַ���ԭ�����
	# ģ��
		<#noparse>
		  <#list animals as animal>
		  <tr><td>${animal.name}<td>${animal.price} Euros
		  </#list>
		</#noparse>
	# ���
		Example:
		--------

		  <#list animals as animal>
		  <tr><td>${animal.name}<td>${animal.price} Euros
		  </#list>

--------------------
setting				|
--------------------
	# ������Ӱ�� FreeMarker ��Ϊ��ֵ
		<#setting name=value>
	
	# �������õ�����
		locale
		number_format
		boolean_format
		date_format
		time_format
		datetime_format
		time_zone
		sql_date_and_time_time_zone 
		url_escaping_charset
		output_encoding
		classic_compatible

--------------------
#switch				|
--------------------
	# �ٷ�˵��������,������if elseif
		<#switch value>
		  <#case refValue1>
			...
			<#break>
		  <#case refValue2>
			...
			<#break>
		  <#case refValueN>
			...
			<#break>
		  <#default>
			...
		</#switch>
	
------------------------
t, lt, rt				|
------------------------
	t 
		* ���Ա������׺�β�����пհס�
	lt
		* ���Ա������ײ����еĿհס�
	rt 
		* ���Ա�����β�����еĿհ�