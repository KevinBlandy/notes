————————————————————————————————
1,Linux入门-软件安装和卸载			|
————————————————————————————————
	* 这是非常重要的一个东西,这里会讲三种在Linux上安装软件的方法
	1,apt-get安装
		* 这东西是Ubantu提供的,Ubantu有一个软件源服务器,在Internet上,所有Ubantu系统都是可以使使用这些服务器
		* Ubantu的软件源服务器,也有非官方的,例如:新浪,搜狐,清华大学
		* 我们需要在Ubantu上安装软件的时候,可以选择指定过服务器,通过网络在服务器上下载安装软件
		* 很显然,这种安装方式是需要联网才能进行
		sudo vi /etc/apt/source.list			//该文件中就有配置源服务器
		sudo apt-get update						//更新源,把源服务器中的软件列表下载到本地,仅仅就是下载个名称和介绍.并非是下载软件本身
			* 一般都是在更新的软件源服务器之后,会操作,获取服务器上的软件列表
		sudo apt-cache search "名称"				//根据指定名称,从本地从服务器上down下来的软件列表中查询
		sudo apt-get install 软件名				//安装指定名称的软件,当然肯定是要有网络的情况下执行
		sudo apt-get install 软件名 --reinstall  //重新安装指定的软件
		sudo apt-get -f install	软件名			//修复安装
		sudo apt-get build-dep 软件名			//安装相关的编译环境
		sudo agt-get remove 软件名				//删除软件
		sudo apt-get remove 软件名 --purge		//删除软件,包括配置文件
		sudo apt-cache show 软件名				//获取软件的相关信息,例如:大小,之类的数据
		sudo apt-get upgrade					//更新已经安装的软件
		sudo apt-get dist-upgrade				//升级系统
		sudo apt-cache depends 软件名			//获取指定软件的依赖信息
		sudo apt-cache rdepends 软件名			//查看指定软件,被哪些软件依赖
		sudo apt-get source 软件名				//下载该软件的源码
	2,deb包安装
		* deb文件格式的安装包
		sudo dpkg -i 名字.deb					//安装指定的软件包
		sudo dpkg -r 名字.deb					//删除指定的软件包
		sudo dpkg -r --purge 名字.deb			//连同配置文件一起删除
		sudo dpkg -info 名字.deb					//查看软件安装包的详细信息
		sudo dpkg -L 名字.deb					//查看文件拷贝详情命令
		sudo dpkg -l							//看到系统中以及安装的软件包
		sudo dpkg-reconfigure 名字				//重新配置软件包
	3,源码安装
		* .h,.c这种软件源代码的安装方式
		* 通常为几个步骤
			1,解压缩源码包
			2,检测文件是否丢失
			3,编译源码,生成库和可执行程序
			4,把库和可执行程序安装到系统路径下
		tar zxbf 源码压缩包名称
		cd 到解压后的路径
		./configure		
		make
		sudo make install
