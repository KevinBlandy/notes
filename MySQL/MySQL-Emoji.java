错误提示：

java.sql.SQLException: Incorrect string value: '\xF0\x9F\x90\x82' for column 'content' at row 1

修改 my.cnf或者mysql.ini，重启服务

[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

数据库编码也必须为utf8mb4

查看编码

SHOW VARIABLES WHERE Variable_name LIKE 'character%' OR Variable_name LIKE 'collation%';

* 除了:character_set_system     | utf8   ，以外。都应该是 utf8mb4