查看你的Eclipse 中使用的是什么SVN Interface 
windows > preference > Team > SVN #SVN Interface (右侧中下方) 


2. 如果是用的JavaHL, 找到以下目录并删除auth目录下的文件. 
Windows 7
C:\Users\"你的用户名"\AppData\Roaming\Subversion\auth\
XP
C:\Documents and Settings\"你的用户名"\Application Data(隐藏文件夹)\"Subversion\auth"  


3. 如果你用的SVNKit, 找到以下目录并删除.keyring文件. 
[eclipse ]"configuration"org.eclipse .core.runtime 