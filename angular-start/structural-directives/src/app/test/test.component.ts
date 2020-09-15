import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { NameServiceService } from '../service/name-service.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  em :EventEmitter<string> = new EventEmitter

  showName = true

  @Input()
  parentData;
  @Output() childEvent = new EventEmitter<string>()


  john = {
    name:"John Smith",
    age : 12,
    addr: "nc"
  }

  people

  constructor(private nameService:NameServiceService) { 
    this.people = nameService.getPeople()
  }

  ngOnInit(): void {
  }

  toggleNameBtnClick() {
    this.parentData = "John Smith"
    this.showName = !this.showName
    if (this.showName) {
      console.log('emitter!')
      this.childEvent.emit("Hello from child.")
    }
  }

}
