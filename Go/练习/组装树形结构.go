package main

import (
	"encoding/json"
	"fmt"
)

type Tree struct {
	Id       int64   `json:"id"`
	ParentId int64   `json:"parentId"`
	Title    string  `json:"title"`
	Child    []*Tree `json:"child"`
}

func main() {
	ret := tree([]*Tree{
		{Id: 1, ParentId: 0, Title: "重庆"},
		{Id: 2, ParentId: 1, Title: "万盛"},
		{Id: 3, ParentId: 2, Title: "关坝"},
		{Id: 4, ParentId: 3, Title: "凉风"},
		{Id: 5, ParentId: 0, Title: "四川"},
		{Id: 6, ParentId: 0, Title: "北京"},
		{Id: 7, ParentId: 5, Title: "达州"},
	})

	c, _ := json.MarshalIndent(ret, "", "	")
	fmt.Println(string(c))
}

func tree(list []*Tree) []*Tree {
	var root []*Tree
	for _, v := range list {
		if v.ParentId == 0 {
			root = append(root, v)
		}
	}
	var subTree func([]*Tree, *Tree)
	subTree = func(list []*Tree, t *Tree) {
		var subTreeList []*Tree
		for _, v := range list {
			if v.ParentId == t.Id {
				subTreeList = append(subTreeList, v)
			}
		}
		if len(subTreeList) > 0 {
			t.Child = subTreeList
			for _, v := range subTreeList {
				subTree(list, v)
			}
		}
	}
	for _, v := range root {
		subTree(list, v)
	}
	return root
}
