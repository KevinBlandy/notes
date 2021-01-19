----------------------
MyBatis-枚举			|
----------------------
	* MyBatis查询若想映射枚举类型，则需要从 EnumTypeHandler 或者 EnumOrdinalTypeHandler 中选一个来使用
		1. EnumOrdinalTypeHandler	
			* 这个转换类使用了枚举类的 ordinal (枚举对象的位置)属性作为数据库存储信息
			* 由于ordinal属性是int类型的，按照官网的说明数据库中对应资源应该是int或double类型的
			* 但是个人测试过程中MYSQL的varchar字段也可以存储。

		2. EnumTypeHandler			
			* 默认的
			* 是按照枚举的名字来存取的，对应数据库的设计为 直接使用枚举名。
	
	* 修改默认的枚举处理器(mybatis-cofig.xml), 实用属性:defaultEnumTypeHandler
		<settings>
			<setting name="defaultEnumTypeHandler" value="org.apache.ibatis.type.EnumOrdinalTypeHandler"/>
		</settings>

	* 如果有特殊需求,可以考虑自定义消息类型处理器来完成
	

1,枚举是DTO的属性
	public enum UserStatus {  
		/** 无效*/  
		DISABLED(0),  
		/** 有效 */  
		AVAILABLE(1);  
		private int status;  
		UserStatus(int status){  
			this.status = status;  
		}  
		public int getStatus() {  
			return status;  
		}  
	}  

	<insert id="addUser" parameterType="User">  
		INSERT INTO t_user(USER_ID,USER_NAME,LOGIN_NAME, LOGIN_PASSWORD,USER_STATUS,CREATE_TIME,UPDATE_TIME)  
		VALUES(  
			#{user_id},  
			#{user_name},  
			#{login_name},  
			#{login_password},  
			#{user_status, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler},  
			#{create_time},  
			#{update_time}  
			)  
	</insert>  

	# 指定Handler = typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler


----------------------------
MyBatis-自定义通用枚举类处理|
----------------------------
	BaseEnum					
	# 基础的接口
	# 所有枚举类都应该实现该接口,就可以完成数据转换
		public interface BaseEnum<E extends Enum<?>, T>{

			T getValue();

			String getDisplayName();
		}

	UniversalEnumHandler		
	# 通用枚举类处理器
		import org.apache.ibatis.type.BaseTypeHandler;
		import org.apache.ibatis.type.JdbcType;
		import xin.suen.teach.base.BaseEnum;
		import java.sql.CallableStatement;
		import java.sql.PreparedStatement;
		import java.sql.ResultSet;
		import java.sql.SQLException;

		/**
		 * Created by KevinBlandy on 2017/3/29 11:09
		 */
		public class UniversalEnumHandler <E extends BaseEnum<?, ?>> extends BaseTypeHandler<E> {

			private Class<E> type;      //枚举Class

			private E [] enums;         //枚举对象
			

			/**
				该构造方法,系统会自动的注入 type ,也就是泛型 E
			*/
			public UniversalEnumHandler(Class<E> type) {
				if (type == null) {
					//参数为空
					throw new IllegalArgumentException("参数不能为空");
				}
				this.type = type;
				this.enums = type.getEnumConstants();
				if (this.enums == null || this.enums.length == 0){
					//参数非枚举类,或者没有任何枚举值
					throw new IllegalArgumentException(type.getSimpleName() + " 非枚举类,或者没有任何枚举值");
				}
			}

			@Override
			public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
				//获取枚举value值
				Integer value = (Integer)parameter.getValue();
				if(jdbcType == null){
					ps.setObject(i,value);
				}else {
					ps.setObject(i,value, jdbcType.TYPE_CODE);
				}
			}

			@Override
			public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
				//从结果集中以,列名获取值
				Integer i = rs.getInt(columnName);
				return i == null ? null : getEnumuByValue(i);
			}

			@Override
			public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
				//从结果集中以,位置获取值
				Integer i = rs.getInt(columnIndex);
				return i == null ? null : getEnumuByValue(i);
			}

			@Override
			public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
				//存储过程
				Integer i = cs.getInt(columnIndex);
				return i == null ? null : getEnumuByValue(i);
			}
			/**
			 * 根据valu值,获取枚举对象
			 * @param value 数据库中存储的自定义value属性
			 * @return 根据value对应的枚举类
			 */
			private E getEnumuByValue(Integer value) {
				for(E e : enums) {
					if(e.getValue().equals(value)) {
						return e;
					}
				}
				throw new IllegalArgumentException("未知的枚举类型:" + value + ",请核对" + type.getSimpleName());
			}
		}


	
	# 注册
		<typeHandler handler="net.itaem.handler.UniversalHandler"  javaType="net.itaem.less.PersonType" jdbcType="TINYINT"/>  
		* jdbcType 只能是 JdbcType 枚举值中的一个,代表的数据库的类型
		* javaType 表示要转换的JAVA类型
