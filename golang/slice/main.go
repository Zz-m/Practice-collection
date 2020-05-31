package main

import (
	"fmt"
)

var a = make([]int, 7, 8)

func Test(slice *[]int) {
	*slice = append(*slice, 100)
	fmt.Println(slice)
}

func myAppend(list *[]int, value int) {
	*list = append(*list, value)
}

func main() {
	sli := []int{}
	fmt.Println(sli)
	fmt.Printf("addr %p\n", &sli)
	myAppend(&sli, 3)

	fmt.Println(sli)

	fmt.Printf("addr %p\n", &sli)
}
