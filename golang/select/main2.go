package main

import (
	"fmt"
	"time"
)

func main() {
	ch := make(chan int)
	go func() {
		time.Sleep(4 * time.Second)
		fmt.Printf("go routine wake up.\n")
		ch <- 1024
		fmt.Printf("ch in queue.\n")
	}()

	select {
	case i := <-ch:
		fmt.Printf("Get %v \n", i)
	case <-time.After(2 * time.Second):
		fmt.Printf("After 2 second\n")
	}

	time.Sleep(5 * time.Second)
	fmt.Printf("Wait 5 second and quit.\n")

}
