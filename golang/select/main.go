package main

import (
	"fmt"
	"time"
)

var ch = make(chan int)

func main() {
	go func() {
		time.Sleep(4 * time.Second)
		ch <- 2
	}()
	for {
		select {
		case <-time.After(3 * time.Second):
			fmt.Println("case 1")
		case <-time.After(4 * time.Second):
			fmt.Println("case 2")
			time.Sleep(12 * time.Second)
		case i := <-ch:
			fmt.Printf("get i %v \n", i)
		}
	}
}
