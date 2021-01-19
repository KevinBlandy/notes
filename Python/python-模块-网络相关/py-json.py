------------------------
json					|
------------------------
	* JSON解析相关
	* json编码支持的基本类型有
		None
			None会被映射为null
		bool
			True 会被映射为 true,False 会被映射为 false
		int
		float
		string
		list
		tuple 
			元组()会被映射为列表[]

		dict
			对于字典,json会假设key是字符串(字典中的任何非字符串key都会在编码时转换为字符串)
			要符合JSON规范应该只对python列表和字典进行编码.此外,在WEB应用中,把最顶层对象定义为字典是一种标准做法
	
	* 编码类型对照表
		Python		JSON
		dict		object
		list,tuple	array
		str			string
		int,float,int- & float-derived Enums	number
		True		true
		False		false
		None		null
	
	* 解码类型对照表
		object		dict
		array		list
		string		str
		number		(int)	int
		number		(real)	float
		true		True
		false		False
		null		None

------------------------
json-模块属性			|
------------------------
	
------------------------
json-模块函数			|
------------------------
	str dumps(obj, *, skipkeys=False, ensure_ascii=True, check_circular=True,
			allow_nan=True, cls=None, indent=None, separators=None,
			default=None, sort_keys=False, **kw)
		* 把可JSON序列化对象转换为JSON字符串
		* 关键字参数
			default
				* 指定 转换 obj 对象时,的转换函数
				* 如果参数是一个不能进行JSON转换的对象,那么就会调用 default 指定的函数来进行转换操作,返回结构就是该函数返回的字符串
				* 并且会把 obj 对象传递给该函数的第一个参数
			separators
				* 指定切割??,应该是格式化json的分隔符,和:
				* separators=(', ', ': ')	,就是在json的 ',' 后添加一个空格,':' 后也添加了一个空格
			
			indent
				* 间距
			sort_keys
				* 是否要对key进行排序
			ensure_ascii
				* 默认值 True,是否把中文转换为 Unicode 编码
		
		* 序列化格式化后的JSON
			data = {'name':'KevinBlandy'}
			json.dumps(data, sort_keys=True, indent=4, separators=(',', ':'), ensure_ascii=False)

	
	str dump(obj,f)
		* 把可JSON序列化对象转换为JSON字符串,并且写入 f(open获取) 文件
		* 关键字参数
			default
				* 同上
			ensure_ascii
				* 同上

	dict loads(jsonstr)
		* 把JSON字符串转换为Python的数据结构
		* 关键字参数
			object_hook
				* 如果该值不为 None,那么就会把JSON数据(dict)传递给该函数,由该函数进行JSON的反序列化
				* 对于复杂的反序列化可以使用,例如:反序列化为自定义的对象

	dict load(filePath)
		* 从指定文件中读取数据,转换为Python的数据结构
		* 关键字参数
			object_hook
				* 同上


------------------------
json-demjson			|
------------------------
	* 如果JSON字符串的key是没有带双引号的,或者是单引号的,那么有可能会在序列化为JSON的时候抛出异常
	* 解决办法就是使用第三方库 demjson
		pip install demjson
		import demjson
		json_obj = demjson.decode("{name:\"KeviinBlandy\"}")

------------------------
json-demo				|
------------------------
# 自定义对象与JSON之间的转换
import json
class Obj():
    def __init__(self,name,age):
        self.name = name
        self.age = age
    
    def __str__(self):
        return "[name=%s,age=%s]"%(self.name,self.age)

    @classmethod
    def toJson(cls,obj):
        return {'name':obj.name,'age':obj.age};
    
    @classmethod
    def toObj(cls,json):
        # 直接访问JSON中的数据字段,此时JSON其实就是个 dict
        return  Obj(json['name'],json['age']);

json_str = json.dumps(Obj('KevinBlandy',23),default=Obj.toJson) 
print("序列化:%s"%(json_str))      # 序列化:{"name": "KevinBlandy", "age": 23}

obj = json.loads(json_str,object_hook=Obj.toObj)
print("反序列化:%s"%(obj))          # 反序列化:[name=KevinBlandy,age=23]
