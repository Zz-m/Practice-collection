import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'structural-directives';

  name = "Nile Smith"
  message="old"

  parentClick(){
    console.log("name:" + this.name)
    console.log("message:" + this.message)
  }

  getMessage(event) {
    this.message = event
  }
}
