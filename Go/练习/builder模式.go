package main

import (
	"fmt"
)

type User struct {
	Name string
	Age int
	Hobby []string
}

type UserBuilder struct {
	*User
}

func (u *UserBuilder) SetName(name string) *UserBuilder {
	u.Name = name
	return u
}
func (u *UserBuilder) SetAge(age int) *UserBuilder {
	u.Age = age
	return u
}
func (u *UserBuilder) SetHobby(hobby []string) *UserBuilder {
	u.Hobby = hobby
	return u
}
func (u *UserBuilder) Build() *User {
	return u.User
}

func main() {
	var user *User = (&UserBuilder{new(User)}).SetName("Áõ±¸").SetAge(25).SetHobby([]string {"³ª", "Ìø", "rap"}).Build()
	fmt.Println(user)
}


