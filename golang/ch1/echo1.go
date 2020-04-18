package main

import (
	"fmt"
	"os"
)

func main() {
	var s, sep string
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
	}
	fmt.Println(s)
}

func test() {
	for true {
		var s = 2
		fmt.Println("Hello")
		fmt.Println(s)
	}
}
