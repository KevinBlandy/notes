--------------------------
user
--------------------------
	# 系统用户相关的Api


--------------------------
var
--------------------------


--------------------------
type
--------------------------
	# type Group struct {
			Gid  string // group ID
			Name string // group name
		}
		
		* 用户组

		func LookupGroup(name string) (*Group, error)
		func LookupGroupId(gid string) (*Group, error)

	# type UnknownGroupError string
		func (e UnknownGroupError) Error() string
	
	# type UnknownGroupIdError string
		func (e UnknownGroupIdError) Error() string
	
	# type UnknownUserError string
		func (e UnknownUserError) Error() string
	
	# type UnknownUserIdError int
		func (e UnknownUserIdError) Error() string
	
	# type User struct {
			Uid string			// 用户ID
			Gid string			// 用户组ID
			Username string		// 登录账户名
			Name string			// 自定义名称
			HomeDir string		// Home目录
		}

		* 系统用户

		func Current() (*User, error)
		func Lookup(username string) (*User, error)
		func LookupId(uid string) (*User, error)
			* 获取当前用户/根据用户名/用户ID查询用户


		func (u *User) GroupIds() ([]string, error)


--------------------------
func
--------------------------

