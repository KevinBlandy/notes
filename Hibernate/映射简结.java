========================	模板	===========================

多对一
	<many-to-one name="" class="" column=""/>
一对多
	<set name="">
        	<key column=""/>
        	<one-to-many class=""/>
        </set>
多对多
	<set name="" table="">
   		<key column=""/>
   		<many-to-many class="" column=""/>
   	</set>
一对一(基于外键,这也是推荐的方式)
	有外键方
	<many-to-one name="" class="" column="" unique="">
	无外键方
	<one-to-one name="" class="" property-ref=""/>
========================	填空	===========================
<!-- XXXX属性,表示与XXXX的多对多关系 -->
------1-------------2-------3---------
1,name属性
	填1
2,class属性
	填2
3,cloumn
   在多对一(many-to-one)时:
	本配置中的,name属性名+id
   在一对多的<key>中:
	写对方类的映射文件中,相应属性配置的列明,就跟我有关系的那个属性
   在多对多的<key>中:
	写自己类的名称+id
   在多对多的<many-to-many>的cloumn中
	写对方类的名称+id后缀