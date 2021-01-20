package main

import (
	"fmt"
)

// 原始类
type User struct {
	Name string
	Age int
	Hobby []string
}

// 定义配置方法
type UserConfig func(*User)

// 各种配置实现
func Name(name string) UserConfig {
	return func(user *User) {
		user.Name = name
	}
}
func Age(age int) UserConfig {
	return func(user *User) {
		user.Age = age
	}
}
func Hobby(hobby []string) UserConfig {
	return func(user *User) {
		user.Hobby = hobby
	}
}

// 工厂方法
func NewUser(configs ...UserConfig) *User {
	user := new(User)
	for _, config := range configs {
		config(user)
	}
	return user
}

func main() {
	// 通过工厂方法初始化
	user := NewUser(Hobby([]string {"Java", "Go"}), Name("卧龙"))
	fmt.Println(user)
}