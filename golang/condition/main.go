package main

import (
	"fmt"
	"sync"
	"time"
)

func main() {
	mu := sync.Mutex{}
	cond := sync.NewCond(&mu)

	go func() {
		time.Sleep(200 * time.Millisecond)
		cond.Broadcast()
	}()

	mu.Lock()
	cond.Wait()
	// mu.Lock()
	fmt.Println("he")
	// time.Sleep(2 * time.Second)
	// cond.Wait()
	mu.Unlock()
	time.Sleep(2 * time.Second)
}
