Collection下面两个子接口
——List :元素是有序的，元素可以重复。因为该集合系有索引。
——Set :元素是无序。元素不可以重复。
ArrayList al = new ArrayList();
List 集合判断元素是否相同，依据的是元素的equals方法。
List 特有方法：
	凡是可以操作角标的方法都是该体系特有的方法！
增
	add(index,element);
	add(index,Collection);
删
	remove(index);
改
	set(index,element);
查
	get(index);
	subList(from,to);
	listIterator();
--------------------------------------------------------
添加元素
al.add(1,"obj");//在角标1的位置插入一个元素。后面的元素统一后移！
删除元素
al.remove(2);//删除角标为2的元素。后面的元素向前靠拢。
修改元素
al.set(2,obj);//把容器中角标为2的元素替换成对象obj.
	重点——————————————查！
获取单个元素
	al.get(5);//获取角标位置是5的元素！
----------------------------------------------------------
获取所有元素
	它的取出方式可以是for循环！
	for(int x=0;x<al.size();x++)
	{
		System.out.println(al.get(x)+"	");
	}
	迭代器
	Iterator it = al.iterator();
	while(it.hasNext())
	{
		System.out.println(it.next());
	}
	for(Iterator it = al.iterator;it.hasNext();)
	{
		System.out.println(it.next());
	}
---------------------------------------------------------
根据元素获取位置
al.indexOf(obj);//获取元素obj在容器中的角标位置
获取容器中的一段元素
List sub = al.subList(1,3);//把al中角标为1，2的元素复制到新列表sub中！从1开始，不包含3。
-----------------------------------------------------------
							列表迭代器！！！
List集合特有的迭代器
ListIterator 是 Iterator的子接口！
在迭代时不可以通过集合对象的方法操作集合中的元素。因为会发生并发修改异常！
所以在迭代时只能用迭代器的方法操作元素。可是，Iterator的方法是有限的！只能对元素进行判断。取出。删除的操作！
如果想要其他的操作——如添加，修改等！就需要使用其子接口——ListIterator！
该接口只能通过List集合的Listerator方法获取！
----------------------------------------------------------------
ListIterator it = al.listIterator();
while(it.hasNext())
{
	Object obj = it.next()
	if(obj.equals("Demo"))
	{
		it.add("添加的元素");	//当遍历到"Demo"这个元素的时候。就在它后面加入元素
		it.remove();		//-----------------------------删除"Demo"元素。
		li.set("修改的元素");	//-----------------------------把"Demo"元素.修改成"修改的元素"!
	}
}













