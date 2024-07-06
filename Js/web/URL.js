-------------------
URL
-------------------
	# 用于解析，构造，规范化和编码 URL。

	# 构造函数
		new URL(url, base)
		
		url
			* 表示绝对或相对 URL 的 DOMString 或任何具有字符串化方法的对象，例如 <a> 或 <area> 元素。
			* 如果 url 是相对 URL，则会将 base 用作基准 URL。
			* 如果 url 是绝对 URL，则无论参数 base 是否存在，都将被忽略。
		
		base
			* 一个表示基准 URL 的字符串，当 url 为相对 URL 时，它才会生效。
			* 如果未指定，默认为 undefined。

-------------------
this
-------------------
	hash
	host
	hostname
	href
	origin
	password
	pathname
	port
	protocol
	search
	searchParams
	username

-------------------
static
-------------------

	canParse()
	createObjectURL(object)
		* 创建一个用于表示参数中给出的对象的 URL 的字符串。
		* 返回一个包含对象 URL 的字符串，可用于引用指定源 object 的内容。

		object
			* 用于创建 URL 的 File、Blob 或 MediaSource 对象。

	parse()

	revokeObjectURL(objectURL)
		* 用于释放之前通过调用 URL.createObjectURL() 创建的现有对象 URL。

		objectURL
			* 之前通过调用 createObjectURL() 方法创建的对象 URL 的字符串。
