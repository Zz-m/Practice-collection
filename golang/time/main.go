package main

import (
	"log"
	"time"
)

func main() {
	//log.Printf("now: %v, before: %v, --: %v > %v\n", time.Now(), mapT.startTime, time.Now().Sub(mapT.startTime), 10*time.Second)
	firstTime := time.Now()
	time.Sleep(5 * time.Second)
	secondeTime := time.Now()
	diff := secondeTime.Sub(firstTime)
	log.Printf("time: %v", diff)
	log.Printf(">3s?: %v", diff < (3*time.Second))
}
