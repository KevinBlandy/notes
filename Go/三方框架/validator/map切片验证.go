---------------------
切片
---------------------
	# 对于切片，验证tag中的数值，便是验证长度(len)
		type User struct {
			Hobby []string `validate:"required,len=3,dive,min=3,max=5"`
			// required和len，指定Hobby是必须的，并且长度只能是3
			// dive 表示要深入校验元素
			// min=3 表示字符串长度，最低为3，最高为5
		}

		func main() {
			v := validator.New()
			var user = &User{Hobby: []string {"Jav", "hon", "Rb" }}
			if err := v.Struct(user); err != nil {
				log.Println(err)
			}
		}
	

	# 多维切片，可以用多个 dive 校验
		var slicethree [][]string
		validate.Var(slicethree, "min=2,dive,len=2,dive,required")
			* min=2：验证第一个 [] 方括号的值长度 ;
			* len=2：验证第二个 []string 长度;
			* required：验证slice里的值

		validate.Var(slicethree, "min=2,dive,dive,required")
			* min=2：验证第一个 [] 方括号的值长度 ;
			* dive： 后没有设置tag值，不验证第二个 []string ;
			* required： 验证slice里的值
	
---------------------
切片
---------------------
	# 使用dive,keys,endkeys tag完成校验
		* keys 和 endkeys 两tag，验证这2个tag之间map的 key，表示一个区间

		mapone = map[string]string{"one": "jimmmy", "two": "tom", "three": ""}

		err := validate.Var(mapone, "gte=3,dive,keys,eq=1|eq=2,endkeys,required")

		* gte=3：验证map自己的长度；
		* dive后的 keys,eq=1|eq=2,endkeys：验证map的keys个数，也就是验证里值。
		* required：验证 map的值value
	
	# 嵌套map，那就多个 dive
		var maptwo map[[3]string]string{}
		validate.Var(maptwo, "gte=3,dive,keys,dive,eq=1|eq=3,endkeys,required")

		* gte=3： 验证map的长度
		* keys,dive,eq=1|eq=3,endkeys：keys和endkeys中有一个dive(深入一级)，验证map中key的数组每一个值
		* required： 验证map的值

