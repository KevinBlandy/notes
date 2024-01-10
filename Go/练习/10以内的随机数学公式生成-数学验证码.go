package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	for {
		fmt.Println(Foo())
		time.Sleep(time.Millisecond * 100)
	}
}

func Foo() (string, int) {
	numbers := []int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
	operator := []rune{'+', '-', '*', '/'}

	random := rand.New(rand.NewSource(time.Now().UnixNano()))
	switch operator[random.Intn(len(operator))] {
	case '+':
		v1 := numbers[rand.Intn(len(numbers))]
		v2 := numbers[rand.Intn(len(numbers))]
		return fmt.Sprintf("%d + %d = ?", v1, v2), v1 + v2
	case '-':
		v1 := numbers[rand.Intn(len(numbers))]
		v2 := numbers[rand.Intn(len(numbers))]
		return fmt.Sprintf("%d - %d = ?", v1, v2), v1 - v2
	case '*':
		v1 := numbers[rand.Intn(len(numbers))]
		v2 := numbers[rand.Intn(len(numbers))]
		return fmt.Sprintf("%d * %d = ?", v1, v2), v1 * v2
	default:

		v1 := numbers[rand.Intn(len(numbers))]
		var arr []int
		for _, v := range numbers[1:] {
			if v1%v == 0 {
				arr = append(arr, v)
			}
		}

		v2 := arr[rand.Intn(len(arr))]
		return fmt.Sprintf("%d / %d = ?", v1, v2), v1 / v2
	}
}
