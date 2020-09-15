import { Component, OnInit } from '@angular/core';
import { NameServiceService } from '../service/name-service.service';

@Component({
  selector: 'app-test2',
  templateUrl: './test2.component.html',
  styleUrls: ['./test2.component.css']
})
export class Test2Component implements OnInit {

  pp
  private counter = 0;
  constructor(private nameService:NameServiceService) { 
    this.pp = nameService.getPeople()
  }

  ngOnInit(): void {
  }

  addPerson() {
    this.nameService.addPerson("John", this.counter++, "nc");
  }
}
