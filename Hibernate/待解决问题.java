一对多映射,插入数据时错误

Exception in thread "main" org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1


 order-by:能解决Set集合中数据无序的问题
 要注意的是,一对多的时候.Set 集合中的数据默认延迟加载的!如果是在JSP页面使用集合数据,一定要 lazy="false",不然抛异常


 多表查... ...
 商品一级分类	商品二级分类	具体商品

 电子产品		笔记本			外星人

 SELECT COUNT(*) FROM 具体商品类 p WHERE p.商品二级分类.商品一级分类.主键=值...
 * 只有商品一级分类的数据,获取所有具体的商品!



