package main

import (
	"errors"
	"fmt"
	"math/rand"
	"sort"
	"time"
)

type Prize struct {
	Title string // 商品
	Odds  int    // 中将概率 1 - 100
}

func (p Prize) String() string {
	return fmt.Sprintf("[title=%s odds=%d]", p.Title, p.Odds)
}

func main() {
	ret := Raffle(&Prize{
		Title: "苹果",
		Odds:  9,
	}, &Prize{
		Title: "香蕉",
		Odds:  19,
	}, &Prize{
		Title: "荔枝",
		Odds:  8,
	}, &Prize{
		Title: "西瓜",
		Odds:  10,
	}, &Prize{
		Title: "葡萄",
		Odds:  15,
	})
	fmt.Printf("抽奖结果：%v\n", ret)
}

// Raffle 抽奖
func Raffle(prizes ...*Prize) *Prize {

	const totally = 100

	// 按照 odds 排序
	sort.Slice(prizes, func(i, j int) bool {
		return prizes[i].Odds < prizes[j].Odds
	})

	fmt.Printf("奖品：%v\n", prizes)

	var weight []int
	weight = append(weight, 0)

	var total int

	for _, v := range prizes {

		total += v.Odds
		if total > totally {
			// 总中奖率，超过了 100%
			panic(errors.New("中奖率不能超过 100%"))
		}

		// 当前奖品占的格子长度等于自己权重上上一个奖品的权重
		weight = append(weight, weight[len(weight)-1]+v.Odds)
	}

	fmt.Printf("中奖率：%v\n", weight)

	// 获取 0 - 100 随机数
	randVal := rand.New(rand.NewSource(time.Now().UnixNano())).Intn(totally)

	fmt.Printf("随机值：%v\n", randVal)

	for i := 1; i < len(weight); i++ {

		start := weight[i]
		end := weight[i-1]

		// 左闭右开，右开左闭都可以
		if randVal <= start && randVal > end {
			return prizes[i-1]
		}
	}
	return nil
}
