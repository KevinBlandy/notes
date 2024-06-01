--------------------
bcrypt
--------------------
	# bcrypt 实现了 Provos 和 Mazières 的 bcrypt 自适应散列算法。

	# bcrypt可以用于数据库中的用户密码保存，相比md5而言更加的安全可靠。


--------------------
var
--------------------

	const (
		MinCost     int = 4  // the minimum allowable cost as passed in to GenerateFromPassword
		MaxCost     int = 31 // the maximum allowable cost as passed in to GenerateFromPassword
		DefaultCost int = 10 // the cost that will actually be set if a cost below MinCost is passed into GenerateFromPassword
	)

	var ErrHashTooShort = errors.New("crypto/bcrypt: hashedSecret too short to be a bcrypted password")

	var ErrMismatchedHashAndPassword = errors.New("crypto/bcrypt: hashedPassword is not the hash of the given password")

	var ErrPasswordTooLong = errors.New("bcrypt: password length exceeds 72 bytes")

--------------------
type
--------------------

	type HashVersionTooNewError byte
		func (hv HashVersionTooNewError) Error() string

	type InvalidCostError int
		func (ic InvalidCostError) Error() string

	type InvalidHashPrefixError byte
		func (ih InvalidHashPrefixError) Error() string

--------------------
func
--------------------

	func CompareHashAndPassword(hashedPassword, password []byte) error
		* 判断密文和明文密码是否匹配

	func Cost(hashedPassword []byte) (int, error)
	func GenerateFromPassword(password []byte, cost int) ([]byte, error)
		* 生成密码，返回密文


--------------------
Demo
--------------------
	# 密码加密和校验

		package main

		import (
			"errors"
			"fmt"
			"golang.org/x/crypto/bcrypt"
		)

		func main() {

			password := "6666666"

			// 加密密码，返回 []byte
			hashedPassword, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
			if err != nil {
				panic(err.Error())
			}

			fmt.Println(string(hashedPassword))

			// 判断密码是否匹配，返回 error，如果为 null 则表示匹配
			err = bcrypt.CompareHashAndPassword(hashedPassword, []byte(password))

			if err != nil {
				if errors.Is(err, bcrypt.ErrMismatchedHashAndPassword) {
					fmt.Println("密码校验失败")
				}
			} else {
				// 密码校验成功
			}
		}
