----------------------
节点属性				|
----------------------
	# 可以对节点设置非HTML的标准属性

	attributes
		* 包含了代表一个元素的特性的Attr对象,仅用于Element节点
		* 返回 NamedNodeMap 对象

	dataset
		* 返回的是一个 map,里面包含了节点上的所有 data- 属性
			let p = document.querySelector('p');
			console.log(p.dataset); //DOMStringMap {a: "1", b: "2"}
			console.log(p.dataset.a);   //1


	createAttribute();
		* 创建一个指定名称的属性节点对象
		* 返回的是一个属性节点对象

	setAttributeNode();
		* 设置一个属性节点对象

	removeAttributeNode()	
		* 移除指定的属性节点，并返回被移除的节点。


	getAttribute();
		* 获取指定名称的属性值
	
	setAttribute(属性名,属性值);
		* 设置属性名和属性值
	
	removeAttribute();
		* 删除指定名称的属性值
	
	hasAttribute();
		* 判断节点是否具备指定的属性

----------------------
节点属性-属性			|
----------------------
	isId	
		* 如果属性是 id 类型，则返回 true，否则返回 false。
	name	
		* 返回属性的名称。
	value	
		* 设置或返回属性的值。
	specified	
		* 如果已指定属性，则返回 true，否则返回 false。

----------------------
节点属性-方法			|
----------------------
	# NamedNodeMap 方法

	nodemap.getNamedItem()
		* 从 NamedNodeMap 返回指定的属性节点。
	nodemap.item()	
		* 返回 NamedNodeMap 中位于指定下标的节点。
	nodemap.length
		* 返回 NamedNodeMap 中的节点数。
	nodemap.removeNamedItem()	
		* 移除指定的属性节点。
	nodemap.setNamedItem()	
		* 设置指定的属性节点（通过名称）。