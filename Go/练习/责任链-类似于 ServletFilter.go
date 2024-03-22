package main

import (
	"container/list"
	"fmt"
)

type Pipeline struct {
	list *list.List
}

func (p *Pipeline) DoFilter(content string) {
	// 从队列头开始获取
	if p.list == nil {
		return
	}
	ele := p.list.Front()
	if ele == nil {
		return
	}

	p.list.Remove(ele)

	f := ele.Value.(Filter)
	f.DoFilter(content, p)
}
func (p *Pipeline) Add(filter Filter) {
	if p.list == nil {
		p.list = list.New()
	}
	p.list.PushBack(filter) // 添加到队列尾部
}

type Filter interface {
	DoFilter(content string, pipeline *Pipeline)
}

type FilterFunc func(content string, pipeline *Pipeline)

func (f FilterFunc) DoFilter(content string, pipeline *Pipeline) {
	f(content, pipeline)
}

func main() {
	pipeline := &Pipeline{}
	pipeline.Add(FilterFunc(func(content string, pipeline *Pipeline) {
		fmt.Printf("Filter1 开始执行：%s\n", content)
		pipeline.DoFilter(content)
		fmt.Printf("Filter1 执行完毕：%s\n", content)
	}))
	pipeline.Add(FilterFunc(func(content string, pipeline *Pipeline) {
		fmt.Printf("Filter2 开始执行：%s\n", content)
		pipeline.DoFilter(content)
		fmt.Printf("Filter2 执行完毕：%s\n", content)
	}))
	pipeline.Add(FilterFunc(func(content string, pipeline *Pipeline) {
		fmt.Printf("Filter3 开始执行：%s\n", content)
		pipeline.DoFilter(content)
		fmt.Printf("Filter3 执行完毕：%s\n", content)
	}))

	pipeline.DoFilter("Hello")
}
