---------------------
list
---------------------
	# ˫������

---------------------
����
---------------------

---------------------
type
---------------------
	# type Element struct {
			Value interface{} // contains filtered or unexported fields
		}
		
		* �����еĽڵ�

		func (e *Element) Next() *Element
		func (e *Element) Prev() *Element

	# type List struct
		
		* ����

		func New() *List

		func (l *List) Back() *Element
		func (l *List) Front() *Element
			* ����β/ͷ���

		func (l *List) Init() *List
			*  ���һ�����е�������߳�ʼ��һ���µ�����

		func (l *List) InsertAfter(v interface{}, mark *Element) *Element
			* ��ָ���Ľڵ�󣬲�������

		func (l *List) InsertBefore(v interface{}, mark *Element) *Element
			* ��ָ���Ľڵ�ǰ����������

		func (l *List) Len() int
			* ���س���

		func (l *List) MoveAfter(e, mark *Element)
		func (l *List) MoveBefore(e, mark *Element)
		func (l *List) MoveToBack(e *Element)
		func (l *List) MoveToFront(e *Element)
		func (l *List) PushBack(v interface{}) *Element
		func (l *List) PushBackList(other *List)
			* ������β���������

		func (l *List) PushFront(v interface{}) *Element
		func (l *List) PushFrontList(other *List)
			* ������ͷ��������󣬷��ص�����
		
		func (l *List) Remove(e *Element) interface{}

---------------------
func
---------------------



---------------------
demo
---------------------
	# ���е�ʹ��
		queue := list.New()

		// ���Ԫ�ص�β��
		queue.PushBack("1")
		queue.PushBack("2")
		queue.PushBack("3")

		for queue.Len() > 0{

			// ��ȡͷ���ڵ�
			var element = queue.Front()
			fmt.Println(element.Value)

			// �Ƴ�ͷ���ڵ�
			queue.Remove(element)
		}

		// ��� ---------------
		1
		2
		3
	
	# ʹ�ö��е���HTML�ĵ�������
		// Preview ��HTML�ı��н�������������
		func Preview(content string, size int) (string, error) {
			if size < 1 {
				return "", errors.New("Ԥ���ַ����Ȳ���С�� 1")
			}
			if content == "" {
				return "", nil
			}
			document, err := html.Parse(strings.NewReader(content))
			if err != nil {
				return "", err
			}

			// �Ѿ���ȡ�����ַ�����
			var count int
			var ret string

			// ���б���
			queue := list.New()
			queue.PushBack(document)

			for queue.Len() > 0 {
				// �Ƴ���ǰ�ڵ�
				item := queue.Remove(queue.Back())

				// ����ÿһ���ڵ�
				node := item.(*html.Node)

				// �ı��ڵ�
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

				// ��һ���ֵܽڵ�
				if node.NextSibling != nil {
					queue.PushBack(node.NextSibling)
				}

				// ��һ���ӽڵ�
				if node.FirstChild != nil {
					queue.PushBack(node.FirstChild)
				}
			}
			return ret, nil
		}
