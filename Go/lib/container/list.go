---------------------
list
---------------------
	# 双向链表

---------------------
变量
---------------------

---------------------
type
---------------------
	# type Element struct {
			Value interface{} // contains filtered or unexported fields
		}
		
		* 链表中的节点

		func (e *Element) Next() *Element
		func (e *Element) Prev() *Element

	# type List struct
		
		* 链表

		func New() *List

		func (l *List) Back() *Element
		func (l *List) Front() *Element
			* 返回尾/头结点

		func (l *List) Init() *List
			*  清空一个现有的链表或者初始化一个新的链表。

		func (l *List) InsertAfter(v interface{}, mark *Element) *Element
			* 在指定的节点后，插入数据

		func (l *List) InsertBefore(v interface{}, mark *Element) *Element
			* 在指定的节点前，插入数据

		func (l *List) Len() int
			* 返回长度

		func (l *List) MoveAfter(e, mark *Element)
		func (l *List) MoveBefore(e, mark *Element)
		func (l *List) MoveToBack(e *Element)
		func (l *List) MoveToFront(e *Element)
		func (l *List) PushBack(v interface{}) *Element
		func (l *List) PushBackList(other *List)
			* 在链表尾部插入对象

		func (l *List) PushFront(v interface{}) *Element
		func (l *List) PushFrontList(other *List)
			* 在链表头部插入对象，返回的数据
		
		func (l *List) Remove(e *Element) interface{}

---------------------
func
---------------------



---------------------
demo
---------------------
	# 队列的使用
		queue := list.New()

		// 添加元素到尾部
		queue.PushBack("1")
		queue.PushBack("2")
		queue.PushBack("3")

		for queue.Len() > 0{

			// 获取头部节点
			var element = queue.Front()
			fmt.Println(element.Value)

			// 移除头部节点
			queue.Remove(element)
		}

		// 输出 ---------------
		1
		2
		3
	
	# 使用队列迭代HTML文档的例子
		// Preview 从HTML文本中解析出文字内容
		func Preview(content string, size int) (string, error) {
			if size < 1 {
				return "", errors.New("预览字符长度不能小于 1")
			}
			if content == "" {
				return "", nil
			}
			document, err := html.Parse(strings.NewReader(content))
			if err != nil {
				return "", err
			}

			// 已经读取到的字符数量
			var count int
			var ret string

			// 队列遍历
			queue := list.New()
			queue.PushBack(document)

			for queue.Len() > 0 {
				// 移除当前节点
				item := queue.Remove(queue.Back())

				// 遍历每一个节点
				node := item.(*html.Node)

				// 文本节点
				if node.Type == html.TextNode {
					text := strings.TrimSpace(node.Data)
					if text != "" {
						textCont := utf8.RuneCountInString(text)
						if textCont+count > size {
							textCont = size - count
						}
						ret = ret + string([]rune(text)[:textCont])
						count += textCont
					}
				}

				// 下一个兄弟节点
				if node.NextSibling != nil {
					queue.PushBack(node.NextSibling)
				}

				// 下一个子节点
				if node.FirstChild != nil {
					queue.PushBack(node.FirstChild)
				}
			}
			return ret, nil
		}
