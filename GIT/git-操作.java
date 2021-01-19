---------------------------------
本地仓库						 |
---------------------------------
# 参考地址
	http://tv1314.com/post-571.html


初始化仓库
	git init 
		* 默认初始化当前目录,而且当前面必须是空的
		* 如果指定了目录参数,则会在当前目录新建一个目录进行初始化

全局的设置(设置全局,添加 --global 参数)
	git config --global user.name "KevinBlandy"
	git config --global user.email "747692844@qq.com"
		* 表示机器上所有的Git仓库都会使用这个配置,当然也可以对某个仓库指定不同的用户名和Email地址
		
	git config --global color.ui true
		* 开启彩色的UI(交互文字有颜色)

添加文件到暂存区
	git add [文件名]
		* 添加新的文件,或者是被修改过的文件到暂存区

删除文件
	git rm [文件名]
		* 删除掉文件的同时,并且提交到暂存区

提交文件
	git commit -m "注释"


查看工作区状态
	git status
		-----------------------------------------------------------------------------
		# Untracked files:									//新增文件未 add 到暂存区
		#
		#       rock										//新增的文件
		-----------------------------------------------------------------------------
		# Changes to be committed:							//暂存区有了新的变化,但是没有 commit 到分支
		#
		#       new file:   kevin							//添加的文件名称
		#		modified:   kevin							//修改的文件名称
		#		
		-----------------------------------------------------------------------------
		# Changes not staged for commit:					//文件修改了未 add 到暂存区
		#
		#       modified:   kevin							//被修改的文件
		#		deleted:    file							//被删除的文件
		-----------------------------------------------------------------------------


查看文件修改内容
	git diff

查看提交记录(版本)
	git log
		commit 333e9a87783594998ea35f9b66611a112caa3164		//版本号
		Author: KevinBlandy <747692844@qq.com>				//用户
		Date:   Thu Nov 2 15:19:10 2017 +0800				//时间
	
			first											//提交的注释信息
		
版本回退
	git reset --hard HEAD^
		* 在Git中,用HEAD表示当前版本,也就是最新的提交 3628164...882e1e0(注意我的提交ID和你的肯定不一样)
		* 上一个版本就是HEAD^,上上一个版本就是HEAD^^
		* 当然往上100个版本写100个^比较容易数不过来,所以写成HEAD~100
	
	
	git reset --hard 4e7e8757dc1bb577df3eab1de1572f7ab7f665ef
		* 切换到指定的版本
		* 版本号没必要写全,前几位就可以了,Git会自动去找

把文件从暂存区撤回到工作区
	git reset HEAD [文件]
		* 其实就是 add 文件到了暂存区后,从暂存区删除文件
		* HEAD 参数表示最新的版本
		* 场景:当你不但改乱了工作区某个文件的内容,还添加到了暂存区时,想丢弃修改
			第一步用命令git reset HEAD file,先从暂存区删除add的文件信息
			第二步用命令git checkout -- file,把文件还原到修改之前(与版本库保持一直)的样子

撤销文件的修改/删除
	 git checkout -- [文件]
		* 文件修改/删除后还没有被放到暂存区			撤销就回到和版本库一模一样的状态
		* 文件已经添加到暂存区后,又作了修改/删除	撤销就回到添加到暂存区时的状态
		* 总之,就是让这个文件回到最近一次 git commit 或 git add时的状态
		* -- 符号很重要,不然这个命令就会变成切换分支的命令
		* 场景:当你改乱了工作区某个文件的内容,想直接丢弃工作区的修改时,用命令


查看操作记录(命令历史)
	git reflog
		* {0}表示是最近的一次记录
		* 可以在记录前面看到操作的版本号
		* 可以通过该记录,进行版本的"前进"
		4e7e875 HEAD@{0}: reset: moving to 4e7e8757dc1bb577df3eab1de1572f7ab7f665ef
		333e9a8 HEAD@{1}: reset: moving to HEAD^^
		4e7e875 HEAD@{2}: commit: 3
		ef93935 HEAD@{3}: commit: 2
		333e9a8 HEAD@{4}: reset: moving to HEAD^^
		1a4446f HEAD@{5}: commit: 哈哈
		94e9b70 HEAD@{6}: commit: 2
		333e9a8 HEAD@{7}: reset: moving to HEAD^
		e84a601 HEAD@{8}: commit: 第二次提交
		333e9a8 HEAD@{9}: reset: moving to HEAD^
		6eb36af HEAD@{10}: commit: 2
		333e9a8 HEAD@{11}: commit (initial): first

中文处理
	git config --global core.quotepath false
		* 解决在 git bash 中,中文以编码形式出现的问题
	
	git config --global gui.encoding utf-8
		* 设置 gui 的编码

	git config --global i18n.commitencoding utf-8
		* 设置提交时用的编码,必须与服务器保持一致


---------------------------------
远程仓库						 |
---------------------------------
创建SSH私钥
	ssh-keygen -t rsa -C "747692844@qq.com"
		* 会在 ~/.ssh 目录中生成两个文件
			id_rsa				私钥,不能泄露出去
			id_rsa.pub			id_rsa.pub是公钥,可以放心地告诉任何人
	
		* github 的 https://github.com/settings/keys 
			* title 随便写
			* key 要输入 id_rsa.pub 中的内容


把本地仓库与远程仓库关联
	git remote add origin [git仓库地址]
		* 远程库的名字就是origin,这是Git默认的叫法,也可以改成别的,但是origin这个名字一看就知道是远程库

把本地仓库的文件推送到远程仓库
	git push -u origin master
		* origin 远程仓库的名称,如果本地仓库可能关联了多个
		* master 推送到master分支
		* 由于远程库是空的,第一次推送master分支时,加上了-u参数
			* Git不但会把本地的master分支内容推送的远程新的master分支
			* 还会把本地的master分支和远程的master分支关联起来,在以后的推送或者拉取时就可以简化命令

从远程仓库克隆
	git clone [远程仓库地址]

从中央仓库更新本地仓库的数据
	git pull
		* 也可以在后面添加远程仓库的名称,如果本地仓库可能关联了多个
			git pull origin

查看远程仓库的名称
	git remote
		* 其实就是返回远程仓库的名称 origin
		* 可以添加参数 -v 来显示更完整的信息

# 创建与远程分支关联的本地分支
	git checkout -b  origin/[远程分支名称] [本地分支名称]

# 设置本地分支与远程分支关联
	git branch --set-upstream-to origin/[远程分支名称] [本地分支名称]

	* 本地分需要先创建

---------------------------------
分支管理						 |
---------------------------------

创建并且切换分支
	git checkout -b [分支名称]
		* 相当于两条命令
			git branch [name]			创建分支		
			git checkout [name]			切换分支


查看当前所在的分支
	git branch
		* 命令会列出所有分支，当前分支前面会标一个*号


分支合并
	git merge [name]
		* 把指定名称的分支(name),合并到当前分支上
		* 合并分支时,加上 --no-ff 参数就可以用普通模式合并,合并后的历史有分支,能看出来曾经做过合并
				git merge --no-ff -m "注释" [name]
				因为本次合并要创建一个新的commit,所以加上-m参数,把commit描述写进去
		* 而fast forward合并就看不出来曾经做过合并(默认)

删除分支
	 git branch -d [name]


# 冻结现场(保存进度)
	git stash

# 查看冻结的列表
	git stash list

# 解除现场冻结
	git stash apply stash@{0}

# 删除冻结
	git stash drop
	git stash pop(解除并且删除最后一个冻结)

---------------------------------
分支策略管理					 |
---------------------------------
	


---------------------------------
冲突解决						 |
---------------------------------


---------------------------------
忽略文件						 |
---------------------------------
	* 在Git工作区的根目录下创建一个特殊的.gitignore文件,然后把要忽略的文件名填进去Git就会自动忽略这些文件
	* 不需要从头写.gitignore文件,GitHub已经为我们准备了各种配置文件,只需要组合一下就可以使用了
	* 所有配置文件可以直接在线浏览：https://github.com/github/gitignore

	* 忽略文件的原则是
		忽略操作系统自动生成的文件,比如缩略图等
		忽略编译生成的中间文件,可执行文件等,也就是如果一个文件是通过另一个文件自动生成的,那自动生成的文件就没必要放进版本库,比如Java编译产生的.class文件
		忽略你自己的带有敏感信息的配置文件,比如存放口令的配置文件。
	
	* .gitignore文件本身要放到版本库里,并且可以对.gitignore做版本管理

	* 检验.gitignore的标准是git status命令是不是说working directory clean
	* 添加一个文件到Git,但发现添加不了,原因是这个文件被.gitignore忽略了,如果你确实想添加该文件可以用-f强制添加到Git
		 git add -f App.class
	
	* 你发现,可能是.gitignore写得有问题,需要找出来到底哪个规则写错了,可以用git check-ignore命令检查
		git check-ignore -v App.class
		* Git会告诉我们,gitignore的第几行规则忽略了该文件,于是我们就可以知道应该修订哪个规则
	
	* 例
		/target/			//忽略指定的文件夹,及其文件夹下的内容
		.classpath			//忽略指定名称的文件
		*.class				//忽略指定后缀的文件


---------------------------------
eclipse							 |
---------------------------------
.gitignore 忽略配置
	/bin/
	/target/
	.project
	.classpath
	/.settings/
