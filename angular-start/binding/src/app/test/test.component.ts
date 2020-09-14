import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  private id;
  public name = 'Jim';
  public endCode = '!';
  public siteUrl = window.location.href;
  public myId = "testId"
  public hasError = true;
  public isSpecial = true;
  public messageClass = {
    "text-success": !this.hasError,
    "text-danger": this.hasError,
    "text-special": this.isSpecial
  }

  styleColor = "orange"

  public successClass = "text-success"

  public showFiller = false

  public timer = 1;

  constructor() { }

  ngOnInit(): void {
    this.id = setInterval(this.fetchData, 1000);
  }

  ngOnDestroy() {
    clearInterval(this.id);
  }

  fetchData() {
    this.timer += 1;
    console.log("count: " + this.timer)
  }

  add1btnClick(event) {
    this.timer += 1;
    this.styleColor = "green"
    console.log("timer: " + this.timer)
    console.log("event: " + event)
  }

  logInput(inputValue) {
    console.log(inputValue)
  }
}
