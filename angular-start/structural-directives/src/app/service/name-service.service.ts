import { Injectable } from '@angular/core';
import { Person } from '../model/person'

@Injectable({
  providedIn: 'root'
})
export class NameServiceService {

  private people = [
    new Person("John", 12, "nc"), new Person("Jack", 32, "bj"), new Person("Lili", 19, "shunqing"), new Person("Lucy", 21, "fujianglu")
  ]

  getPeople() {
    return this.people;
  }

  addPerson(name, age, addr) {
    this.people.push(new Person(name, age, addr))
  }

  constructor() { }
}
