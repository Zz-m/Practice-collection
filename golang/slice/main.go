package main

import (
	"fmt"
)

func main() {
	sli := [3]int{}
	sli[0] = 0
	sli[1] = 1
	sli[2] = 2

	fmt.Println(sli)

	fmt.Println(sli[1:2])
}
