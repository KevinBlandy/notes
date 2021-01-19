package main

import (
	"fmt"
)

func main() {
	_99()
}
func _99(){
	for i := 1; i < 10 ; i ++ {
		for x := 1; x <= i; x ++ {
			fmt.Printf("%d x %d = %d	", x, i, i * x)
		}
		fmt.Println()
	}
}
