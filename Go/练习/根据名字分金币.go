import (
	"fmt"
)

var (
	coins = 50
	users = [] string{
		"Matthew", "Sarah", "Augustus", "Heidi", "Emilie", "Peter", "Giana", "Adriano", "Aaron", "Elizabeth",
	}
	distribution = make(map[string]int, len(users))
)

func main() {
	left := dispatchCoin()
	fmt.Printf("还剩下 %d 个金币\n", left)
}
func dispatchCoin() int {
	for _, user := range users{
		count := 0
		for _, c := range []rune(user) {
			if c == 'e' || c == 'E' {
				count += 1
			} else if c == 'i' || c == 'I' {
				count += 2
			} else if c == 'o' || c == 'O' {
				count += 3
			} else if c == 'u' || c == 'U' {
				count += 4
			}
		}
		fmt.Printf("用户 %s 分到了 %d 个金币\n", user, count)
		distribution[user] = count
		coins -= count
	}
	return coins
}

