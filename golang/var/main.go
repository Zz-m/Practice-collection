package main

import (
	"fmt"
	"sync"
	// "time"
)

var a string = "123"

var wg = sync.WaitGroup{}

var c = make(chan int)

var mu = sync.Mutex{}

func f() {
	mu.Lock()
	defer mu.Unlock()
	fmt.Println("go routine")
	wg.Done()
}

func main() {
	wg.Add(1)
	mu.Lock()
	fmt.Println("main")
	f()
	mu.Unlock()
	wg.Wait()
}

func print(s string) {
	fmt.Println(s)
}
