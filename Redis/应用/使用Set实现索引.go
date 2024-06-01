
package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/redis/go-redis/v9"
)

type Product struct {
	// 商品标题
	Title string
	// 商品的标签
	Tag []string
}

type Indexer struct {
	rdb *redis.Client
	// 商品前 KEY 前缀
	productIndexKey string
	// 标签 KEY 前缀
	tagIndexKey string
}

// Add 添加商品到索引
func (i *Indexer) Add(product *Product) error {
	// 添加商品的映射
	var val []any
	for _, v := range product.Tag {
		val = append(val, v)
	}
	// 建立商品索引
	if cmd := i.rdb.SAdd(context.Background(), i.productIndexKey+":"+product.Title, val...); cmd.Err() != nil {
		return cmd.Err()
	}
	// 建立标签索引
	for _, v := range product.Tag {
		if cmd := i.rdb.SAdd(context.Background(), i.tagIndexKey+":"+v, product.Title); cmd.Err() != nil {
			return cmd.Err()
		}
	}
	return nil
}

// Products 根据标签检索关联的所有商品，OR 关系
func (i *Indexer) Products(tags ...string) ([]*Product, error) {
	var ret = make([]*Product, 0)
	var keys []string
	for _, v := range tags {
		keys = append(keys, i.tagIndexKey+":"+v)
	}

	// 对“标签”对应的商品集合进行 union
	cmd := i.rdb.SUnion(context.Background(), keys...)
	if cmd.Err() != nil {
		return nil, cmd.Err()
	}

	// 遍历所有商品的标签
	for _, v := range cmd.Val() {
		tags, err := i.Tags(v)
		if err != nil {
			return nil, err
		}
		ret = append(ret, &Product{
			Title: v,
			Tag:   tags,
		})
	}

	return ret, nil
}

// Tags 获取指定商品下的所有标签
func (i *Indexer) Tags(title string) ([]string, error) {
	cmd := i.rdb.SMembers(context.Background(), i.productIndexKey+":"+title)
	return cmd.Val(), cmd.Err()
}

func main() {
	rdb := redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})
	if cmd := rdb.Ping(context.Background()); cmd.Err() != nil {
		panic(cmd.Err().Error())
	}

	indexer := Indexer{rdb: rdb, productIndexKey: "pro", tagIndexKey: "tag"}
	indexer.Add(&Product{Title: "MacBook", Tag: []string{"电脑", "MAC"}})
	indexer.Add(&Product{Title: "ThinkPad", Tag: []string{"电脑", "Windows"}})
	indexer.Add(&Product{Title: "Iphone", Tag: []string{"手机", "IOS"}})
	indexer.Add(&Product{Title: "Nokia", Tag: []string{"手机", "Symbian"}})
	indexer.Add(&Product{Title: "瑞幸", Tag: []string{"咖啡", "拿铁"}})
	indexer.Add(&Product{Title: "酷迪", Tag: []string{"咖啡", "美式"}})
	indexer.Add(&Product{Title: "星巴克", Tag: []string{"咖啡", "美式", "拿铁"}})

	ret, err := indexer.Products("美式", "拿铁", "咖啡")
	if err != nil {
		panic(err.Error())
	}

	content, _ := json.MarshalIndent(ret, "", "	")
	fmt.Println(string(content))
}
