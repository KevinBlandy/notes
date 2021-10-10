-------------------
Settings
-------------------
	# ����ʱ���ã���ѡ��
		public class Settings extends SettingsBase implements Serializable, Cloneable, XMLAppendable
	
	# ��org.jooq.conf.Settings����JAXBע�����ͣ�����ͨ�����ַ�ʽ�ṩ�����ã�
		* �� DSLContext ���캯�� ( DSL.using()) �С��⽫���������Ĭ������
		* ��org.jooq.impl.DefaultConfiguration���캯���С��⽫���������Ĭ������
		* �� JVM ����ָ����λ�ã�-Dorg.jooq.settings
		* �� /jooq-settings.xml ����·��
		* ������Ĭ��ֵ��ָ��
			https://www.jooq.org/xsd/jooq-runtime-3.15.0.xsd

		Settings settings = JAXB.unmarshal(new File("/path/to/settings.xml"), Settings.class);

		Settings settings = new Settings();
		settings.setStatementType(StatementType.STATIC_STATEMENT);
		DSLContext create = DSL.using(connection, dialect, settings);
	
	# ����
		public Settings withRenderKeywordCase(RenderKeywordCase value)
			* ������Ⱦ��SQL�йؼ��ִ�Сд
				AS_IS			// ��Ĭ�ϣ������ڴ�����ж���Ĺؼ��֣���Ҫ��Сд��
				LOWER			// Сд
				UPPER			// ��д
				PASCAL			// ���� pascal ��Сд�Ĺؼ��֡�
		
		public Settings withExecuteLogging(Boolean value) 
			* �Ƿ��¼ִ����־
		
		public Settings withMapJPAAnnotations(Boolean value)
			* org.jooq.impl.DefaultRecordMapper֧�ֻ���JPAӳ�䣨���@Table��@Columnע�ͣ���
			* ������Щע�ͻ����һЩ����Ŀ�������Ҫͨ�����仺��������������ʹ������mapJPAAnnotations���ùر�����
			* Ĭ��Ϊ true

		public Settings withParamType(ParamType value) 
			* ����ռλ����������
				INDEXED
				FORCE_INDEXED
				NAMED
				NAMED_OR_INLINED
				INLINED
		
		public Settings withRenderNamedParamPrefix(String value)
			* ռλ����������� named�����������������ǰ׺

		public Settings withRenderFormatted(Boolean value) 
			* �Ƿ��ʽ�����SQL
		
		public Settings withRenderQuotedNames(RenderQuotedNames value)
			* �����ֶε������������
				ALWAYS
				EXPLICIT_DEFAULT_QUOTED
				EXPLICIT_DEFAULT_UNQUOTED
				NEVER

		public Settings withRenderNameCase(RenderNameCase value)
			* �ֶ����ƴ�Сд����
				AS_IS,
				LOWER,
				LOWER_IF_UNQUOTED,
				UPPER,
				UPPER_IF_UNQUOTED;
		

			
				