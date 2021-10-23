------------------
html
------------------
	# html模块

------------------
var
------------------

------------------
type
------------------

------------------
func
------------------
	func EscapeString(s string) string
		* EscapeString可以转义特殊字符，如"<"变成"&lt;"。
		* 它只转义了五个这样的字符。<, >, &, ', " 
		* UnescapeString(EscapeString(s)) == s 总是成立的，但反之不一定成立。

	func UnescapeString(s string) string
		* UnescapeString将"&lt; "这样的实体解读为"<"。
		* 它比EscapeString转义的实体范围更大。
		* 例如，"&aacute;"可以解读为 "á"，"&#225; "和"&#xE1; "
		* 也是如此。UnescapeString(EscapeString(s)) == s总是成立的，但反之不一定成立。
