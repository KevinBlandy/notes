
a := []int{1, 2, 3, 4, 5, 6}
for i, j := 0, len(a) - 1; i < j; i, j = i + 1, j - 1 {
	a[i], a[j] = a[j], a[i]
}
fmt.Println(a)	// [6 5 4 3 2 1]