------------------------
����
------------------------
	# ģ��������õķ�����ֻ�ܷ���һ�������������2������ô�ڶ��������� error
	
	# ����/�����ĵ���
		* ���ú���������������(����ͨ��Api����ͨ����ע��Ϊ���ú���)
		* ��ͨ����������ͨ�������������������������ģ���ͨ�� call ָ�����(callָ�����Ǹ����ú���)
		* ��Ա����������ֱ��ͨ�� [����].[����] ����


	# �ṹ�巽�����ã�ʹ��:. 
		type User struct {
			Name string
		}
		func (u *User) Say(val string) string {
			return fmt.Sprintf("%s, Im %s\n", val, u.Name)
		}
		
		
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"user": &User{Name: "KevinBlandy"},
		})

		{{ .user.Say "Hello" }}


		* ���û�в�����ʡ�Լ���
	
	# �����������ã�ʹ�� call ָ��
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"say": func(val string) string {
				return fmt.Sprintf("Hello %s\n", val)
			},
		})

		{{ call .say "½����" }}
	

	# ע��ȫ�ֺ���������ط������Ե��ã����������ã�����д�ں��棬����Ļ�ʹ�ö��ŷָ�

		* Ҳ����ע��δ�����ú������� call, html ��һ����
		
		// FuncMaps ϵͳ���õ���ģ�����溯��
		var FuncMaps = template.FuncMap{
			"Global": func() map[string]any {
				return map[string]any{
					// struct
					"Title": struct {
						Name string
					}{Name: "PPP"},

					// func
					"Key": func(val string) string {
						return "Hello " + val
					},
				}
			},
			// func
			"Test": func() string {
				return "Im From Func"
			},
		}
		
		// ����
        {{ Global.Title.Name }} <br/>
        {{ call Global.Key "Go's hh ;" | html }}<br/>			// ͨ�� call ���� Global.Key ���������ݲ��������Ұѷ���ֵ�� html�ٴε���
        {{ html (call Global.Key "Go's template") }}<br/>		// ���� call Global.Key ���������ݲ��������Ұѷ���ֵ�� html�ٴε���
        {{ Test }}<br/>			// ע���ȫ�����ú�����ֱ�ӵ���

		// ���
        PPP <br/>
        Hello Go&#39;s hh ;<br/>
        Hello Go&#39;s template<br/>
        Im From Func<br/>
	
	# �ܵ�����
		* ʹ�� | �ѱ�����Ϊ���������ݸ�����
		* �����ķ���ֵ������Ϊ��һ�������Ĳ���
			<a href="/search?q={{. | urlescaper | attrescaper}}">{{. | htmlescaper}}</a>
		
		* ��������ж����������ô�ܵ�����ѱ������ݸ����һ������
			{{ . | printf "%s\n" "abcd" }}  // {.}}�Ľ�������ݸ�printf���Ҵ��ݵĲ���λ����"abcd"֮��
				
	# ֱ�����ԭʼ����
		{{`"output"`}}


------------------------
���ú���
------------------------
	js
		* ��������������ı���ʾ��ʽ��Ч��ת��JavaScript
	
	len
		* ���س���
	
	index
		* ������������ֵ
	
	call
		* ִ�н���ǵ��õ�һ�������ķ���ֵ���ò��������Ǻ������ͣ����������Ϊ���øú����Ĳ���
		* û�в���ֱ�ӵ��ã��в���ʹ��call
	
	html
		* �Բ�������HTML����

	urlquery
		* �Լ����������б���
			{{ urlquery "name=����&age=65" }}
			name%3D%E5%88%98%E5%A4%87%26age%3D65
	

------------------------
�������ĺ�����
------------------------
	# https://github.com/Masterminds/sprig