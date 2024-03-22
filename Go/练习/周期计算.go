package main

import (
	"fmt"
	"time"
)

func main() {

	start, end := Period(time.Date(2024, time.January, 15, 0, 0, 0, 0, time.Local), time.Now(), 30)

	fmt.Println(start.Format(time.DateTime), end.Format(time.DateTime))
}

// Period 从 statTime 开始，每 periodDays 天一个周期，计算出 nowTime 所在周期的开始和结束时间
func Period(statTime time.Time, nowTime time.Time, periodDays int) (start time.Time, end time.Time) {

	// 截取年月日
	startDate := time.Date(statTime.Year(), statTime.Month(), statTime.Day(), 0, 0, 0, 0, statTime.Location())
	nowDate := time.Date(nowTime.Year(), nowTime.Month(), nowTime.Day(), 0, 0, 0, 0, nowTime.Location())

	// 相距天数
	days := int(nowDate.Sub(startDate).Hours()) / 24

	// 第几个周期
	period := days / periodDays

	start = startDate.AddDate(0, 0, period*periodDays)
	end = start.AddDate(0, 0, periodDays-1)

	return
}
