import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tab-app',
  templateUrl: './tab-app.component.html',
  styleUrls: ['./tab-app.component.css']
})
export class TabAppComponent implements OnInit {

    tabs:any;

  constructor() {
          this.tabs = [
              { title: 'About', content: 'This is the About tab' },
              { title: 'Blog', content: 'This is our blog' },
              { title: 'Contact us', content: 'Contact us here' },
          ];
  }

  ngOnInit(): void {
  }

}
