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
	i := 0
	go func() {
		for {
			fmt.Printf("timer: %v\n", i)
			i++
			time.Sleep(1 * time.Second)
		}
	}()
	for {
		select {
		case <-time.After(3 * time.Second):
			fmt.Println("case 1")
		case <-time.After(4 * time.Second):
			fmt.Println("case 2")
		case i := <-ch:
			fmt.Printf("get i %v \n", i)
			time.Sleep(6 * time.Second)
			fmt.Println("case 3 fnish")
		}
	}
}
