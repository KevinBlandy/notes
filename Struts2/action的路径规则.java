struts2这个action很奇葩

Http://localhost:8080/a/b/c/d/demo.action


首先查找有没有一个叫做 /a/b/c/d 的namespace,看看里面有没有一个叫做action名名称为demo的action,发现没有,然后
namespace = "/a/b/c/d"			action的name="demo.action"	没有	查看默认命名空间有没有	没有[404]
namespace = "/a/b/c"	减去一个路径	action的name="demo.action"	没有	查看默认命名空间有没有	没有[404]
namespace = "/a/b"	减去一个路径	action的name="demo.action"	没有	查看默认命名空间有没有	没有[404]
namespace = "/a"	减去一个路径	action的name="demo.action"	没有	查看默认命名空间有没有	没有[404]
namespace = "/"				action的name="demo.action"	没有	查看默认命名空间有没有	没有[404]


---------------> 按照上述流程,只要找到同名的 action ,那么就执行.后面的也就不会执行了!


先查找pacakge有没有?如果没有！就减去一级目录!再找包!如果包还没有继续挨个减去!
如果找到了package,那么就看里面有没有action,如果有！执行!如果没有.就去默认的命名空间找！如果找不到！直接404！

实际开发,没太大意义!但是一定要知道！

还有一个非常重要的信息,没人教我！自己验证的！
当struts.xml中include包含了其他的xml文件！而这些其他的xml文件中,有相同的包名,以及Action名称！
执行的顺序就是--->  哪个xml先被struts.xml引用！谁就执行!换句话说就是,谁在上！谁先执行！

--------------------------------------------------------------------------------------------------
关于Action名称的搜索顺序
1,获得请求的URL,例如URL是:http://localhost:8080/path1/path2/path3/demo.action

2,首先寻找namespace为:/path1/path2/path3的package.如果不存在package,则执行步骤3.
  如果存在这个package,则在这个package中寻找名字为demo的action,当在该pacakage中找不到demo这个action时,直接跑到默认namesapce的package里面去寻找action(默认命名空间为空字符串""),如果在默认namespace的package里面还找不到action,那么就抛出异常,报错!

3,寻找namesapce为:/path1/path2的package,如果不存在这个package,则执行步骤4.如果存在,则在这个package中找寻名字为demo的action,当该package中找不到action的时候,直接到默认的namespace的package中去寻找名字为demo的action,在默认的namespace中还找不到demo这个action,抛出异常,报错

4,寻找namespace为:/path1的package,如果不存在这个package则执行步骤5,如果存在这个package,则在这个package中找寻名为demo的action,当在该package中找不到action时,就会直接跑到默认的namespace的package里面去找名字为demo的action,如果在namespace的package里面还找不到该action,抛出异常,报错

5,寻找namespace为/的package,如果存在这个package,则在这个package中寻找名字为demo的action,当在package中寻找不到demp,这个action时,都会去默认的namespace的package里面去寻找action,都特么找不到了,那就直接抛出异常,报错!
