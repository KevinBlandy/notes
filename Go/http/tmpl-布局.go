---------------------
����
---------------------
	# ģ��Ķ���
		{{ define "header" }}
			<meta charset="UTF-8">
		{{ end }}

		* ģ��Ĭ�ϵ����ƣ����ļ�����
		* ģ�����ƻ������Ǽ�����
	
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
		

	# block
		* ʹ�� {{template "header" . }} ʱ��� "header" �����ڣ�����쳣
		* ����ʹ�� block��������ģ�岻���ڣ����ҿ������Ĭ��ֵ
	
		{{ block "header" .}} header ģ��û�ҵ� {{ end }}
		
			* ��� "header" ģ�岻���ڣ�����ʱ����һ��{{define "header"}} header ģ��û�ҵ� {{end}}����ִ������
		