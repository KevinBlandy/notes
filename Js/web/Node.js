-------------------------
Node
-------------------------
	# 表示一个 HTML 节点

-------------------------
this
-------------------------

	nodeType
		* 只读属性，整数类型，表示该节点的类型（比如元素、文本和注释）
		* 全局的常量

			Node.ELEMENT_NODE = 1
				* 元素节点，例如 <p> 和 <div>。
			
			Node.ATTRIBUTE_NODE = 2
				* 元素的属性
			
			Node.TEXT_NODE = 3
				* 元素或者属性中实际的文本。
			
			Node.CDATA_SECTION_NODE = 4
				* CDATASection，例如 <!CDATA[[ … ]]>。
			
			Node.ENTITY_REFERENCE_NODE = 5
				* 废弃
			
			Node.ENTITY_NODE = 6
				* 废弃
			
			Node.PROCESSING_INSTRUCTION_NODE = 7
				* 用于 XML 文档的 ProcessingInstruction，例如 <?xml-stylesheet ... ?> 声明。
			
			Node.COMMENT_NODE = 8
				* 注释节点，例如 <!-- … -->。
			
			Node.DOCUMENT_NODE = 9
				* 文档（Document）节点
			
			Node.DOCUMENT_TYPE_NODE = 10
				* 描述文档类型的 DocumentType 节点。例如 <!DOCTYPE html>。
			
			Node.DOCUMENT_FRAGMENT_NODE = 11
				*  DocumentFragment 节点
			
			Node.NOTATION_NODE = 12
				* 废弃
		
	nodeName
		* 返回当前节点的节点名称，字符串。
		* 如果是元素节点，nodeName 属性和 tagName 属性返回相同的值，但如果是文本节点，nodeName 属性会返回 "#text"，而 tagName 属性会返回 undefined。
	
	nodeValue
		*  返回或设置当前节点的值。
	
	childNodes
		* 返回包含指定节点的子节点的集合。
		* 该集合为即时更新的集合（live collection），DOM结构的变化会自动地在NodeList中反映出来。
	
	firstChild
	lastChild 
	parentNode
	previousSibling
	nextSibling

	hasChildNodes()
		* 当前节点是否包含有子节点。
	
	appendChild(aChild)
		* 给父节点（通常为一个元素）追加一个元素节点。
		* 返回追加后的子节点（aChild），如果 aChild 是一个文档片段（DocumentFragment），这种情况下将返回空文档片段（DocumentFragment）。

		* 加新节点会更新相关的关系指针，包括父节点和之前的最后一个子节点。
		* 如果把文档中已经存在的节点传给 appendChild()，则这个节点会从之前的位置被转移到新位置。

		
	insertBefore(newNode, referenceNode);
		* 在 referenceNode 节点之前插入一个子节点 newNode。

		newNode
			* 要插入的新节点
		
		referenceNode 
			* 把新节点插入到这个节点之前。
			* 注意，这不是可选参数—，必须显式传入一个 Node 或者 null，如果不提供节点或者传入无效值，在不同的浏览器中会有不同的表现。
		

	replaceChild(newChild, oldChild)
		* 替换当前节点的一个子节点，并返回被替换掉的节点。

		newChild
			* 用来替换 oldChild 的新节点。如果该节点已经存在于 DOM 树中，则它首先会被从原始位置删除。

		oldChild
			* 被替换掉的原始节点。
	
	removeChild(child);
		
		* 移除一个子节点

		child
			* 要移除的节点。
		
	cloneNode(deep);
		* Clone 一个节点
		
		deep
			* 是否采用深度克隆，如果为 true （默认），则该节点的所有后代节点也都会被克隆，如果为 false，则只克隆该节点本身。
		
	normalize()
		* 将指定的节点及其所有子树转化为规范化形式。
		* 在规范化子树中，子树上的文本节点都不为空，且没有相邻的文本节点。



-------------------------
static
-------------------------