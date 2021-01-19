
# ipv4
	^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$

# 移动电话号码
	^1[3-9]\d{9}$


# 用户名
	/^(?!\d+$)(?!_+$)\w{1,14}$/.test(name.replace(/[\u4e00-\u9fa5]/g,"aa"));
	name.replaceAll("[\\u4e00-\\u9fa5]", "aa").matches("^(?!\\d+$)(?!_+$)\\w{1,14}$");

# 真实姓名（包涵少数名族名称）
	^[\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,20}$

# 合法的金钱表示
	/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/

	 //000 错
     //0 对
     //0. 错
     //0.0 对
     //050 错
     //00050.12错
     //70.1 对
     //70.11 对
     //70.111错
     //500 正确

# B站总结的垃圾信息过滤正则(致敬B站的开源精神)
	regexpTaobao    = regexp.MustCompile(`￥([\w\s]+)￥`)
	regexpURL       = regexp.MustCompile(`(?:http|https|www)(?:[\s\.:\/\/]{1,})([\w%+:\s\/\.?=]{1,})`)
	regexpWhitelist = regexp.MustCompile(`((acg|im9|bili|gov).*(com|html|cn|tv)|(av\d{8,}|AV\d{8,}))`)
	regexpQQ        = regexp.MustCompile(`(?:[加qQ企鹅号码\s]{2,}|[群号]{1,})(?:[\x{4e00}-\x{9eff}]*)(?:[:，：]?)([\d\s]{6,})`)
	regexpWechat    = regexp.MustCompile(`(?:[加+微＋+➕薇？vV威卫星♥❤姓xX信]{2,}|weixin|weix)(?:[，❤️.\s]?)(?:[\x{4e00}-\x{9eff}]?)(?:[:，：]?)([\w\s]{6,})`)
