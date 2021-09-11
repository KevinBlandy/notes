
---------------
dao
---------------
	# ʵ��
		public class AdminDao extends DAOImpl<AdminRecord, io.springcloud.jooq.generated.tables.pojos.Admin, UInteger> 
		public class AdminRoleDao extends DAOImpl<AdminRoleRecord, io.springcloud.jooq.generated.tables.pojos.AdminRole, Record2<UInteger, UInteger>>


---------------
�Զ�������ӳ��
---------------
	# ö��ӳ��
		* ����ö��ת����
			import org.jooq.impl.EnumConverter;
			import io.springcloud.domain.enums.Gender;
			public class GenderEnumConverter extends EnumConverter<String, Gender>{
				private static final long serialVersionUID = -3880880972715165871L;
				public GenderEnumConverter() {
					// source ���ͣ�ת����ö������
					super(String.class, Gender.class); // ��� source ���ַ�����������ʵ�����ƣ����source��Number���࣬����orind() ֵ
				}
			}
	
		* ����
			<forcedType>
				<!-- ָ��ö���� -->
				<userType>io.springcloud.domain.enums.Gender</userType>
				<!-- ָ��ת���� -->
				<converter>io.springcloud.jooq.converter.GenderEnumConverter</converter>
				<!-- Ҫת���еı��ʽ������������ -->
				<includeExpression>gender</includeExpression>
				<!-- Ҫת���������ͣ����������� -->
				<includeTypes>.*</includeTypes>
			</forcedType>
	
	# Boolean ����ӳ��
		<forcedType>
			<!-- ����boolean���� -->
			<name>BOOLEAN</name>
			<!-- ���ֶ����ƣ�������������ʽ --> 
			<includeExpression>enabled</includeExpression>
			<!-- �����ͣ�������������ʽ -->
			<includeTypes>.*</includeTypes>
		</forcedType>
	
