
# 查询计划
MySQL执行查询语句的方式称之为访问方法或者访问类型。同一个查询语句可能可以使用多种不同的访问方法来执行，虽然最后的查询结果都是一样的，但是执行的时间可能会相差甚远

MySQL Server有一个称为查询优化器的模块，一条查询语句进行语法解析之后就会被交给查询优化器来进行优化，优化的结果就是生成一个所谓的执行计划，这个执行计划表明了应该使用哪些索引进行查询，表之间的连接顺序是啥样的，最后会按照执行计划中的步骤调用存储引擎提供的方法来真正的执行查询，并将查询结果返回给用户

## 模拟表
```sql
CREATE TABLE single_table (
    id INT NOT NULL AUTO_INCREMENT,
    key1 VARCHAR(100),
    key2 INT,
    key3 VARCHAR(100),
    key_part1 VARCHAR(100),
    key_part2 VARCHAR(100),
    key_part3 VARCHAR(100),
    common_field VARCHAR(100),
    PRIMARY KEY (id),
    KEY idx_key1 (key1),
    UNIQUE KEY idx_key2 (key2),
    KEY idx_key3 (key3),
    KEY idx_key_part(key_part1, key_part2, key_part3)
) Engine=InnoDB CHARSET=utf8;
```

## MYSQL查询方式
### 使用全表扫描进行查询
把表的每一行记录都扫一遍嘛，把符合搜索条件的记录加入到结果集

### 使用索引进行查询
使用索引来执行查询可能会加快查询执行的时间，使用索引来执行查询，可以细分为许多种类
- 针对主键或唯一二级索引的等值查询
- 针对普通二级索引的等值查询
- 针对索引列的范围查询
- 直接扫描整个索引



#### const
- 通过主键或者唯一二级索引列与常数的等值比较来定位一条记录的访问方法定义为：const
- 这种const访问方法只能在“主键列”或者“唯一二级索引列”和一个“常数”进行“等值比较”时才有效
- 如果主键或者唯一二级索引是由多个列构成的话，索引中的每一个列都需要与常数进行等值比较，这个const访问方法才有效（这是因为只有该索引中全部列都采用等值比较才可以定位唯一的一条记录）。
- 对于唯一二级索引来说，查询该列为NULL值的情况比较特殊，比如这样：
```sql
SELECT * FROM single_table WHERE key2 IS NULL;
```
因为唯一二级索引列并不限制 NULL 值的数量，所以上述语句可能访问到多条记录，也就是说 上边这个语句不可以使用const访问方法来执行

### ref
- 搜索条件为“二级索引列”与“常数”“等值比较”，采用二级索引来执行查询的访问方法称为：ref
- 对某个普通的二级索引列与常数进行等值比较，比如这样：
```sql
SELECT * FROM single_table WHERE key1 = 'abc';
```
- 二级索引列值为NULL的情况

不论是普通的二级索引，还是唯一二级索引，它们的索引列对包含NULL值的数量并不限制，所以我们采用key IS NULL这种形式的搜索条件最多只能使用ref的访问方法，而不是const的访问方法。

- 对于某个包含多个索引列的二级索引来说，只要是最左边的连续索引列是与常数的等值比较就可能采用ref的访问方法，比方说下边这几个查询：
```sql
SELECT * FROM single_table WHERE key_part1 = 'god like';

SELECT * FROM single_table WHERE key_part1 = 'god like' AND key_part2 = 'legendary';

SELECT * FROM single_table WHERE key_part1 = 'god like' AND key_part2 = 'legendary' AND key_part3 = 'penta kill';
```
但是如果最左边的连续索引列并不全部是等值比较的话，它的访问方法就不能称为ref了，比方说这样：
```sql
SELECT * FROM single_table WHERE key_part1 = 'god like' AND key_part2 > 'legendary';
```

### ref_or_null
- 不仅想找出某个二级索引列的值等于某个常数的记录，还想把该列的值为NULL的记录也找出来，就像下边这个查询：
```sql
SELECT * FROM single_table WHERE key1 = 'abc' OR key1 IS NULL;
```
- 当使用二级索引而不是全表扫描的方式执行该查询时，这种类型的查询使用的访问方法就称为ref_or_null

### range
- 搜索条件更复杂，比如下边这个查询：
```sql
SELECT * FROM single_table WHERE key2 IN (1438, 6328) OR (key2 >= 38 AND key2 <= 79);
```
- 这种利用索引进行范围匹配的访问方法称之为：range。
> 此处所说的使用索引进行范围匹配中的 `索引` 可以是聚簇索引，也可以是二级索引。

- 其实对于B+树索引来说，只要索引列和常数使用=、<=>、IN、NOT IN、IS NULL、IS NOT NULL、>、<、>=、<=、BETWEEN、!=（不等于也可以写成<>）或者LIKE操作符连接起来，就可以产生一个所谓的区间。
- `IN (1438, 6328)`为2个单点区间，`key2 >= 38 AND key2 <= 79` 为1个连续范围区间。


### index
- 遍历二级索引记录的执行方式称之为：index。
- 这个查询：
    - 查询列表只有3个列：key_part1, key_part2, key_part3，而索引idx_key_part又包含这三个列。     
    - 搜索条件中只有key_part2列。这个列也包含在索引idx_key_part中。 
    - 直接通过遍历idx_key_part索引的叶子节点的记录来比较key_part2 = 'abc'这个条件是否成立，把匹配成功的二级索引记录的key_part1, key_part2, key_part3列的值直接加到结果集中就行了
      
`````sql
SELECT key_part1, key_part2, key_part3 FROM single_table WHERE key_part2 = 'abc';
`````
二级索引记录比聚簇索记录小的多,而且这个过程也不用进行回表操作，所以直接遍历二级索引比直接遍历聚簇索引的成本要小很多



### all
- 全表扫描，对于InnoDB表来说也就是直接扫描聚簇索引，这种使用全表扫描执行查询的方式称之为：all

## 明确range访问方法使用的范围区间
### 两种情况由AND或OR组成的复杂搜索条件中提取出正确的范围区间
#### 所有搜索条件都可以使用某个索引的情况
- 例如
```sql
SELECT * FROM single_table WHERE key2 > 100 AND key2 > 200;
SELECT * FROM single_table WHERE key2 > 100 OR key2 > 200;
```

#### 有的搜索条件无法使用索引的情况
- 例如
```sql
SELECT * FROM single_table WHERE key2 > 100 AND common_field = 'abc';
```
- 范围区间是为了到索引中取记录中提出的概念，所以在确定范围区间的时候不需要考虑common_field = 'abc'这个条件
- 在为某个索引确定范围区间的时候只需要把用不到相关索引的搜索条件替换为TRUE就好了。

- 替换过程
```sql
// AND 过程
SELECT * FROM single_table WHERE key2 > 100 AND TRUE;
SELECT * FROM single_table WHERE key2 > 100;    // key2的范围区间就是：(100, +∞)。

// OR 过程
SELECT * FROM single_table WHERE key2 > 100 OR common_field = 'abc';
SELECT * FROM single_table WHERE key2 > 100 OR TRUE;
SELECT * FROM single_table WHERE TRUE; // key2的范围区间是：(-∞, +∞)。还是强制用 key2 索引回表的的话，代价太大了，不如直接遍历聚簇索引
```

## 索引合并
- 使用到多个索引来完成一次查询的执行方法称之为（在一个查询中使用到多个二级索引）：index merge

### Intersection合并
- 交集。某个查询可以使用多个二级索引，将从多个二级索引中查询到的结果取交集，比方说下边这个查询：
```sql
SELECT * FROM single_table WHERE key1 = 'a' AND key3 = 'b';
```
- 假设这个查询使用Intersection合并的方式执行的话，那这个过程就是这样的：
    1. idx_key1二级索引对应的B+树中取出key1 = 'a'的相关记录。
    2. 从idx_key3二级索引对应的B+树中取出key3 = 'b'的相关记录。
    3. 二级索引的记录都是由索引列 + 主键构成的，所以我们可以计算出这两个结果集中id值的交集。
    4. 按照上一步生成的id值列表进行回表操作，也就是从聚簇索引中把指定id值的完整用户记录取出来，返回给用户。

- 直接使用key1或者key3只根据某个搜索条件去读取一个二级索引，然后回表后再过滤另外一个搜索条件的代价
- 读取多个二级索引比读取一个二级索引消耗性能，但是读取二级索引的操作是顺序I/O，而回表操作是随机I/O

- MySQL在某些特定的情况下才可能会使用到Intersection索引合并：
    - 情况一：二级索引列是等值匹配的情况，对于联合索引来说，在联合索引中的每个列都必须等值匹配，不能出现只匹配部分列的情况。
    ```sql
    SELECT * FROM single_table WHERE key1 = 'a' AND key_part1 = 'a' AND key_part2 = 'b' AND key_part3 = 'c';  // idx_key1和idx_key_part这两个二级索引进行Intersection索引合并的操作：
    ```
    - 情况二：主键列可以是范围匹配
    ```sql
    SELECT * FROM single_table WHERE id > 100 AND key1 = 'a';
    ```

- 也就是说即使情况一、情况二成立，也不一定发生Intersection索引合并，这得看优化器的心情。优化器只有在单独根据搜索条件从某个二级索引中获取的记录数太多，导致回表开销太大，而通过Intersection索引合并后需要回表的记录数大大减少时才会使用Intersection索引合并。


### Union合并
- 想把既符合某个搜索条件的记录取出来，也把符合另外的某个搜索条件的记录取出来，这些不同的搜索条件之间是OR关系。
```sql
SELECT * FROM single_table WHERE key1 = 'a' OR key3 = 'b'
```
- Union是并集的意思，适用于使用不同索引的搜索条件之间使用OR连接起来的情况。与Intersection索引合并类似，MySQL在某些特定的情况下才可能会使用到Union索引合并：

    - 情况一：二级索引列是等值匹配的情况，对于联合索引来说，在联合索引中的每个列都必须等值匹配，不能出现只出现匹配部分列的情况。
    ```sql
    SELECT * FROM single_table WHERE key1 = 'a' OR ( key_part1 = 'a' AND key_part2 = 'b' AND key_part3 = 'c');
    ```
    
    - 情况二：主键列可以是范围匹配
    - 情况三：使用Intersection索引合并的搜索条件
    ```sql
    SELECT * FROM single_table WHERE key_part1 = 'a' AND key_part2 = 'b' AND key_part3 = 'c' OR (key1 = 'a' AND key3 = 'b');
    // 先按照搜索条件key1 = 'a' AND key3 = 'b'从索引idx_key1和idx_key3中使用Intersection索引合并的方式得到一个主键集合。
    // 再按照搜索条件key_part1 = 'a' AND key_part2 = 'b' AND key_part3 = 'c'从联合索引idx_key_part中得到另一个主键集合。
    // 采用Union索引合并的方式把上述两个主键集合取并集，然后进行回表操作，将结果返回给用户。
    ```
- 查询条件符合了这些情况也不一定就会采用Union索引合并，也得看优化器的心情。优化器只有在单独根据搜索条件从某个二级索引中获取的记录数比较少，通过Union索引合并后进行访问的代价比全表扫描更小时才会使用Union索引合并。

### Sort-Union合并
- 下边这个查询就无法使用到Union索引合并
    ```sql
    SELECT * FROM single_table WHERE key1 < 'a' OR key3 > 'z'
    // key1 < 'a'从idx_key1索引中获取的二级索引记录的主键值不是排好序的，根据key3 > 'z'从idx_key3索引中获取的二级索引记录的主键值也不是排好序的
    
    // 先根据key1 < 'a'条件从idx_key1二级索引中获取记录，并按照记录的主键值进行排序
    // 再根据key3 > 'z'条件从idx_key3二级索引中获取记录，并按照记录的主键值进行排序
    // 因为上述的两个二级索引主键值都是排好序的，剩下的操作和Union索引合并方式就一样了。  
    ```
- 先按照二级索引记录的主键值进行排序，之后按照Union索引合并方式执行的方式称之为Sort-Union索引合并
- 这种Sort-Union索引合并比单纯的Union索引合并多了一步对二级索引记录的主键值排序的过程。
