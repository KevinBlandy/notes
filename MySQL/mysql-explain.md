# Explain
- 一条查询语句在经过MySQL查询优化器的各种基于成本和规则的优化会后生成一个所谓的执行计划
- 执行计划展示了接下来具体执行查询的方式，比如多表连接的顺序是什么，对于每个表采用什么访问方法来具体执行查询等等
- Explain
```
mysql> EXPLAIN SELECT 1;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra          |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
|  1 | SIMPLE      | NULL  | NULL       | NULL | NULL          | NULL | NULL    | NULL | NULL |     NULL | No tables used |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
1 row in set, 1 warning (0.01 sec)
```

## 信息
```sql
id              在一个大的查询语句中每个SELECT关键字都对应一个唯一的id
    * 一个SELECT对应一个ID
    * 每个表都会对应一条记录，这些记录的id列的值是相同的，出现在前边的表表示驱动表，出现在后边的表表示被驱动表。
    * 如果包含了SELECT,UNION 的情况，就会有多个ID了

select_type     ELECT关键字对应的那个查询的类型
    SIMPLE
        * 查询语句中不包含UNION或者子查询的查询都算作是SIMPLE类型，连接查询也算是SIMPLE类型
    PRIMARY
        * 对于包含UNION、UNION ALL或者子查询的大查询来说，它是由几个小查询组成的，其中最左边的那个查询的select_type值就是PRIMARY
    UNION
        * 包含UNION或者UNION ALL的大查询来说，它是由几个小查询组成的，其中除了最左边的那个小查询以外，其余的小查询的select_type值就是UNION
    UNION RESULT
        * MySQL选择使用临时表来完成UNION查询的去重工作，针对该临时表的查询的select_type就是UNION RESULT
    SUBQUERY
        * 如果包含子查询的查询语句不能够转为对应的semi-join的形式，并且该子查询是不相关子查询，并且查询优化器决定采用将该子查询物化的方案来执行该子查询时，该子查询的第一个SELECT关键字代表的那个查询的select_type就是SUBQUERY
    DEPENDENT SUBQUERY
        * 如果包含子查询的查询语句不能够转为对应的semi-join的形式，并且该子查询是相关子查询，则该子查询的第一个SELECT关键字代表的那个查询的select_type就是DEPENDENT SUBQUERY
    DEPENDENT UNION
        * 在包含UNION或者UNION ALL的大查询中，如果各个小查询都依赖于外层查询的话，那除了最左边的那个小查询之外，其余的小查询的select_type的值就是DEPENDENT UNION
    DERIVED
        * 采用物化的方式执行的包含派生表的查询，该派生表对应的子查询的select_type就是DERIVED
    MATERIALIZED
        * 查询优化器在执行包含子查询的语句时，选择将子查询物化之后与外层查询进行连接查询时，该子查询对应的select_type属性就是MATERIALIZED
    UNCACHEABLE SUBQUERY
    UNCACHEABLE UNION
        * 上面俩都不常用
table           表名
partitions      匹配的分区信息
    * 一般情况下查询语句的执行计划的partitions列的值都是NULL。

type            针对单表的访问方法
    system
        * 当表中只有一条记录并且该表使用的存储引擎的统计数据是精确的，比如MyISAM、Memory，那么对该表的访问方法就是system
    const
        * 根据主键或者唯一二级索引列与常数进行等值匹配时，对单表的访问方法就是const
    eq_ref
        * 在连接查询时，如果被驱动表是通过主键或者唯一二级索引列等值匹配的方式进行访问的（如果该主键或者唯一二级索引是联合索引的话，所有的索引列都必须进行等值比较），则对该被驱动表的访问方法就是eq_ref
    ref
        * 当通过普通的二级索引列与常量进行等值匹配时来查询某个表，那么对该表的访问方法就可能是ref
    fulltext
        * 全文检索
    ref_or_null
        * 对普通二级索引进行等值匹配查询，该索引列的值也可以是NULL值时，那么对该表的访问方法就可能是ref_or_null
    index_merge
        * 一般情况下对于某个表的查询只能使用到一个索引，对于使用多个索引的情况下，使用索引合并的方式来执查询。
    unique_subquery
        * 类似于两表连接中被驱动表的eq_ref访问方法，unique_subquery是针对在一些包含IN子查询的查询语句中，
        * 如果查询优化器决定将IN子查询转换为EXISTS子查询，而且子查询可以使用到主键进行等值匹配的话，那么该子查询执行计划的type列的值就是unique_subquery
    index_subquery
        * 与unique_subquery类似，只不过访问子查询中的表时使用的是普通的索引
    range
        * 使用索引获取某些范围区间的记录，那么就可能使用到range访问方法
    index
        * 使用索引覆盖，但需要扫描全部的索引记录时，该表的访问方法就是index
    ALL
        * 全表扫描
        * 除了All这个访问方法外，其余的访问方法都能用到索引
        * 除了index_merge访问方法外，其余的访问方法都最多只能用到一个索引

possible_keys   可能用到的索引
key             实际上使用的索引
key_len         实际使用到的索引长度
ref             当使用索引列等值查询时，与索引列进行等值匹配的对象信息
rows            预估的需要读取的记录条数
filtered        某个表经过搜索条件过滤后剩余记录条数的百分比
Extra           一些额外的信息
```

