---------------------
����
---------------------
	# ģ��Ķ���
		{{ define "header" }}
			<meta charset="UTF-8">
		{{ end }}

		* ģ��Ĭ�ϵ����ƣ����ļ�����
	
	# ��������ģ��
		{{template "header"}}
	
	# ģ��֮�䴫�ݱ���
		* ����ģ�壬��������
			{{ define "header" }}
				<meta charset="{{ . }}">
			{{ end }}
		
		* ʹ��ģ�壬���ݱ���
			{{template "header" "utf-8"}}		// ����
			 {{ template "common/head.html" .val}}	// ģ�����
		

