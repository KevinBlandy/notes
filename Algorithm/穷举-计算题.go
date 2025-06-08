package main

import "fmt"

func main() {
	/*
		 算法描述题
			    算
		----------
		题题题题题题

		“算” 字和 “题” 字不能为0。
	*/

	// 算
	for v1 := 1; v1 < 10; v1++ {
		// 法
		for v2 := 0; v2 < 10; v2++ {
			// 描
			for v3 := 0; v3 < 10; v3++ {
				// 述
				for v4 := 0; v4 < 10; v4++ {
					// 题
					for v5 := 1; v5 < 10; v5++ {
						// 得到：算法描述题
						x1 := (v1 * 10000) + (v2 * 1000) + (v3 * 100) + (v4 * 10) + v5
						// 得到：算
						x2 := v1
						// 得到：题题题题题
						x3 := (v5 * 100000) + (v5 * 10000) + (v5 * 1000) + (v5 * 100) + (v5 * 10) + v5

						// 计算：算法描述题 x 算 = 题题题题题
						if x1*x2 == x3 {
							fmt.Println("找到解")
							fmt.Printf(" %d\n", x1)
							fmt.Println("\t x")
							fmt.Printf("\t %d\n", x2)
							fmt.Println("------")
							fmt.Printf("%d\n", x3)
						}
					}
				}
			}
		}
	}
}
