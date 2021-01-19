-------------------
stat				|
------------------
	# 指定路径文件的信息

	int stat (const char *path, struct stat *);
		* 获取文件的属性,成功返回0,失败返回-1
		* path 文件的路径,stat 保存状态的结构体
		* 结构体的定义
			{ _dev_t	st_dev; 	/* Equivalent to drive number 0=A 1=B ... */ 
			  _ino_t	st_ino; 	/* Always zero ? */			   
			  _mode_t	st_mode;	/* See above constants */		     
			   short 	st_nlink;	/* Number of links. */			     
			   short 	st_uid; 	/* User: Maybe significant on NT ? */	     
			   short 	st_gid; 	/* Group: Ditto */			    
			  _dev_t	st_rdev;	/* Seems useless (not even filled in) */    
			  __st_off_t	st_size;	/* File size in bytes */		    
			  __st_time_t	st_atime;	/* Access time (always 00:00 on FAT) */	    
			  __st_time_t	st_mtime;	/* Modified time */			    
			  __st_time_t	st_ctime;	/* Creation time */			    
			}
			

	S_ISDIR(m)	(((m) & S_IFMT) == S_IFDIR)
		* 判断文件是否是一个目录
			S_ISDIR(fileStat.st_mode);

	S_ISFIFO(m)	(((m) & S_IFMT) == S_IFIFO)
	S_ISCHR(m)	(((m) & S_IFMT) == S_IFCHR)
	S_ISBLK(m)	(((m) & S_IFMT) == S_IFBLK)
	S_ISREG(m)	(((m) & S_IFMT) == S_IFREG)
		* 判断是否一个普通文件