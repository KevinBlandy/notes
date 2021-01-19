--------------------------------
Snakeyaml						|
--------------------------------
	# Maven
		<dependency>
			<groupId>org.yaml</groupId>
			<artifactId>snakeyaml</artifactId>
			<version>1.23</version>
		</dependency>
	
	# 网站
		https://bitbucket.org/asomov/snakeyaml/wiki/Home
	
	# 入门
		Yaml yaml = new Yaml();
		InputStream inputStream = Main.class.getResourceAsStream("/application.yml");
		Object load = yaml.load(inputStream);
		System.out.println(load);							//{name=KevinBlandy}
		System.out.println(load.getClass().getName());		//java.util.LinkedHashMap
	
	# Map 结构对象
		- 对象:java.util.LinkedHashMap
		- 数组:java.util.ArrayList
	
--------------------------------
List							|
--------------------------------
	- Kevin
	- Litch
	- Rocco


	Yaml yaml = new Yaml();
	List<String> list = yaml.load(Main.class.getResourceAsStream("/application.yml"));
	System.out.println(list);						//[Kevin, Litch, Rocco]
	System.out.println(list.getClass().getName());	//java.util.ArrayList
		
	
--------------------------------
Map								|
--------------------------------
	name: KevinBlandy
	age: 23
	gender: boy
		
	Yaml yaml = new Yaml();
	Map<String,String> map = yaml.load(Main.class.getResourceAsStream("/application.yml"));
	System.out.println(map);						//{name=KevinBlandy, age=23, gender=boy}
	System.out.println(map.getClass().getName());	//java.util.LinkedHashMap

--------------------------------
复杂的解析						|
--------------------------------
user:
  name: KevinBlandy
  age: 23
  skills:
    - Java
    - Python
    - C
    - Javascript


	Yaml yaml = new Yaml();
	Map<String,Object> map = yaml.load(Main.class.getResourceAsStream("/application.yml"));
	System.out.println(map);						//{user={name=KevinBlandy, age=23, skills=[Java, Python, C, Javascript]}}
	System.out.println(map.getClass().getName());	//java.util.LinkedHashMap
	
	Map<String,Object> user = (Map<String, Object>) map.get("user");
	String name = (String) user.get("name");
	System.out.println(name);									//KevinBlandy

	List<String> skills = (List<String>) user.get("skills");
	System.out.println(skills);									//[Java, Python, C, Javascript]

--------------------------------
把 yml 解析对象渲染				|
--------------------------------
	Yaml yaml = new Yaml();
	String user = yaml.loadAs(Main.class.getResourceAsStream("/application.yml"),User.class);


--------------------------------
把对象渲染为 yml				|
--------------------------------
	Yaml yaml = new Yaml();
	Map<String,Object> map = new HashMap<>();
	map.put("name", "KevinBlandy");
	map.put("age", "23");
	map.put("gender", "男");
	yaml.dump(map, new PrintWriter(System.out));	//{gender: 男, name: KevinBlandy, age: '23'}
	
























