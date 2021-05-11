--------------------------
tag
--------------------------
	# 参考文档
		https://github.com/go-playground/validator/blob/master/README.md#baked-in-validations

--------------------------
基本
--------------------------

	-
		* 跳过这个字段，不验证

	|
		* 多个验证关系是OR
	
	required
		* 必须选项
	
	omitempty
		* 如果字段没有设置，则忽略校验
		* 也就是说0值忽略校验

	email
		* 必须是邮箱

--------------------------
范围
--------------------------
	lte
		* 小于等于参数值，validate:"lte=3" (小于等于3)
		* 可以用于限制字符串的长度
	gte
		* 大于等于参数值，validate:"lte=120,gte=0" (大于等于0小于等于120)
	lt
		* 小于参数值，validate:"lt=3" (小于3)
	gt
		* 大于参数值，validate:"lt=120,gt=0" (大于0小于120)
	eq
		* 等于参数值，validate:"eq=2"
	ne
		* 不等于，validate:"ne=2" (不等于2)
	max
		* 最大值，小于等于参数值，validate:"max=20" (小于等于20)
		* 可以用于判断字符串长度
	min
		* 最小值，大于等于参数值，validate:"min=2,max=20" (大于等于2小于等于20)
		* 可以用于判断字符串长度
	oneof
		* 只能是列举出的值其中一个，这些值必须是数值或字符串，以空格分隔，如果字符串中有空格，将字符串用单引号包围，validate:"oneof=red green"
	len
		* 判断字符串/map/slice 的长度

--------------------------
字符串
--------------------------
	contains
		* 包含参数子串，validate:"contains=tom" (字段的字符串值包含tom)
	excludes
		* 包含参数子串，validate:"excludes=tom" (字段的字符串值不包含tom)
	startswith
		* 以参数子串为前缀，validate:"startswith=golang"
	endswith
		* 以参数子串为后缀，validate:"startswith=world"
	alpha
		* 只能是字母
	alphanum
	alphanumunicode
	alphaunicode
	ascii
	containsany
	containsrune
	endswith
	lowercase
	multibyte
	number
	numeric
	printascii
	uppercase



--------------------------
字段验证
--------------------------
	eqcsfield
		* 跨不同结构体字段验证，比如说 Struct1 Filed1，与结构体Struct2 Field2相等
			type Struct1 struct {
				Field1 string `validate:eqcsfield=Struct2.Field2`
				Struct2 struct {
					Field2 string 
				}
			}

	necsfield
		* 跨不同结构体字段不相等
	
	eqfield
		* 同一结构体字段验证相等，最常见的就是输入2次密码验证
			type User struct { 
				Password string `validate:"min=10"`
				Password2 string `validate:"eqfield=Password"`
			}

	nefield
		* 同一结构体字段验证不相等
	
	gtefield
		* 大于等于同一结构体字段，validate:"gtefiled=Field2"
	
	ltefield
		* 小于等于同一结构体字段
	
--------------------------
网络验证
--------------------------
	ip
		* 字段值是否包含有效的IP地址，validate:"ip"
	ipv4
		* 字段值是否包含有效的ipv4地址，validate:"ipv4"
	ipv6
		* 字段值是否包含有效的ipv6地址，validate:"ipv6"
	uri
		* 字段值是否包含有效的uri，validate:"uri"
	url
		* 字段值是否包含有效的uri，validate:"url"

--------------------------
格式化
--------------------------
	base64
		* 字段值是否包含有效的base64值
	
	base64url
	btc_addr
	btc_addr_bech32
	datetime
	e164
	email
	eth_addr
	hexadecimal
	hexcolor
	hsl
	hsla
	html
	html_encoded
	isbn
	isbn10
	isbn13
	json
	latitude
	longitude
	rgb
	rgba
	ssn
	uuid
