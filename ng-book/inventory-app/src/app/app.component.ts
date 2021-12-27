import {Component} from '@angular/core';
import {Product} from "./model/Product";

@Component({
    selector: 'inventory-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'inventory-app';

    constructor() {

    }


    onSubmit(value: any) {
        console.log("submit value:",value);
    }
}



