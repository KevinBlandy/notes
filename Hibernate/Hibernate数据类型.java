
————————————————————————————
一,Hiernate数据类型			|
————————————————————————————
Hibernate映射类型
内置映射类型
Hibernate		java					sql			取值
-------------------------------------------------------------
基本数据类型,一般使用Hibernat指定的类型						 |
-------------------------------------------------------------
int/Integer		int/java.lang.Iteger	INTEGER		4
long			long/java.lang.LONG		BIGINT		8
short			short/Short				SMALLINT	2
byte			byte/Byte				TINYINT		1
float			float/Float				FLOAT		4
double			double/Double			DOUBLE		8
big_decimal		java.math.BigDecimal	NUMERIC		8位包含2位小数部分
character		car/character String	CHAR(1)		定长字符
boolean			boolean/Boolean			BIT			布尔
yes_no			boolean/Boolean			CHAR(1)		布尔
true_false		boolean/Boolean			CHAR(1)		布尔
-------------------------------------------------------------
java时间和日期类型(需要记住)								 |
-------------------------------------------------------------
date			util.Date.sql.Date		DATE		YYYY-MM-DD		   ---> 年月日,没有时分秒适用于生日字段
time			util.Date.sql.Time		TIME		HH:mm:ss
timestamp		util.Date/sql.timestamp	DATETIME	YYYYMMDDHHmmss	[已测,默认]---> 年月日时分秒,时间戳,用于用户的注册时间,商品的订单时间
calendar		java.util.Calendar		TIMESTAMP	YYYYMMDDHHmmss
calendar_date	java.util.Calendar		DATE		YYYY-MM-DD
-------------------------------------------------------------
大数据对象类型												 |
-------------------------------------------------------------
binary			byte[]					MEDIUMBLOB(BLOB)	[已测,默认]
text			String					LONGTEXT			[已测,默认]
serializable	实现类					BARBINARY(BLOB)
clob			sql.Clob				CLOB
blob			sql.Blob				BLOB

