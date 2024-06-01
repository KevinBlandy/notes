package main

import (
	"context"
	"fmt"
	"github.com/redis/go-redis/v9"
)

type AutoComplete struct {
	rdb *redis.Client
}

// Add 添加自动补全的元素
func (a *AutoComplete) Add(keywords string, source float64) error {
	// 解码为字符数组
	chars := []rune(keywords)
	// 迭代所有成员，添加到 Set
	for i := range len(chars) {
		if i == 0 {
			continue // 忽略第一个空字节
		}
		cmd := a.rdb.ZIncrBy(context.Background(), string(chars[:i]), source, keywords)
		if cmd.Err() != nil {
			return cmd.Err()
		}
	}
	return nil
}

// Complete 自动补全元素
func (a *AutoComplete) Complete(keywords string, count int) ([]string, error) {
	cmd := a.rdb.ZRevRange(context.Background(), keywords, 0, int64(count-1))
	return cmd.Val(), cmd.Err()
}

func main() {
	rdb := redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})

	rdb.Ping(context.Background())

	a := &AutoComplete{rdb: rdb}
	a.Add("余文乐", 9)
	a.Add("余文朋", 1)
	a.Add("余兴", 1)
	a.Add("余明勇", 1)
	a.Add("余胜男", 3)

	ret, err := a.Complete("余", 9)
	if err != nil {
		panic(err.Error())
	}

	fmt.Println(ret)
}
