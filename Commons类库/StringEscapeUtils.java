------------------------------
防止XSS工具,HTML转义工具类		|
------------------------------
	# common-lang3.jar
	# org.apache.commons.lang3.StringEscapeUtils
	# StringEscapeUtils.escapeHtml4(String js);
	# 可以把传入的HTML代码和JS代码转换为转义字符,防止XSS跨站攻击

		String js =  "中文<script>hi</script><h5></h5>";
		System.out.println(js);	//中文<script>hi</script><h5></h5>
		js = StringEscapeUtils.escapeHtml4(js);
		System.out.println(js);	//中文&lt;script&gt;hi&lt;/script&gt;&lt;h5&gt;&lt;/h5&gt;

		
		StringEscapeUtils.escapeHtml4();
			# 换行HTML4所有

		StringEscapeUtils.escapeEcmaScript()
			# 仅仅转换JS代码
